package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.StockDto;
import com.backend.juvaro.dtos.output.VentaDto;
import com.backend.juvaro.dtos.requests.ItemVentaRequest;
import com.backend.juvaro.dtos.requests.RegistrarVentaRequest;
import com.backend.juvaro.dtos.update.UpdateStockRequest;
import com.backend.juvaro.entities.*;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.mappers.VentaMapper;
import com.backend.juvaro.repositories.*;
import com.backend.juvaro.services.StockService;
import com.backend.juvaro.services.VentaService;
import com.backend.juvaro.services.notificaciones.factory.MensajeFactory;
import com.backend.juvaro.services.notificaciones.observer.VentaPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VentaServiceImpl implements VentaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VentaServiceImpl.class);

    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private DepartamentoRepository departamentoRepository;
    @Autowired
    private VentaMapper ventaMapper;
    @Autowired
    private VentaPublisher ventaPublisher;
    @Autowired
    private MensajeFactory mensajeFactory;

    @Override
    @Transactional // ¡Muy importante! Asegura que toda la operación sea atómica. Si algo falla, se revierte todo.
    public VentaDto crearVenta(RegistrarVentaRequest request) throws BadRequestException, ResourceNotFoundException {
        LOGGER.info("Iniciando proceso de creación de venta para el usuario ID: {}", request.getUsuarioId());

        // 1. Validar y obtener las entidades principales
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getUsuarioId()));

        // 2. Preparar el objeto Venta principal
        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(usuario);
        nuevaVenta.setFecha(LocalDate.now());
        nuevaVenta.setDetalles(new ArrayList<>());

        double valorTotalVenta = 0.0;

        // 3. Procesar cada ítem del "carrito de compras"
        for (ItemVentaRequest item : request.getItems()) {
            // Validar stock
            StockDto stockActual = stockService.buscarStockPorProductoYDepartamento(item.getProductoId(), item.getDepartamentoId());
            if (stockActual.getCantidad() < item.getCantidad()) {
                throw new BadRequestException("Stock insuficiente para el producto ID " + item.getProductoId());
            }

            // Descontar stock
            int nuevaCantidad = stockActual.getCantidad() - item.getCantidad();
            UpdateStockRequest stockRequest = new UpdateStockRequest();
            stockRequest.setCantidad(nuevaCantidad);
            stockService.actualizarStock(stockActual.getId(), stockRequest);

            // --- Notificación de STOCK (Patrón Observer) ---
            ventaPublisher.notificar(mensajeFactory.crearStockUpdateMensaje(item.getProductoId(), nuevaCantidad));

            // Preparar el detalle de la venta
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + item.getProductoId()));
            Departamento departamento = departamentoRepository.findById(item.getDepartamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + item.getDepartamentoId()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);

            detalle.setDepartamento(departamento);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio()); // Asumiendo que Producto tiene getPrecio()
            detalle.setVenta(nuevaVenta);

            nuevaVenta.getDetalles().add(detalle);
            valorTotalVenta += detalle.getPrecioUnitario() * detalle.getCantidad();
        }

        // 4. Finalizar y guardar la Venta
        nuevaVenta.setValorTotal(valorTotalVenta);
        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        // --- Notificación de VENTA REALIZADA (Patrón Observer) ---
        ventaPublisher.notificar(mensajeFactory.crearVentaRealizadaMensaje(ventaGuardada));

        LOGGER.info("Venta creada exitosamente con ID: {}. Notificaciones delegadas al Publisher.", ventaGuardada.getId());

        // 5. Devolver el resultado
        return ventaMapper.toDto(ventaGuardada);
    }


    @Override
    public List<VentaDto> listarVentas() {
        LOGGER.info("Solicitud para listar todas las ventas.");

        List<Venta> ventas = ventaRepository.findAll();

        List<VentaDto> ventasDto = ventas.stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());

        LOGGER.info("Se encontraron {} ventas.", ventasDto.size());
        return ventasDto;
    }

    @Override
    public List<VentaDto> buscarVentasPorUsuario(long usuarioId) throws ResourceNotFoundException {
        LOGGER.info("Solicitud para listar todas las ventas del usuario. {}", usuarioId);

        if (!usuarioRepository.existsById(usuarioId)) {
            throw new ResourceNotFoundException("No se pudo listar las ventas porque no se encontró un usuario con el ID: " + usuarioId);
        }
        List<Venta> ventas = ventaRepository.findByUsuarioId(usuarioId);

        List<VentaDto> ventasDto = ventas.stream()
                .map(ventaMapper::toDto)
                .collect(Collectors.toList());

        LOGGER.info("Se encontraron {} ventas.", ventasDto.size());
        return ventasDto;
    }

    @Override
    public VentaDto obtenerVentaPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando venta con ID: {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));

        LOGGER.info("Venta encontrada: {}", venta.getId());
        return ventaMapper.toDto(venta);
    }
}

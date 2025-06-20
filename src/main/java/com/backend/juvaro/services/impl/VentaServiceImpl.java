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
    // En el futuro, también inyectarías el VentaPublisher y MensajeFactory aquí

    @Override
    @Transactional // ¡Muy importante! Asegura que toda la operación sea atómica. Si algo falla, se revierte todo.
    public VentaDto crearVenta(RegistrarVentaRequest request) throws BadRequestException, ResourceNotFoundException {
        LOGGER.info("Iniciando proceso de creación de venta para el usuario ID: {}", request.getUsuarioId());


        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + request.getUsuarioId()));

        Venta nuevaVenta = new Venta();
        nuevaVenta.setUsuario(usuario);
        nuevaVenta.setFecha(LocalDate.now());
        nuevaVenta.setDetalles(new ArrayList<>());

        double valorTotalVenta = 0.0;

        for (ItemVentaRequest item : request.getItems()) {

            StockDto stockActual = stockService.buscarStockPorProductoYDepartamento(item.getProductoId(), item.getDepartamentoId());


            if (stockActual.getCantidad() < item.getCantidad()) {
                throw new BadRequestException("Stock insuficiente para el producto ID " + item.getProductoId() +
                        ". Solicitado: " + item.getCantidad() + ", Disponible: " + stockActual.getCantidad());
            }

            LOGGER.info("Stock validado para producto ID: {}. Descontando {} unidades.", item.getProductoId(), item.getCantidad());


            int nuevaCantidad = stockActual.getCantidad() - item.getCantidad();
            UpdateStockRequest stockRequest = new UpdateStockRequest();
            stockRequest.setCantidad(nuevaCantidad);

            stockService.actualizarStock(stockActual.getId(), stockRequest);
            // NOTA: Más adelante, es este método 'actualizarStock' el que debería notificar al Publisher.


            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + item.getProductoId()));
            Departamento departamento = departamentoRepository.findById(item.getDepartamentoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Departamento no encontrado con ID: " + item.getDepartamentoId()));

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setDepartamento(departamento);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            detalle.setVenta(nuevaVenta);

            nuevaVenta.getDetalles().add(detalle);
            valorTotalVenta += detalle.getPrecioUnitario() * detalle.getCantidad();
        }


        nuevaVenta.setValorTotal(valorTotalVenta);
        Venta ventaGuardada = ventaRepository.save(nuevaVenta);

        LOGGER.info("Venta creada exitosamente con ID: {}. Valor total: {}", ventaGuardada.getId(), ventaGuardada.getValorTotal());
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
    public VentaDto obtenerVentaPorId(Long id) throws ResourceNotFoundException {
        LOGGER.info("Buscando venta con ID: {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));

        LOGGER.info("Venta encontrada: {}", venta.getId());
        return ventaMapper.toDto(venta);
    }
}

package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.ProductoDto;
import com.backend.juvaro.dtos.requests.RegistrarProductoRequest;
import com.backend.juvaro.dtos.update.UpdateProductoRequest;
import com.backend.juvaro.entities.Categoria;
import com.backend.juvaro.entities.Producto;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.mappers.ProductoMapper;
import com.backend.juvaro.repositories.CategoriaRepository;
import com.backend.juvaro.repositories.ProductoRepository;
import com.backend.juvaro.services.ProductoService;
import com.backend.juvaro.utils.JsonPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductoServiceImpl implements ProductoService {

    private final Logger LOGGER = LoggerFactory.getLogger(ProductoServiceImpl.class);

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ProductoMapper productoMapper;

    @Override
    public List<ProductoDto> listarProductos() throws BadRequestException {
        return productoRepository.findAll()
                .stream()
                .map(productoMapper::toDto)
                .toList();
    }

    @Override
    public Object registrarProducto(RegistrarProductoRequest request) throws BadRequestException {
        var producto = productoMapper.toEntity(request);
        categoriaRepository.findById(request.getCategoriaId())
                .orElseThrow(() -> new BadRequestException("Categoría no encontrada"));

        var productoGuardado = productoRepository.save(producto);
        LOGGER.info("Registro guardado: {}", JsonPrinter.toString(producto));
        return productoMapper.toDto(productoGuardado);
    }

    @Override
    public ProductoDto buscarProductoPorId(Long id) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(id).orElse(null);
        ProductoDto productoEncontrado = null;
        if (producto != null) {
            productoEncontrado = productoMapper.toDto(producto);
            LOGGER.info("Buscando producto: {}", productoEncontrado);
        }else{
            LOGGER.error("No existe el producto");
            throw new ResourceNotFoundException("No existe el producto en la base de datos");
        }
        return productoEncontrado;
    }

    @Override
    public ProductoDto eliminarProducto(Long id) throws ResourceNotFoundException {
        ProductoDto productoEliminar = null;
        productoEliminar = buscarProductoPorId(id);
        if(productoEliminar != null){
            productoRepository.deleteById(id);
            LOGGER.warn("Eliminando producto: {}", productoEliminar);
        }else{
            LOGGER.error("No existe el producto");
            throw new ResourceNotFoundException("No existe el producto en la base de datos");
        }
        return productoEliminar;
    }

    @Override
    public ProductoDto ActualizarProducto(UpdateProductoRequest request, long id) throws ResourceNotFoundException {
        Producto producto = productoRepository.findById(id).orElse(null);
        if(producto == null){
            LOGGER.error("No existe el producto");
            throw new ResourceNotFoundException("No existe el producto en la base de datos");
        }
        if(categoriaRepository.findById(request.getCategoriaId()).orElse(null) == null){
            LOGGER.error("No existe la categoria");
            throw new ResourceNotFoundException("No existe la categoria");
        }
        productoMapper.update(request, producto);
        productoRepository.save(producto);

        return productoMapper.toDto(producto);
    }
}
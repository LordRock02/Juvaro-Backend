package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.ProductoDto;
import com.backend.juvaro.dtos.requests.RegistrarProductoRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.mappers.ProductoMapper;
import com.backend.juvaro.repositories.ProductoRepository;
import com.backend.juvaro.services.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

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
        var productoGuardado = productoRepository.save(producto);
        return productoMapper.toDto(productoGuardado);
    }
}

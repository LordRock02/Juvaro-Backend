package com.backend.juvaro.services;

import com.backend.juvaro.dtos.output.ProductoDto;
import com.backend.juvaro.dtos.requests.RegistrarProductoRequest;
import com.backend.juvaro.dtos.update.UpdateProductoRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;

import java.util.List;

public interface ProductoService {
    List<ProductoDto> listarProductos() throws BadRequestException;
    Object registrarProducto(RegistrarProductoRequest request) throws BadRequestException;
    ProductoDto buscarProductoPorId(Long id) throws ResourceNotFoundException;
    ProductoDto eliminarProducto(Long id) throws ResourceNotFoundException;
    ProductoDto ActualizarProducto(UpdateProductoRequest request, long id) throws ResourceNotFoundException;
}

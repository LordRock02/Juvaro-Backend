package com.backend.juvaro.services;

import com.backend.juvaro.dtos.output.ProductoDto;
import com.backend.juvaro.dtos.requests.RegistrarProductoRequest;
import com.backend.juvaro.exceptions.BadRequestException;

import java.util.List;

public interface ProductoService {
    List<ProductoDto> listarProductos() throws BadRequestException;
    Object registrarProducto(RegistrarProductoRequest request) throws BadRequestException;
}

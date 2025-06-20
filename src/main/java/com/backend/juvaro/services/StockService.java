package com.backend.juvaro.services;

import com.backend.juvaro.dtos.output.StockDto;
import com.backend.juvaro.dtos.requests.RegistrarStockRequest;
import com.backend.juvaro.dtos.update.UpdateStockRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;

import java.util.List;

public interface StockService {
    List<StockDto> listarStocks() throws BadRequestException;
    Object resgistrarStock(RegistrarStockRequest request) throws BadRequestException;
    StockDto buscarStockPorId(Long id) throws ResourceNotFoundException;
    StockDto eliminarStock(Long id) throws ResourceNotFoundException;
    StockDto actualizarStock(Long id, UpdateStockRequest request) throws ResourceNotFoundException;
    StockDto buscarStockPorProductoYDepartamento(Long productoId, Long departamentoId) throws ResourceNotFoundException;
}

package com.backend.juvaro.services;

import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.dtos.update.UpdateCategoriaRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDto> listarCategorias() throws BadRequestException;
    Object resgistrarCategoria(RegistrarCategoriaRequest request) throws BadRequestException;
    CategoriaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException;
    CategoriaDto eliminarCategoria(Long id) throws ResourceNotFoundException;
    CategoriaDto actualizarCategoria(Long id, UpdateCategoriaRequest request) throws ResourceNotFoundException;
}

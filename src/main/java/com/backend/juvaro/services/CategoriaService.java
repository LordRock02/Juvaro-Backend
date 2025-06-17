package com.backend.juvaro.services;

import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.exceptions.BadRequestException;

import java.util.List;

public interface CategoriaService {
    List<CategoriaDto> listarCategorias() throws BadRequestException;
    Object resgistrarCategoria(RegistrarCategoriaRequest request) throws BadRequestException;
}

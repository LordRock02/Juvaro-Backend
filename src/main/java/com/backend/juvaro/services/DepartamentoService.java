package com.backend.juvaro.services;

import com.backend.juvaro.dtos.output.DepartamentoDto;
import com.backend.juvaro.dtos.requests.RegistrarDepartamentoRequest;
import com.backend.juvaro.dtos.update.UpdateDepartamentoRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;

import java.util.List;

public interface DepartamentoService {
    List<DepartamentoDto> listarDepartamentos() throws BadRequestException;
    Object resgistrarDepartamento(RegistrarDepartamentoRequest request) throws BadRequestException;
    DepartamentoDto buscarDepartamentoPorId(Long id) throws ResourceNotFoundException;
    DepartamentoDto eliminarDepartamento(Long id) throws ResourceNotFoundException;
    DepartamentoDto actualizarDepartamento(Long id, UpdateDepartamentoRequest request) throws ResourceNotFoundException;
}

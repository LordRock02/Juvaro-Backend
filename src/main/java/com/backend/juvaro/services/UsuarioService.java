package com.backend.juvaro.services;


import com.backend.juvaro.dtos.output.UsuarioDto;
import com.backend.juvaro.dtos.requests.RegistrarUsuarioRequest;
import com.backend.juvaro.dtos.requests.UsuarioLoginEntradaDto;
import com.backend.juvaro.dtos.update.UpdateUsuarioRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;

import java.util.List;

public interface UsuarioService {

    Object registrarUsuario(RegistrarUsuarioRequest request) throws BadRequestException;
    UsuarioDto iniciarSesion(UsuarioLoginEntradaDto request);
    List<UsuarioDto> listarUsuarios();
    UsuarioDto buscarUsuarioPorId(Long id) throws ResourceNotFoundException;

    UsuarioDto buscarUsuarioPorEmail(String email) throws ResourceNotFoundException;
    UsuarioDto actualizarUsuario(UpdateUsuarioRequest request) throws ResourceNotFoundException, BadRequestException;
    UsuarioDto eliminarUsuario(Long id) throws ResourceNotFoundException;
}

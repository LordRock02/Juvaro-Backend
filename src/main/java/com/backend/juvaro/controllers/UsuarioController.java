package com.backend.juvaro.controllers;


import com.backend.juvaro.dtos.output.UsuarioDto;
import com.backend.juvaro.dtos.requests.RegistrarUsuarioRequest;
import com.backend.juvaro.dtos.update.UpdateUsuarioRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.services.UsuarioService;
import com.backend.juvaro.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {
    private UsuarioService usuarioService;
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioService.class);

    @PostMapping("/registrar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistrarUsuarioRequest usuario) throws BadRequestException {
        LOGGER.info("Inicia registro");
        LOGGER.info("Usuario request: "+ JsonPrinter.toString(usuario));
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuario), HttpStatus.OK);
    }

    @PutMapping("actualizar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UsuarioDto> actualizarUsuario(@Valid @RequestBody UpdateUsuarioRequest usuario) throws ResourceNotFoundException, BadRequestException {
        return new ResponseEntity<>(usuarioService.actualizarUsuario(usuario), HttpStatus.OK);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<List<UsuarioDto>> listarUsuarios() {
        return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<UsuarioDto> obtenerUsuarioPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.buscarUsuarioPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("eliminar/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> eliminar(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(usuarioService.eliminarUsuario(id), HttpStatus.OK);
    }


}

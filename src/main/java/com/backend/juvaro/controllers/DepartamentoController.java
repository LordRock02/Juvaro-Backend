package com.backend.juvaro.controllers;

import com.backend.juvaro.dtos.output.DepartamentoDto;
import com.backend.juvaro.dtos.requests.RegistrarDepartamentoRequest;
import com.backend.juvaro.dtos.update.UpdateDepartamentoRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.services.DepartamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@AllArgsConstructor
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @GetMapping
    public ResponseEntity<List<DepartamentoDto>> getAllDepartamentos() throws BadRequestException {
        return new ResponseEntity<>(departamentoService.listarDepartamentos(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarDepartamento(@RequestBody RegistrarDepartamentoRequest request) throws BadRequestException {
        return new ResponseEntity<>(departamentoService.resgistrarDepartamento(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartamentoDto> buscarDepartamento(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(departamentoService.buscarDepartamentoPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DepartamentoDto> actualizarDepartamento(@PathVariable Long id, @RequestBody UpdateDepartamentoRequest request) throws ResourceNotFoundException {
        return new ResponseEntity<>(departamentoService.actualizarDepartamento(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarDepartamento(@PathVariable Long id) throws ResourceNotFoundException {
        departamentoService.eliminarDepartamento(id);
        return new ResponseEntity<>("Departamento eliminada con éxito", HttpStatus.OK);
    }

}


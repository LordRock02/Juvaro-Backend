package com.backend.juvaro.controllers;

import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.dtos.update.UpdateCategoriaRequest;
import com.backend.juvaro.entities.Categoria;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.services.CategoriaService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@AllArgsConstructor
public class CategoriaController {

    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<CategoriaDto>> getAllCategorias() throws BadRequestException {
        return new ResponseEntity<>(categoriaService.listarCategorias(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarCategoria(@RequestBody RegistrarCategoriaRequest request) throws BadRequestException {
        return new ResponseEntity<>(categoriaService.resgistrarCategoria(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaDto> buscarCategoria(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoriaService.buscarCategoriaPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaDto> actualizarCategoria(@PathVariable Long id, @RequestBody UpdateCategoriaRequest request) throws ResourceNotFoundException {
        return new ResponseEntity<>(categoriaService.actualizarCategoria(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCategoria(@PathVariable Long id) throws ResourceNotFoundException {
        categoriaService.eliminarCategoria(id);
        return new ResponseEntity<>("Categoria eliminada con éxito", HttpStatus.OK);
    }

}


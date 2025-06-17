package com.backend.juvaro.controllers;

import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.entities.Categoria;
import com.backend.juvaro.exceptions.BadRequestException;
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

    @GetMapping("/registrar")
    public ResponseEntity<?> registrarCategoria(@RequestParam RegistrarCategoriaRequest request) throws BadRequestException {
        return new ResponseEntity<>(categoriaService.resgistrarCategoria(request), HttpStatus.OK);
    }

}


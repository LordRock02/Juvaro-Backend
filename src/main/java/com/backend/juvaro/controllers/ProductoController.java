package com.backend.juvaro.controllers;

import com.backend.juvaro.dtos.output.ProductoDto;
import com.backend.juvaro.dtos.requests.RegistrarProductoRequest;
import com.backend.juvaro.dtos.update.UpdateProductoRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.services.ProductoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
@AllArgsConstructor
public class ProductoController {
    private final ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<ProductoDto>> getAllProductos() throws  BadRequestException {
        return new ResponseEntity<>(productoService.listarProductos(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarProducto(@RequestBody RegistrarProductoRequest request) throws BadRequestException {
        return new ResponseEntity<>(productoService.registrarProducto(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductoDto> ObtenerRecurso(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productoService.buscarProductoPorId(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(productoService.eliminarProducto(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductoDto> modificarProducto(@PathVariable Long id, @RequestBody UpdateProductoRequest request) throws ResourceNotFoundException {
        return new ResponseEntity<>(productoService.ActualizarProducto(request,id),HttpStatus.OK);
    }
}

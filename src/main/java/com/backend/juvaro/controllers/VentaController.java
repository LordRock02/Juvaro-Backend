package com.backend.juvaro.controllers;

import com.backend.juvaro.dtos.output.VentaDto;
import com.backend.juvaro.dtos.requests.RegistrarVentaRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.services.VentaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
@AllArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping("/registrar")
    public ResponseEntity<VentaDto> registrarVenta(@RequestBody RegistrarVentaRequest request) throws BadRequestException, ResourceNotFoundException {
        VentaDto ventaCreada = ventaService.crearVenta(request);
        return new ResponseEntity<>(ventaCreada, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<VentaDto>> listarTodasLasVentas() throws BadRequestException {
        List<VentaDto> ventas = ventaService.listarVentas();
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaDto> obtenerVentaPorId(@PathVariable Long id) throws ResourceNotFoundException {
        VentaDto venta = ventaService.obtenerVentaPorId(id);
        return new ResponseEntity<>(venta, HttpStatus.OK);
    }

    @GetMapping("/buscarVentaUsuario/{id}")
    public ResponseEntity<List<VentaDto>> obtenerVentaPorUsuario(@PathVariable Long id) throws ResourceNotFoundException {
        List<VentaDto> ventas = ventaService.buscarVentasPorUsuario(id);
        return new ResponseEntity<>(ventas, HttpStatus.OK);
    }
}

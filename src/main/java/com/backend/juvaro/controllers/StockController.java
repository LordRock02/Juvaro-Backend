package com.backend.juvaro.controllers;

import com.backend.juvaro.dtos.output.StockDto;
import com.backend.juvaro.dtos.requests.RegistrarStockRequest;
import com.backend.juvaro.dtos.update.UpdateStockRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.services.StockService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@AllArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public ResponseEntity<List<StockDto>> getAllStocks() throws BadRequestException {
        return new ResponseEntity<>(stockService.listarStocks(), HttpStatus.OK);
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarStock(@RequestBody RegistrarStockRequest request) throws BadRequestException {
        return new ResponseEntity<>(stockService.resgistrarStock(request), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDto> buscarStock(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(stockService.buscarStockPorId(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StockDto> actualizarStock(@PathVariable Long id, @RequestBody UpdateStockRequest request) throws ResourceNotFoundException {
        return new ResponseEntity<>(stockService.actualizarStock(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarStock(@PathVariable Long id) throws ResourceNotFoundException {
        stockService.eliminarStock(id);
        return new ResponseEntity<>("Stock eliminada con éxito", HttpStatus.OK);
    }

    @GetMapping("/buscar")
    public ResponseEntity<StockDto> buscarStockPorProductoYDepartamento(
            @RequestParam Long productoId,
            @RequestParam Long departamentoId) throws ResourceNotFoundException {
        StockDto stockEncontrado = stockService.buscarStockPorProductoYDepartamento(productoId, departamentoId);
        return new ResponseEntity<>(stockEncontrado, HttpStatus.OK);
    }

}


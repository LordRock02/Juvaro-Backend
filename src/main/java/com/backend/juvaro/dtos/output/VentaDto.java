package com.backend.juvaro.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class VentaDto {
    private long id;
    private Long usuarioId;
    private String nombreUsuario;
    private LocalDate fecha;
    private double valorTotal;
    private List<DetalleVentaDto> detalles;
}
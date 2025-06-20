package com.backend.juvaro.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class DetalleVentaDto {
    private Long id;
    private Long productoId;
    private String nombreProducto;
    private Long departamentoId;
    private String nombreDepartamento;
    private int cantidad;
    private double precioUnitario;
}

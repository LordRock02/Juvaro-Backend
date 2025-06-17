package com.backend.juvaro.dtos.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateProductoRequest {
    private String nombre;
    private String descripcion;
    private double precio;
    private Long categoriaId;
}

package com.backend.juvaro.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductoDto {
    private long id;
    private long categoriaId;
    private String nombre;
    private String descripcion;
    private double precio;
}

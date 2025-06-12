package com.backend.juvaro.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ProductoDto {
    private long id;
    private CategoriaDto categoria;
    private String nombre;
    private String descripcion;
    private double precio;
}

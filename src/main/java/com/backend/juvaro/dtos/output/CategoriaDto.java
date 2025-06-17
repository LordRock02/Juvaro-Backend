package com.backend.juvaro.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoriaDto {
    private long id;
    private String nombre;
    private String descripcion;
}

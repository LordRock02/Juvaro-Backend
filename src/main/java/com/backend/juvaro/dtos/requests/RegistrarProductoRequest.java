package com.backend.juvaro.dtos.requests;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegistrarProductoRequest {
    String nombre;
    String descripcion;
    String imagenUrl;
    double precio;
    long categoriaId;

}

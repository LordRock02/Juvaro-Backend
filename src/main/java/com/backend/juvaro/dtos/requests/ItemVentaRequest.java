package com.backend.juvaro.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ItemVentaRequest {
    private Long productoId;
    private Long departamentoId;
    private int cantidad;
}
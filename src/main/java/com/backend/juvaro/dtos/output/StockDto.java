package com.backend.juvaro.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class StockDto {
    private int id;
    private int cantidad;
    private Long productoId;
    private Long departamentoId;
}

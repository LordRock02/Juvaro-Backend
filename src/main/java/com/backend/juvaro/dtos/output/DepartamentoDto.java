package com.backend.juvaro.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class DepartamentoDto {
    private long id;
    private String nombre;
    private List<StockDto> stocks;
}

package com.backend.juvaro.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class RegistrarVentaRequest {
    private Long usuarioId;
    private List<ItemVentaRequest> items;
}

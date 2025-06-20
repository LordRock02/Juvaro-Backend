package com.backend.juvaro.dtos.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UpdateStockRequest {
    private int cantidad;
}

package com.backend.juvaro.mappers;

import com.backend.juvaro.dtos.output.StockDto;
import com.backend.juvaro.dtos.requests.RegistrarStockRequest;
import com.backend.juvaro.dtos.update.UpdateStockRequest;
import com.backend.juvaro.entities.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StockMapper {
    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "departamento.id", target = "departamentoId")
    StockDto toDto(Stock stock);

    @Mapping(source = "productoId", target = "producto.id")
    @Mapping(source = "departamentoId", target = "departamento.id")
    Stock toEntity(RegistrarStockRequest request);

    void update(UpdateStockRequest request, @MappingTarget Stock stock);
}

package com.backend.juvaro.mappers;

import com.backend.juvaro.dtos.output.ProductoDto;
import com.backend.juvaro.dtos.requests.RegistrarProductoRequest;
import com.backend.juvaro.entities.Producto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    ProductoDto toDto(Producto producto);
    Producto toEntity(RegistrarProductoRequest request);
}

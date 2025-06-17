package com.backend.juvaro.mappers;


import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.entities.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaDto toDto(Categoria categoria);
    Categoria toEntity(RegistrarCategoriaRequest request);
}

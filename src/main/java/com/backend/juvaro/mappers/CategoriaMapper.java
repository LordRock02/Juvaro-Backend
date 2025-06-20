package com.backend.juvaro.mappers;


import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.dtos.update.UpdateCategoriaRequest;
import com.backend.juvaro.entities.Categoria;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {
    CategoriaMapper INSTANCE = Mappers.getMapper(CategoriaMapper.class);
    CategoriaDto toDto(Categoria categoria);
    Categoria toEntity(RegistrarCategoriaRequest request);
    Categoria update(UpdateCategoriaRequest request, @MappingTarget Categoria categoria);
}

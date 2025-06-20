package com.backend.juvaro.mappers;

import com.backend.juvaro.dtos.output.DepartamentoDto;
import com.backend.juvaro.dtos.requests.RegistrarDepartamentoRequest;
import com.backend.juvaro.dtos.update.UpdateDepartamentoRequest;
import com.backend.juvaro.entities.Departamento;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DepartamentoMapper {
    DepartamentoMapper INSTANCE = Mappers.getMapper(DepartamentoMapper.class);
    DepartamentoDto toDto(Departamento departamento);
    Departamento toEntity(RegistrarDepartamentoRequest request);
    Departamento update(UpdateDepartamentoRequest request, @MappingTarget Departamento departamento);
}

package com.backend.juvaro.mappers;


import com.backend.juvaro.dtos.output.DetalleVentaDto;
import com.backend.juvaro.entities.DetalleVenta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DetalleVentaMapper {

    @Mapping(source = "producto.id", target = "productoId")
    @Mapping(source = "producto.nombre", target = "nombreProducto")
    @Mapping(source = "departamento.id", target = "departamentoId")
    @Mapping(source = "departamento.nombre", target = "nombreDepartamento")
    DetalleVentaDto toDto(DetalleVenta detalleVenta);
}
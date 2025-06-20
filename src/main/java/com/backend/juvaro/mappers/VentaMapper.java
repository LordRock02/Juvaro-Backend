package com.backend.juvaro.mappers;

import com.backend.juvaro.dtos.output.VentaDto;
import com.backend.juvaro.dtos.requests.RegistrarVentaRequest;
import com.backend.juvaro.entities.Venta;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {DetalleVentaMapper.class}) // Le decimos que puede usar otros mappers
public interface VentaMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "usuario.fullname", target = "nombreUsuario")
    VentaDto toDto(Venta venta);

}

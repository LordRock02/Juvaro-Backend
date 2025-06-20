package com.backend.juvaro.mappers;

import com.backend.juvaro.dtos.output.UsuarioDto;
import com.backend.juvaro.dtos.requests.RegistrarUsuarioRequest;
import com.backend.juvaro.dtos.update.UpdateUsuarioRequest;
import com.backend.juvaro.entities.Usuario;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UsuarioMapper {

    /**
     * Convierte un DTO de registro a una entidad Usuario.
     * @param request El DTO con los datos de registro.
     * @return Una nueva entidad Usuario.
     */
    Usuario toEntity(RegistrarUsuarioRequest request);

    /**
     * Convierte una entidad Usuario a su DTO de salida.
     * @param usuario La entidad a convertir.
     * @return El DTO con los datos públicos del usuario.
     */
    UsuarioDto toDto(Usuario usuario);

    /**
     * Actualiza una entidad Usuario existente a partir de un DTO de modificación.
     * El @MappingTarget le dice a MapStruct que no cree una nueva instancia, sino que modifique la existente.
     * Ignora los campos nulos del DTO para no sobrescribir datos existentes con nulos.
     * @param dto El DTO con los campos a actualizar.
     * @param usuario La entidad a modificar (obtenida de la base de datos).
     */
    void updateUserFromDto(UpdateUsuarioRequest dto, @MappingTarget Usuario usuario);
}
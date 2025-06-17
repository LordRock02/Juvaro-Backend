package com.backend.juvaro.mappers;

import com.backend.juvaro.dtos.output.ProductoDto;
import com.backend.juvaro.dtos.requests.RegistrarProductoRequest;
import com.backend.juvaro.dtos.update.UpdateProductoRequest;
import com.backend.juvaro.entities.Categoria;
import com.backend.juvaro.entities.Producto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ProductoMapper {
    @Mapping(source = "categoria.id", target = "categoriaId")
    ProductoDto toDto(Producto producto);
    @Mapping(source = "categoriaId", target = "categoria.id")
    Producto toEntity(RegistrarProductoRequest request);
    @Mapping(target = "categoria", expression = "java(mapCategoria(request.getCategoriaId()))")
    Producto update(UpdateProductoRequest request, @MappingTarget Producto producto);

    default Categoria mapCategoria(Long id) {
        if (id == null) return null;
        Categoria c = new Categoria();
        c.setId(id);
        return c;
    }
}

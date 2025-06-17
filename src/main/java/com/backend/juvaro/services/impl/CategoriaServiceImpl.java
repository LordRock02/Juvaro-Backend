package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.dtos.update.UpdateCategoriaRequest;
import com.backend.juvaro.entities.Categoria;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.mappers.CategoriaMapper;
import com.backend.juvaro.repositories.CategoriaRepository;
import com.backend.juvaro.services.CategoriaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private final Logger LOGGER = LoggerFactory.getLogger(CategoriaServiceImpl.class);

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;

    @Override
    public List<CategoriaDto> listarCategorias() throws BadRequestException {
        return categoriaRepository.findAll()
                .stream()
                .map(categoriaMapper::toDto)
                .toList();
    }

    @Override
    public Object resgistrarCategoria(RegistrarCategoriaRequest request) throws BadRequestException {
        var categoria = categoriaMapper.toEntity(request);
        var categoriaGuardada = categoriaRepository.save(categoria);
        LOGGER.info("Categoria guardada con exito {}", categoriaGuardada);
        return categoriaGuardada;
    }

    @Override
    public CategoriaDto buscarCategoriaPorId(Long id) throws ResourceNotFoundException {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        CategoriaDto categoriaEncontrada = null;
        if(categoria != null){
            categoriaEncontrada = categoriaMapper.toDto(categoria);
            LOGGER.info("Buscando producto: {}", categoriaEncontrada);
        }
        else {
            LOGGER.error("Categoria no encontrada");
            throw new ResourceNotFoundException("Categoria no encontrada");
        }

        return categoriaEncontrada;
    }

    @Override
    public CategoriaDto eliminarCategoria(Long id) throws ResourceNotFoundException {
        CategoriaDto categoriaEliminar = null;
        categoriaEliminar = buscarCategoriaPorId(id);
        if(categoriaEliminar != null){
            categoriaRepository.deleteById(id);
            LOGGER.warn("Eliminando categoria: {}", categoriaEliminar);
        }else{
            LOGGER.error("No existe la categoria");
            throw new ResourceNotFoundException("No existe la categoria en la base de datos");
        }
        return categoriaEliminar;
    }

    @Override
    public CategoriaDto actualizarCategoria(Long id, UpdateCategoriaRequest request) throws ResourceNotFoundException {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        if(categoria == null){
            LOGGER.error("No existe la categoria");
            throw new ResourceNotFoundException("No existe la categoria en la base de datos");
        }


        categoriaMapper.update(request, categoria);

        categoriaRepository.save(categoria);

        return categoriaMapper.toDto(categoria);
    }
}

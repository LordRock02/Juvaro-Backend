package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.CategoriaDto;
import com.backend.juvaro.dtos.requests.RegistrarCategoriaRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.mappers.CategoriaMapper;
import com.backend.juvaro.repositories.CategoriaRepository;
import com.backend.juvaro.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

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
        categoriaRepository.save(categoria);

        return null;
    }
}

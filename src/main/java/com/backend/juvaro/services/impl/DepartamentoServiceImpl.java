package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.DepartamentoDto;
import com.backend.juvaro.dtos.requests.RegistrarDepartamentoRequest;
import com.backend.juvaro.dtos.update.UpdateDepartamentoRequest;
import com.backend.juvaro.entities.Departamento;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.mappers.DepartamentoMapper;
import com.backend.juvaro.repositories.DepartamentoRepository;
import com.backend.juvaro.services.DepartamentoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    private final Logger LOGGER = LoggerFactory.getLogger(DepartamentoServiceImpl.class);

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Autowired
    private DepartamentoMapper departamentoMapper;

    @Override
    @Transactional(readOnly = true)
    public List<DepartamentoDto> listarDepartamentos() throws BadRequestException {
        List<Departamento> departamentos = departamentoRepository.findAllWithStocksAndProducts();

        return departamentos.stream()
                .map(departamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Object resgistrarDepartamento(RegistrarDepartamentoRequest request) throws BadRequestException {
        var departamento = departamentoMapper.toEntity(request);
        var departamentoGuardada = departamentoRepository.save(departamento);
        LOGGER.info("Departamento guardada con exito {}", departamentoGuardada);
        return departamentoGuardada;
    }

    @Override
    public DepartamentoDto buscarDepartamentoPorId(Long id) throws ResourceNotFoundException {
        Departamento departamento = departamentoRepository.findById(id).orElse(null);
        DepartamentoDto departamentoEncontrada = null;
        if(departamento != null){
            departamentoEncontrada = departamentoMapper.toDto(departamento);
            LOGGER.info("Buscando producto: {}", departamentoEncontrada);
        }
        else {
            LOGGER.error("Departamento no encontrada");
            throw new ResourceNotFoundException("Departamento no encontrada");
        }

        return departamentoEncontrada;
    }

    @Override
    public DepartamentoDto eliminarDepartamento(Long id) throws ResourceNotFoundException {
        DepartamentoDto departamentoEliminar = null;
        departamentoEliminar = buscarDepartamentoPorId(id);
        if(departamentoEliminar != null){
            departamentoRepository.deleteById(id);
            LOGGER.warn("Eliminando departamento: {}", departamentoEliminar);
        }else{
            LOGGER.error("No existe la departamento");
            throw new ResourceNotFoundException("No existe la departamento en la base de datos");
        }
        return departamentoEliminar;
    }

    @Override
    public DepartamentoDto actualizarDepartamento(Long id, UpdateDepartamentoRequest request) throws ResourceNotFoundException {
        Departamento departamento = departamentoRepository.findById(id).orElse(null);
        if(departamento == null){
            LOGGER.error("No existe la departamento");
            throw new ResourceNotFoundException("No existe la departamento en la base de datos");
        }


        departamentoMapper.update(request, departamento);

        departamentoRepository.save(departamento);

        return departamentoMapper.toDto(departamento);
    }
}

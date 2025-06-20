package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.StockDto;
import com.backend.juvaro.dtos.requests.RegistrarStockRequest;
import com.backend.juvaro.dtos.update.UpdateStockRequest;
import com.backend.juvaro.entities.Stock;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.mappers.StockMapper;
import com.backend.juvaro.repositories.StockRepository;
import com.backend.juvaro.services.StockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    private final Logger LOGGER = LoggerFactory.getLogger(StockServiceImpl.class);

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private StockMapper stockMapper;

    @Override
    public List<StockDto> listarStocks() throws BadRequestException {
        return stockRepository.findAll()
                .stream()
                .map(stockMapper::toDto)
                .toList();
    }

    @Override
    public Object resgistrarStock(RegistrarStockRequest request) throws BadRequestException {
        var stock = stockMapper.toEntity(request);
        var stockGuardada = stockRepository.save(stock);
        LOGGER.info("Stock guardada con exito {}", stockGuardada);
        return stockRepository.findById(stockGuardada.getId()).orElse(null);
    }

    @Override
    public StockDto buscarStockPorId(Long id) throws ResourceNotFoundException {
        Stock stock = stockRepository.findById(id).orElse(null);
        StockDto stockEncontrada = null;
        if(stock != null){
            stockEncontrada = stockMapper.toDto(stock);
            LOGGER.info("Buscando producto: {}", stockEncontrada);
        }
        else {
            LOGGER.error("Stock no encontrada");
            throw new ResourceNotFoundException("Stock no encontrada");
        }

        return stockEncontrada;
    }

    @Override
    public StockDto eliminarStock(Long id) throws ResourceNotFoundException {
        StockDto stockEliminar = null;
        stockEliminar = buscarStockPorId(id);
        if(stockEliminar != null){
            stockRepository.deleteById(id);
            LOGGER.warn("Eliminando stock: {}", stockEliminar);
        }else{
            LOGGER.error("No existe la stock");
            throw new ResourceNotFoundException("No existe la stock en la base de datos");
        }
        return stockEliminar;
    }

    @Override
    public StockDto actualizarStock(Long id, UpdateStockRequest request) throws ResourceNotFoundException {
        Stock stock = stockRepository.findById(id).orElse(null);
        if(stock == null){
            LOGGER.error("No existe la stock");
            throw new ResourceNotFoundException("No existe la stock en la base de datos");
        }


        stockMapper.update(request, stock);

        stockRepository.save(stock);

        return stockMapper.toDto(stock);
    }
}

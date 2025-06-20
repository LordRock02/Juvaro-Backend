package com.backend.juvaro.repositories;

import com.backend.juvaro.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductoIdAndDepartamentoId(Long productoId, Long departamentoId);
}

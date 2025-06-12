package com.backend.juvaro.repositories;

import com.backend.juvaro.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Integer> {
}

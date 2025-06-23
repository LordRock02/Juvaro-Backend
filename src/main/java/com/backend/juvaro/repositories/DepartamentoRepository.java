package com.backend.juvaro.repositories;

import com.backend.juvaro.entities.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {
    @Query("SELECT DISTINCT d FROM Departamento d LEFT JOIN FETCH d.stocks s LEFT JOIN FETCH s.producto")
    List<Departamento> findAllWithStocksAndProducts();
}

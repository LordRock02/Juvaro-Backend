package com.backend.juvaro.repositories;

import com.backend.juvaro.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}

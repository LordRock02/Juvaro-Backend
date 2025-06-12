package com.backend.juvaro.repositories;

import com.backend.juvaro.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

package com.backend.juvaro.repositories;

import com.backend.juvaro.entities.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
    @Query("SELECT v FROM Venta v LEFT JOIN FETCH v.detalles WHERE v.usuario.id = :usuarioId")
    List<Venta> findByUsuarioId(@Param("usuarioId") Long usuarioId);
}

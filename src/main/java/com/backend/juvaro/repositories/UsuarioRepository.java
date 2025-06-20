package com.backend.juvaro.repositories;

import com.backend.juvaro.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {
    @Query("select u from Usuario u where u.email = ?1 and u.password = ?2")
    Usuario findByEmail(String email,String contraseña);
    Optional<Usuario> findOneByEmail(String email);
    List<Usuario> findByCedula(int cedula);
}
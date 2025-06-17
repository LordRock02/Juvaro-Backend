package com.backend.juvaro.dtos.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioDto {
    private long id;
    private String fullname;
    private String email;
    private String password;
    private String cedula;
    private LocalDate fechaRegistro;
    private int rol;

}

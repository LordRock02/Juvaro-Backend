package com.backend.juvaro.dtos.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUsuarioRequest {

    private Long id;
    private String fullname;
    private String password;
    private String email;
    private int cedula;
    private LocalDate fechaRegistro;
    private int rol;
}

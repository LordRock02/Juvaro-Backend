package com.backend.juvaro.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioLoginEntradaDto {
    //@Email
    @NotBlank(message = "Debe especificarse el correo del usuario")
    private String email;
    @NotBlank(message = "Debe especificarse la contraseña del usuario")
    private String password;
}

package com.backend.juvaro.controllers;


import com.backend.juvaro.dtos.output.UsuarioDto;
import com.backend.juvaro.dtos.requests.RegistrarUsuarioRequest;
import com.backend.juvaro.dtos.requests.UsuarioLoginEntradaDto;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.security.AuthCredentials;
import com.backend.juvaro.security.TokenUtils;
import com.backend.juvaro.security.UserDetailsImpl;
import com.backend.juvaro.services.UsuarioService;
import com.backend.juvaro.utils.JsonPrinter;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);
    private final UsuarioService usuarioService;

    @PostMapping("/login")

    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody AuthCredentials authCredentials) {
        System.out.println("Inicia login");
        try {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(authCredentials.getEmail(), authCredentials.getPassword());

            UserDetailsImpl userDetails = (UserDetailsImpl) authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            String token = TokenUtils.createToken(userDetails.getNombre(), userDetails.getUsername());

            UsuarioLoginEntradaDto usuarioEntradaDto = new UsuarioLoginEntradaDto(authCredentials.getEmail(),authCredentials.getPassword());
            UsuarioDto usuarioDB = usuarioService.iniciarSesion(usuarioEntradaDto);

            LOGGER.info("Usuario DB: "+ JsonPrinter.toString(usuarioDB));

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("nombre", userDetails.getUsername());
            response.put("usuario", usuarioDB);

            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);

            return new ResponseEntity<>(response, headers, HttpStatus.OK);

        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody RegistrarUsuarioRequest usuario) throws BadRequestException {
        LOGGER.info("Inicia registro");
        LOGGER.info("Usuario request: "+ JsonPrinter.toString(usuario));
        return new ResponseEntity<>(usuarioService.registrarUsuario(usuario), HttpStatus.OK);
    }
}

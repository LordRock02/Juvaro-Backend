package com.backend.juvaro.services.impl;

import com.backend.juvaro.dtos.output.UsuarioDto;
import com.backend.juvaro.dtos.requests.RegistrarUsuarioRequest;
import com.backend.juvaro.dtos.requests.UsuarioLoginEntradaDto;
import com.backend.juvaro.dtos.update.UpdateUsuarioRequest;
import com.backend.juvaro.entities.Usuario;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;
import com.backend.juvaro.mappers.UsuarioMapper;
import com.backend.juvaro.repositories.UsuarioRepository;
import com.backend.juvaro.services.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioServiceImpl.class);

    // Se utiliza inyección de dependencias por campo con @Autowired.
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioMapper usuarioMapper;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // No se necesita un constructor explícito para la inyección de dependencias.

    @Override
    @Transactional
    public UsuarioDto registrarUsuario(RegistrarUsuarioRequest request) throws BadRequestException {
        // ... (Esta lógica ya estaba correcta)
        if (usuarioRepository.findOneByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("El correo electrónico ya se encuentra registrado.");
        }
        if (!usuarioRepository.findByCedula(request.getCedula()).isEmpty()) {
            throw new BadRequestException("La cédula ya se encuentra registrada.");
        }

        Usuario nuevoUsuario = usuarioMapper.toEntity(request);
        nuevoUsuario.setPassword(passwordEncoder.encode(request.getPassword()));
        // nuevoUsuario.setRol("CLIENTE"); // Asumiendo que tu entidad ya tiene el campo rol como String

        Usuario usuarioGuardado = usuarioRepository.save(nuevoUsuario);
        return usuarioMapper.toDto(usuarioGuardado);
    }

    @Override
    public UsuarioDto iniciarSesion(UsuarioLoginEntradaDto request) {
        // ... (Esta lógica ya estaba correcta y segura)
        Usuario usuario = usuarioRepository.findOneByEmail(request.getEmail()).orElse(null);
        if (usuario != null && passwordEncoder.matches(request.getPassword(), usuario.getPassword())) {
            return usuarioMapper.toDto(usuario);
        } else {
            return null;
        }
    }

    @Override
    public List<UsuarioDto> listarUsuarios() {
        return usuarioRepository.findAll().stream().map(usuarioMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public UsuarioDto buscarUsuarioPorId(Long id) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario con el ID: " + id));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    public UsuarioDto buscarUsuarioPorEmail(String email) throws ResourceNotFoundException {
        Usuario usuario = usuarioRepository.findOneByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró un usuario con el email: " + email));
        return usuarioMapper.toDto(usuario);
    }

    @Override
    @Transactional
    public UsuarioDto actualizarUsuario(UpdateUsuarioRequest request) throws ResourceNotFoundException, BadRequestException {
        Long id = request.getId();
        Usuario usuarioAActualizar = usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el usuario a actualizar con ID: " + id));

        // Lógica de validación mejorada para evitar el try-catch en la lambda
        Optional<Usuario> usuarioConEmailOpt = usuarioRepository.findOneByEmail(request.getEmail());
        if (usuarioConEmailOpt.isPresent() && !usuarioConEmailOpt.get().getId().equals(id)) {
            throw new BadRequestException("El correo electrónico ya está en uso por otro usuario.");
        }

        List<Usuario> usuariosConCedula = usuarioRepository.findByCedula(request.getCedula());
        if (!usuariosConCedula.isEmpty() && !usuariosConCedula.get(0).getId().equals(id)) {
            throw new BadRequestException("La cédula ya está en uso por otro usuario.");
        }

        usuarioMapper.updateUserFromDto(request, usuarioAActualizar);
        Usuario usuarioActualizado = usuarioRepository.save(usuarioAActualizar);
        return usuarioMapper.toDto(usuarioActualizado);
    }

    @Override
    public UsuarioDto eliminarUsuario(Long id) throws ResourceNotFoundException {
        UsuarioDto usuarioAEliminar = buscarUsuarioPorId(id);
        usuarioRepository.deleteById(id);
        return usuarioAEliminar;
    }
}

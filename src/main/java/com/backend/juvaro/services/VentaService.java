package com.backend.juvaro.services;

import com.backend.juvaro.dtos.output.VentaDto;
import com.backend.juvaro.dtos.requests.RegistrarVentaRequest;
import com.backend.juvaro.exceptions.BadRequestException;
import com.backend.juvaro.exceptions.ResourceNotFoundException;

import java.util.List;

public interface VentaService {

    /**
     * Se han añadido dos correcciones:
     * 1. El tipo de retorno ahora es VentaDto, que es más específico y útil que Object.
     * 2. Se ha añadido 'throws ResourceNotFoundException' para que coincida con la implementación.
     */
    VentaDto crearVenta(RegistrarVentaRequest request) throws BadRequestException, ResourceNotFoundException;

    List<VentaDto> listarVentas() throws BadRequestException;

    VentaDto obtenerVentaPorId(Long id) throws ResourceNotFoundException;

    // Aquí irían los métodos para los suscriptores si sigues el diseño del publisher dedicado.
    // Por ejemplo:
    // void subscribe(NotificadorSubscriber subscriber);
    // void unsubscribe(NotificadorSubscriber subscriber);
}
package com.backend.juvaro.services.notificaciones.mensajes;

import com.backend.juvaro.entities.Venta;

public record VentaRealizadaMensaje(Venta venta) implements Mensaje {
}

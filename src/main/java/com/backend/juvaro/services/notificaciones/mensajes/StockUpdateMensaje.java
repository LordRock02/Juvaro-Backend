package com.backend.juvaro.services.notificaciones.mensajes;

public record StockUpdateMensaje(Long productoId, int nuevaCantidad) implements Mensaje{
}

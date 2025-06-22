package com.backend.juvaro.services.notificaciones.factory;

import com.backend.juvaro.entities.Venta;
import com.backend.juvaro.services.notificaciones.mensajes.Mensaje;

public interface MensajeFactory {
    Mensaje crearStockUpdateMensaje(Long productoId, int nuevaCantidad);
    Mensaje crearVentaRealizadaMensaje(Venta venta);
}

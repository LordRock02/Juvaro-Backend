package com.backend.juvaro.services.notificaciones.factory;

import com.backend.juvaro.entities.Venta;
import com.backend.juvaro.services.notificaciones.mensajes.Mensaje;
import com.backend.juvaro.services.notificaciones.mensajes.StockUpdateMensaje;
import com.backend.juvaro.services.notificaciones.mensajes.VentaRealizadaMensaje;
import org.springframework.stereotype.Component;

@Component
public class MensajeFactoryImpl implements MensajeFactory {
    @Override
    public Mensaje crearStockUpdateMensaje(Long productoId, int nuevaCantidad) {
        return new StockUpdateMensaje(productoId, nuevaCantidad);
    }

    @Override
    public Mensaje crearVentaRealizadaMensaje(Venta venta) {
        return new VentaRealizadaMensaje(venta);
    }
}

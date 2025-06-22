package com.backend.juvaro.services.notificaciones.observer;

import com.backend.juvaro.services.notificaciones.mensajes.Mensaje;

public interface NotificadorSubscriber {
    void notificar (Mensaje mensaje);
}

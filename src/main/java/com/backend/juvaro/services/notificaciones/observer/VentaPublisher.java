package com.backend.juvaro.services.notificaciones.observer;

import com.backend.juvaro.services.notificaciones.mensajes.Mensaje;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class VentaPublisher {


    private final List<NotificadorSubscriber> subscribers = new CopyOnWriteArrayList<>();

    public void subscribe(NotificadorSubscriber subscriber) {
        System.out.println("PUBLISHER: Registrando nuevo suscriptor: " + subscriber.getClass().getSimpleName());
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(NotificadorSubscriber subscriber) {
        System.out.println("PUBLISHER: Eliminando suscriptor: " + subscriber.getClass().getSimpleName());
        this.subscribers.remove(subscriber);
    }


    public void notificar(Mensaje mensaje) {
        System.out.println("----------------------------------------");
        System.out.println("PUBLISHER: Recibí una solicitud para notificar un mensaje de tipo " + mensaje.getClass().getSimpleName());
        System.out.println("PUBLISHER: Enviando a " + subscribers.size() + " suscriptores...");
        for (NotificadorSubscriber subscriber : subscribers) {
            subscriber.notificar(mensaje);
        }
    }
}
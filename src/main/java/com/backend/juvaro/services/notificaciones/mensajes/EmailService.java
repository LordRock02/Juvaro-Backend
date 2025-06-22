package com.backend.juvaro.services.notificaciones.mensajes;

public interface EmailService {
    /**
     * Envía un correo electrónico simple.
     * @param to El destinatario del correo.
     * @param subject El asunto del correo.
     * @param text El cuerpo del correo en texto plano.
     */
    void sendSimpleMessage(String to, String subject, String text);
}

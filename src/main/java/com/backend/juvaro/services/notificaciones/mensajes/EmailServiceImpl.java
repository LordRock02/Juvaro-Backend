package com.backend.juvaro.services.notificaciones.mensajes;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor // Usa Lombok para generar el constructor con las dependencias.
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender emailSender;

    // No se necesita un constructor manual gracias a @AllArgsConstructor

    @Override
    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("empresa.juvaro@gmail.com"); // Puedes poner un remitente fijo
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);

            emailSender.send(message);
            System.out.println(">> Correo enviado exitosamente a: " + to);

        } catch (Exception e) {
            // En un proyecto real, aquí deberías usar un logger.
            System.err.println("Error al enviar correo a " + to + ": " + e.getMessage());
        }
    }
}

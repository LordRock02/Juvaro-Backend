package com.backend.juvaro.services.notificaciones.observer;

import com.backend.juvaro.entities.Usuario;
import com.backend.juvaro.repositories.UsuarioRepository;
import com.backend.juvaro.services.notificaciones.mensajes.EmailService;
import com.backend.juvaro.services.notificaciones.mensajes.Mensaje;
import com.backend.juvaro.services.notificaciones.mensajes.StockUpdateMensaje;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminNotificationService implements NotificadorSubscriber {

    private final VentaPublisher ventaPublisher;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService; // Asumimos que tienes un servicio para enviar correos

    public AdminNotificationService(VentaPublisher ventaPublisher, UsuarioRepository usuarioRepository, EmailService emailService) {
        this.ventaPublisher = ventaPublisher;
        this.usuarioRepository = usuarioRepository;
        this.emailService = emailService;
    }

    /**
     * El método init se ejecuta después de que Spring crea esta clase.
     * Lo usamos para que el servicio se suscriba a sí mismo al Publisher.
     */
    @PostConstruct
    public void init() {
        ventaPublisher.subscribe(this);
    }

    /**
     * Este es el método que llama el Publisher.
     * Aquí, la clase filtra el mensaje para ver si le interesa.
     */
    @Override
    public void notificar(Mensaje mensaje) {
        // Solo reacciona si el mensaje es del tipo que le importa: StockUpdateMensaje
        if (mensaje instanceof StockUpdateMensaje stockMensaje) {

            // Revisa la lógica de negocio (si el stock es bajo)
            if (stockMensaje.nuevaCantidad() < 20) {
                System.out.println("SUSCRIPTOR (Admin): Stock bajo detectado. Notificando a los administradores...");

                List<Usuario> admins = usuarioRepository.findByRol(1);
                String subject = "Alerta de Stock Bajo - JUVARO S.A.";
                String text = "¡Atención! El stock del producto con ID " + stockMensaje.productoId() + " ha caído a " + stockMensaje.nuevaCantidad() + " unidades.";

                for (Usuario admin : admins) {
                    emailService.sendSimpleMessage(admin.getEmail(), subject, text);
                }
            }
        }
    }
}


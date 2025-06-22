package com.backend.juvaro.services.notificaciones.observer;


import com.backend.juvaro.entities.DetalleVenta;
import com.backend.juvaro.entities.Venta;
import com.backend.juvaro.services.notificaciones.mensajes.EmailService;
import com.backend.juvaro.services.notificaciones.mensajes.Mensaje;
import com.backend.juvaro.services.notificaciones.mensajes.VentaRealizadaMensaje;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class FacturacionService implements NotificadorSubscriber {

    private final VentaPublisher ventaPublisher;
    private final EmailService emailService;

    public FacturacionService(VentaPublisher ventaPublisher, EmailService emailService) {
        this.ventaPublisher = ventaPublisher;
        this.emailService = emailService;
    }

    @PostConstruct
    public void init() {
        ventaPublisher.subscribe(this);
    }

    @Override
    public void notificar(Mensaje mensaje) {
        // Solo reacciona si el mensaje es del tipo que le importa: VentaRealizadaMensaje
        if (mensaje instanceof VentaRealizadaMensaje ventaMensaje) {
            System.out.println("SUSCRIPTOR (Facturación): Venta completada. Generando factura...");

            Venta venta = ventaMensaje.venta();
            String emailCliente = venta.getUsuario().getEmail();
            String subject = "Factura de su compra en JUVARO S.A. - Venta #" + venta.getId();
            String body = buildInvoiceBody(venta); // Método para construir el cuerpo del correo

            emailService.sendSimpleMessage(emailCliente, subject, body);
        }
    }

    private String buildInvoiceBody(Venta venta) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        sb.append("¡Gracias por su compra en JUVARO S.A.!\n\n");
        sb.append("Resumen de su Factura\n");
        sb.append("========================\n");
        sb.append("Factura N°: ").append(venta.getId()).append("\n");
        sb.append("Cliente: ").append(venta.getUsuario().getFullname()).append("\n");
        sb.append("Fecha: ").append(venta.getFecha().format(formatter)).append("\n");
        sb.append("========================\n\n");
        sb.append("Detalle de la compra:\n");

        for (DetalleVenta detalle : venta.getDetalles()) {
            double subtotal = detalle.getCantidad() * detalle.getPrecioUnitario();
            sb.append(String.format("- Producto: %s\n", detalle.getProducto().getNombre()));
            sb.append(String.format("  Cantidad: %d\n", detalle.getCantidad()));
            sb.append(String.format("  Precio Unitario: $%.2f\n", detalle.getPrecioUnitario()));
            sb.append(String.format("  Subtotal: $%.2f\n", subtotal));
            sb.append("\n");
        }

        sb.append("========================\n");
        sb.append(String.format("TOTAL A PAGAR: $%.2f\n", venta.getValorTotal()));
        sb.append("========================\n\n");
        sb.append("Gracias por preferirnos.\n");

        return sb.toString();
    }
}
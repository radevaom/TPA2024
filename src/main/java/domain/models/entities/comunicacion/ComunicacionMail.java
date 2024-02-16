package domain.models.entities.comunicacion;

import lombok.Getter;
import lombok.Setter;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Getter
@Setter
public class ComunicacionMail implements MedioComunicacion {

    //TODO HACER QUE ESTO NO ESTE HARCODEADO
    private String correoElectronico = "dds2023tpa@gmail.com";
    private String contrasenia = "hdqymsctldkbgygu";

    @Override
    public void comunicar(Notificacion notificacion, String mailDestinatario) {

        Session conexion = configurarConexionGmail();
        try {
            MimeMessage message = new MimeMessage(conexion);
            message.setFrom(new InternetAddress(this.correoElectronico));
            agregarDestinatario(message, mailDestinatario);//////////////////////
            //contactos.forEach(contacto -> agregarDestinatario(message, contacto.getEmail()));
            message.setSubject(notificacion.getAsunto());
            message.setSentDate(new Date());
            message.setText(notificacion.getMensaje());

            Transport t = conexion.getTransport("smtp");
            t.connect(this.correoElectronico, this.contrasenia);
            t.sendMessage(message, message.getAllRecipients());
            t.close();

        } catch (Exception e) {
            System.out.println("Hubo un problema en el proceso de notificación: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private Session configurarConexionGmail() {
        // Propiedades de la conexión
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", this.correoElectronico);
        props.setProperty("mail.smtp.auth", "true");

        return Session.getDefaultInstance(props);
    }

    private void agregarDestinatario(MimeMessage message, String email) {
        try {
            message.addRecipient(
                Message.RecipientType.TO,
                new InternetAddress(email));
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
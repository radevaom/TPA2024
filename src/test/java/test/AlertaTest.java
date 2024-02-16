package test;

import domain.models.entities.comunicacion.MedioComunicacion;
import domain.models.entities.comunicacion.Notificacion;
import domain.models.entities.comunicacion.TipoAlerta;

public class AlertaTest extends TipoAlerta {
    private String alertText;

    public void alertar(Notificacion notificacion, MedioComunicacion medioComunicacion, String mailDestinatario) {
        this.alertText = String.format("%s: %s", notificacion.getAsunto(), notificacion.getMensaje());
        //System.out.printf("%s: %s", mensaje, incidente.mensaje());
    }

    public String getAlertText() {
        return alertText;
    }
}

package domain.models.entities.comunicacion;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "cuando_sucede")
public class AlertaCuandoSucede extends TipoAlerta{


    public void alertar(Notificacion notificacion, MedioComunicacion medioComunicacion, String mailDestinatario) {
        medioComunicacion.comunicar(notificacion, mailDestinatario);
    }
}

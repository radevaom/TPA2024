package domain.models.entities.comunicacion;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ComunicacionWhatsapp implements MedioComunicacion{
    private String telefono;
    @Override
    public void comunicar(Notificacion notificacion, String mailDestinatario) {
        //TODO sender
    }
}

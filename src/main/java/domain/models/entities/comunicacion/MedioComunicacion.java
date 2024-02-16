package domain.models.entities.comunicacion;

public interface MedioComunicacion {
    void comunicar(Notificacion notificacion, String mailDestinatario);
}

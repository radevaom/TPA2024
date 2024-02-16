package domain.models.entities.converters;

import domain.models.entities.comunicacion.ComunicacionMail;
import domain.models.entities.comunicacion.ComunicacionWhatsapp;
import domain.models.entities.comunicacion.MedioComunicacion;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter(autoApply = true)
public class MedioComunicacionConverter implements AttributeConverter<MedioComunicacion, String> {
    @Override
    public String convertToDatabaseColumn(MedioComunicacion medioComunicacion) {
        return medioComunicacion != null ? medioComunicacion.getClass().getSimpleName() : null;
    }

    @Override
    public MedioComunicacion convertToEntityAttribute(String medioComunicacion) {
        if(medioComunicacion == null) return null;
        return switch (medioComunicacion) {
            case "ComunicacionWhatsapp" -> new ComunicacionWhatsapp();
            case "ComunicacionMail" -> new ComunicacionMail();
            default-> null;
        };
    }
}

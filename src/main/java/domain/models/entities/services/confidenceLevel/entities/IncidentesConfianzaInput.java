package domain.models.entities.services.confidenceLevel.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class IncidentesConfianzaInput {

    private String nombreServicio;
    private String comunidad;
    private Long idServicio;
    private String fechaApertura;
    private String fechaCierre;
    private String usuarioCreador;
    private String usuarioCierre;
}

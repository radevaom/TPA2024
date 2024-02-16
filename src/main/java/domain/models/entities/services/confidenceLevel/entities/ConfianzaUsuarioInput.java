package domain.models.entities.services.confidenceLevel.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConfianzaUsuarioInput {
    private String idUsuario;
    private Double gradoConfianza;
    private List<IncidentesConfianzaInput> incidentes;

}

package domain.models.entities.services.confidenceLevel.entities;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ConfianzaComunidadInput {
    private String id;
    private List<ConfianzaUsuarioInput> miembros;
}

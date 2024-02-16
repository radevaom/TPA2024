package domain.models.entities.services.confidenceLevel.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ConfianzaUsuarioOutput {
    @JsonProperty("newConfidenceLevel")
    private Double newConfidenceLevel;
}

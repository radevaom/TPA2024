package domain.models.entities.services.confidenceLevel.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class ConfianzaComunidadOutput {
    @JsonProperty("confidence")
    private Double confidence;
}

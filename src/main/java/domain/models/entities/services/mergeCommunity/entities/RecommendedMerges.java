package domain.models.entities.services.mergeCommunity.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class RecommendedMerges {

    private List<PossibleMerge> possibleMerges;
}

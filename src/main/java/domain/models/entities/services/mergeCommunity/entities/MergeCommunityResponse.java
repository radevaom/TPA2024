package domain.models.entities.services.mergeCommunity.entities;

import lombok.Getter;

import java.util.List;

@Getter
public class MergeCommunityResponse {
    private List<FusingCommunity> mergedCommunity;
}

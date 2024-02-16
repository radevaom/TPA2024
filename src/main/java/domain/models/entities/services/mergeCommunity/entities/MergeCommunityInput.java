package domain.models.entities.services.mergeCommunity.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class MergeCommunityInput {
    private FusingCommunity community1;
    private FusingCommunity community2;

    public MergeCommunityInput(FusingCommunity community1, FusingCommunity community2) {
        this.community1 = community1;
        this.community2 = community2;
    }
}

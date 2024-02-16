package domain.models.entities.services.mergeCommunity.entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class FusingCommunityMember {

    private String id;
    private String name;

    public FusingCommunityMember(String id, String name) {
        this.id = id;
        this.name = name;
    }

}

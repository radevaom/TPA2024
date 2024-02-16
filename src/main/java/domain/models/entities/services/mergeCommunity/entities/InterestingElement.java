package domain.models.entities.services.mergeCommunity.entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class InterestingElement {

    private String id;
    private String name;

    public InterestingElement(String id, String name){
        this.id = id;
        this.name = name;
    }

}

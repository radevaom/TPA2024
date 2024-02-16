package domain.models.entities.services.mergeCommunity.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Date;


@Setter @Getter
public class FusingCommunity {
    private String id;
    private String name;
    private String lastTimeMerged;
    private Double degreeOfConfidence;
    private List<FusingCommunityMember> members;

    private List<InterestingElement> interestingServices;
    private List<InterestingElement> interestingEstablishments;
}


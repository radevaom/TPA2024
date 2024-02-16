package domain.models.entities.services.mergeCommunity;

import domain.models.entities.services.confidenceLevel.entities.ConfianzaComunidadInput;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaComunidadOutput;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaUsuarioInput;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaUsuarioOutput;
import domain.models.entities.services.mergeCommunity.entities.FusingCommunity;
import domain.models.entities.services.mergeCommunity.entities.MergeCommunityInput;
import domain.models.entities.services.mergeCommunity.entities.MergeCommunityResponse;
import domain.models.entities.services.mergeCommunity.entities.RecommendedMerges;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import java.util.List;

public interface MergeCommunityImpl {

    @POST("api/recommendations")
    Call<RecommendedMerges> recommendMerges(@Body List<FusingCommunity> input);


    @POST("api/fusion")
    Call<MergeCommunityResponse> mergeCommunities(@Body MergeCommunityInput input);


}

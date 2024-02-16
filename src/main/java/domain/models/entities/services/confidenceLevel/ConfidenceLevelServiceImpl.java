package domain.models.entities.services.confidenceLevel;

import domain.models.entities.services.confidenceLevel.entities.ConfianzaComunidadInput;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaComunidadOutput;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaUsuarioInput;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaUsuarioOutput;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ConfidenceLevelServiceImpl {

    @POST("user_confidence")
    Call<ConfianzaUsuarioOutput> userConfidence(@Body ConfianzaUsuarioInput input);


    @POST("community_confidence")
    Call<ConfianzaComunidadOutput> communityConfidence(@Body ConfianzaComunidadInput input);


}

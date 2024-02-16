package domain.models.entities.services.mergeCommunity;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.services.mergeCommunity.entities.*;
import domain.models.entities.servicio.Servicio;
import domain.models.utils.Utils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MergeCommunityService {
    private static MergeCommunityService instancia = null;

    private static final String urlAPI = "https://service-01-merge-community-utn-production.up.railway.app/";

    private Retrofit retrofit;

    private MergeCommunityService(){

        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MergeCommunityService getInstance(){
        if(instancia == null){
            instancia = new MergeCommunityService();
        }
        return instancia;
    }


    public RecommendedMerges obtenerPosiblesFusiones(List<Comunidad> comunidades) throws IOException {
        MergeCommunityImpl mergeCommunityService = this.retrofit.create(MergeCommunityImpl.class);

        List<FusingCommunity> communities = mapearComunidadAMergeInput(comunidades);

        Call<RecommendedMerges> confianzaOutputCall = mergeCommunityService.recommendMerges(communities);

        Response<RecommendedMerges> output = confianzaOutputCall.execute();
        return output.body();
    }

    public MergeCommunityResponse fusionarComunidades(Comunidad comunidad1, Comunidad comunidad2) throws IOException {
        MergeCommunityImpl mergeCommunityService = this.retrofit.create(MergeCommunityImpl.class);

        List<FusingCommunity> communities = mapearComunidadAMergeInput(List.of(comunidad1, comunidad2));


        Call<MergeCommunityResponse> confianzaOutputCall = mergeCommunityService.mergeCommunities(new MergeCommunityInput(communities.get(0), communities.get(1)));

        Response<MergeCommunityResponse> output = confianzaOutputCall.execute();
        return output.body();
    }


    private List<FusingCommunity> mapearComunidadAMergeInput(List<Comunidad> comunidades) {
        return comunidades.stream().map(comunidad -> {
            FusingCommunity community = new FusingCommunity();
            community.setName(comunidad.getNombre());
            community.setId(comunidad.getId().toString());
            Set<InterestingElement> intestingServicess = new HashSet<InterestingElement>();
            Set<InterestingElement> interestingEstablishmentss = new HashSet<InterestingElement>();

            comunidad.getIncidentesReportados().forEach(incidente -> {
                Servicio servicio = incidente.getServicio();
                InterestingElement interestingService = new InterestingElement(servicio.getId().toString(), servicio.getNombre());
                intestingServicess.add(interestingService);
                interestingEstablishmentss.add(new InterestingElement(servicio.getEstacion().getId().toString(), servicio.getEstacion().getNombre()));
            });
            community.setInterestingServices(intestingServicess.stream().toList());
            community.setInterestingEstablishments(interestingEstablishmentss.stream().toList());
            community.setMembers(comunidad.getMiembros().stream().map(miembro ->
                    new FusingCommunityMember(miembro.getId().toString(), miembro.getPersona().getNombre())
            ).toList());
            community.setDegreeOfConfidence(comunidad.getGradoConfianza());
            community.setLastTimeMerged(Utils.formatLocalDateTimeToString(comunidad.getFechaUltimaFusion()));
            return community;
        }).toList();
    }


}

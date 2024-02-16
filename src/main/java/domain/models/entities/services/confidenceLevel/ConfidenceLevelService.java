package domain.models.entities.services.confidenceLevel;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.Excluder;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.services.confidenceLevel.entities.*;
import domain.models.entities.services.georef.GeorefService;
import domain.models.entities.services.georef.ServicioGeoref;
import domain.models.entities.services.georef.entities.ListadoDeProvincias;
import domain.models.entities.usuario.Usuario;
import domain.models.utils.Utils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ConfidenceLevelService {
    private static ConfidenceLevelService instancia = null;

    private static final String urlAPI = "http://localhost:3000/";

    private Retrofit retrofit;

    private ConfidenceLevelService(){

        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()))
                .build();
    }

    public static ConfidenceLevelService getInstance(){
        if(instancia == null){
            instancia = new ConfidenceLevelService();
        }
        return instancia;
    }

    public ConfianzaUsuarioOutput obtenerGradoDeConfianzaDeUsuario(Usuario usuario, List<Incidente> incidentesSemanales) throws IOException {
        ConfianzaUsuarioInput confianzaUsuario = new ConfianzaUsuarioInput();
        confianzaUsuario.setGradoConfianza(usuario.getPuntosConfianza());
        confianzaUsuario.setIdUsuario(usuario.getId().toString());

        List<IncidentesConfianzaInput> incidentes = incidentesSemanales.stream().map(incidente -> {
            IncidentesConfianzaInput incidenteDeConfianza = new IncidentesConfianzaInput();
            incidenteDeConfianza.setNombreServicio(incidente.getServicio().getNombre());
            incidenteDeConfianza.setComunidad(incidente.getComunidad().getId().toString());
            incidenteDeConfianza.setIdServicio(incidente.getServicio().getId());
            incidenteDeConfianza.setFechaApertura(Utils.formatLocalDateTimeToString(incidente.getFecha()));
            incidenteDeConfianza.setFechaCierre(Utils.formatLocalDateTimeToString(incidente.getCierre()));
            incidenteDeConfianza.setUsuarioCreador(incidente.getUsuarioInformador().getId().toString());
            incidenteDeConfianza.setUsuarioCierre(incidente.getUsuarioCierre().getId().toString());
            return incidenteDeConfianza;
        }).toList();

        confianzaUsuario.setIncidentes(incidentes);

        ConfidenceLevelServiceImpl confidenceLevelService = this.retrofit.create(ConfidenceLevelServiceImpl.class);
        Call<ConfianzaUsuarioOutput> confianzaOutputCall = confidenceLevelService.userConfidence(confianzaUsuario);

        Response<ConfianzaUsuarioOutput> output = confianzaOutputCall.execute();
        return output.body();
    }

    public ConfianzaComunidadOutput obtenerGradoDeConfianzaDeComunidad(Comunidad comunidad) throws IOException {
        ConfianzaComunidadInput confianzaComunidadInput = new ConfianzaComunidadInput();
        List<ConfianzaUsuarioInput> miembros = comunidad.getMiembros().stream().map(miembro -> {
            Usuario usuario = miembro.getPersona().getUsuario();
            ConfianzaUsuarioInput confianzaUsuario = new ConfianzaUsuarioInput();
            confianzaUsuario.setGradoConfianza(usuario.getPuntosConfianza());
            confianzaUsuario.setIdUsuario(usuario.getId().toString());
            return confianzaUsuario;
        }).toList();

        confianzaComunidadInput.setId(comunidad.getId().toString());
        confianzaComunidadInput.setMiembros(miembros);

        ConfidenceLevelServiceImpl confidenceLevelService = this.retrofit.create(ConfidenceLevelServiceImpl.class);
        Call<ConfianzaComunidadOutput> confianzaOutputCall = confidenceLevelService.communityConfidence(confianzaComunidadInput);

        Response<ConfianzaComunidadOutput> output = confianzaOutputCall.execute();
        return output.body();
    }




}

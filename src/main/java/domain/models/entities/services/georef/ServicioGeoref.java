package domain.models.entities.services.georef;

import domain.models.entities.services.georef.entities.ListadoDeMunicipios;
import domain.models.entities.services.georef.entities.ListadoDeProvincias;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServicioGeoref {
    private static ServicioGeoref instancia = null;

    private static final String urlAPI = "https://apis.datos.gob.ar/georef/api/";

    private Retrofit retrofit;

    private ServicioGeoref(){
        this.retrofit = new Retrofit.Builder()
                .baseUrl(urlAPI)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static ServicioGeoref getInstance(){
        if(instancia == null){
            instancia = new ServicioGeoref();
        }
        return instancia;
    }

    public ListadoDeProvincias listadoDeProvincias() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeProvincias> requestProvinciasArg = georefService.provincias();
        Response<ListadoDeProvincias> responseProvinciasArg = requestProvinciasArg.execute();
        return responseProvinciasArg.body();
    }

    public ListadoDeMunicipios listadoDeMunicipios(Long id) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosDeProvincia = georefService.todosLosMunicipiosDeUnaProvincia(id, 200);
        Response<ListadoDeMunicipios> responseMunicipiosDeProvincia = requestMunicipiosDeProvincia.execute();
        return responseMunicipiosDeProvincia.body();
    }

    public ListadoDeMunicipios listadoDeTodosLosMunicipios() throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosDeProvincia = georefService.todosLosMunicipiosDeArgentina(2000);
        Response<ListadoDeMunicipios> responseMunicipiosDeProvincia = requestMunicipiosDeProvincia.execute();
        return responseMunicipiosDeProvincia.body();
    }


    public ListadoDeProvincias listadoDeUnaProvincia(int id) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeProvincias> requestProvinciasArg = georefService.provincia(id);
        Response<ListadoDeProvincias> responseProvinciasArg = requestProvinciasArg.execute();
        return responseProvinciasArg.body();
    }

    public ListadoDeMunicipios listadoDeUnMunicipio(String id) throws IOException {
        GeorefService georefService = this.retrofit.create(GeorefService.class);
        Call<ListadoDeMunicipios> requestMunicipiosDeProvincia = georefService.municipio(id);
        Response<ListadoDeMunicipios> responseMunicipiosDeProvincia = requestMunicipiosDeProvincia.execute();
        return responseMunicipiosDeProvincia.body();
    }

}

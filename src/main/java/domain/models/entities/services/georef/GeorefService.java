package domain.models.entities.services.georef;

import domain.models.entities.services.georef.entities.ListadoDeMunicipios;
import domain.models.entities.services.georef.entities.ListadoDeProvincias;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GeorefService {

    //TODAS LAS PROVINCIAS
    @GET("provincias")
    Call<ListadoDeProvincias> provincias();

    //TODAS LAS PROVINCIAS CON LOS CAMPOS QUE LE PIDO
    @GET("provincias")
    Call<ListadoDeProvincias> provincias(@Query("campos") String campos);


    //UNA PROVINCIAS SEGUN ID
    @GET("provincias")
    Call<ListadoDeProvincias> provincia(@Query("id") int id);





    //TODOS LOS MUNICIPIOS
    @GET("municipios")
    Call<ListadoDeMunicipios> todosLosMunicipiosDeArgentina(@Query("max") int max);

    //10  MUNICIPIOS DE UNA  PROVINCIA QUE LE PIDO
    @GET("municipios")
    Call<ListadoDeMunicipios> municipiosDeUnaProvincia(@Query("provincia") int provincia);


    //TODOS LOS MUNICIPIOS DE UNA  PROVINCIA QUE LE PIDO
    @GET("municipios")
    Call<ListadoDeMunicipios> todosLosMunicipiosDeUnaProvincia(@Query("provincia") Long provincia, @Query("max") int max);

    //TODOS LOS MUNICIPIOS DE UNA PROVINCIA QUE LE PIDO CON LOS CAMPOS QUE LE PIDO
    @GET("municipios")
    Call<ListadoDeMunicipios> municipios(@Query("provincia") int provincia, @Query("campos") String campos, @Query("max") int max);

    //UN MUNICIPIO SEGUN ID
    @GET("municipios")
    Call<ListadoDeMunicipios> municipio(@Query("id") String id);

}

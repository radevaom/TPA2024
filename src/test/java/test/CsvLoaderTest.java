package test;

import domain.models.entities.services.servicioCSV.CsvLoader;
import domain.models.entities.serviciospublicos.entidad.TipoTransporte;
import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvLoaderTest {
    protected  CsvLoader csvLoader = new CsvLoader();

    private void initializeCsvLoader(){
        this.csvLoader = new CsvLoader();
    }


    @BeforeEach
    public void initialize() {
        this.initializeCsvLoader();
    }

    @AfterEach
    public void clean(){

    }


    @Test
    void cargarCSV() {
        EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublico = Common.getEntidadPrestadoraServicioPublico();
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.cargarCSVEntdadPrestadora("./src/main/resources/csv/prueba2.csv", entidadPrestadoraServicioPublico);

        Assertions.assertEquals(3, entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().size());
        Assertions.assertEquals("NOMBRE1", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(0).getNombre());
        Assertions.assertEquals("NOMBRE2", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(1).getNombre());
        Assertions.assertEquals("NOMBRE3", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(2).getNombre());

        Assertions.assertEquals("CHACO", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(0).getLocalizacion().getNombre());
        Assertions.assertEquals("LANUS", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(1).getLocalizacion().getNombre());
        Assertions.assertEquals("SAN JUAN", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(2).getLocalizacion().getNombre());


        TransportePublico transportePublico2 = (TransportePublico) entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(0);
        Assertions.assertEquals(TipoTransporte.SUBTERRANEO, transportePublico2.getTipoTransporte());

    }

    @Test
    void cargarCSVOrganismosDeControl() {
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.cargarCSVOrganismoDeControl2("./src/main/resources/csv/prueba3.csv");
    }

    @Test
    void cargarCSVEntdadPrestadora() {
        CsvLoader csvLoader = new CsvLoader();
        csvLoader.cargarCSVOrganismoDeControl2("./src/main/resources/csv/prueba4.csv");
    }
}


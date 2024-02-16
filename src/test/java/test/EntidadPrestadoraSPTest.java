package test;

import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.services.georef.entities.Provincia;
import domain.models.entities.serviciospublicos.entidad.TipoTransporte;
import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.serviciospublicos.establecimiento.Sucursal;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import domain.models.entities.usuario.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntidadPrestadoraSPTest {

    protected EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublico;
    protected Usuario usuarioTest;

    private void initializeEntidadPrestadoraServicioPublico(){
        this.entidadPrestadoraServicioPublico =  Common.getEntidadPrestadoraServicioPublico();
        this.usuarioTest = new Usuario("unUsername", null, entidadPrestadoraServicioPublico);
    }


    @BeforeEach
    public void initialize() {
        this.initializeEntidadPrestadoraServicioPublico();
    }

    @AfterEach
    public void clean(){

    }

    @Test
    void sePuedeCrearUnaAgrupacion() {
        Assertions.assertEquals("unNombre", entidadPrestadoraServicioPublico.getNombre());
        Assertions.assertEquals("unUsername", usuarioTest.getUsername());
    }

    @Test
    void sePuedenAgregarServiciosPublicos() {

        Localizacion localizacion = new Provincia("AAAA");
        Sucursal sucursal = new Sucursal(localizacion);
        entidadPrestadoraServicioPublico.agregarServicioOfrecido(sucursal);

        Assertions.assertEquals(1, entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().size());
        Assertions.assertEquals(localizacion, entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(0).getLocalizacion());
    }

    @Test
    void sePuedenRemoverServiciosPublicos() {

        Localizacion localizacion = new Provincia("AAAA");
        Sucursal sucursal = new Sucursal(localizacion);
        entidadPrestadoraServicioPublico.agregarServicioOfrecido(sucursal);
        entidadPrestadoraServicioPublico.removerServicioOfrecido(sucursal);

        Assertions.assertEquals(0, entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().size());

    }

    @Test
    void sePuedenCargarEntidadesConCSV() {

        entidadPrestadoraServicioPublico.cargarArchivoCSV("./src/main/resources/csv/prueba2.csv");

        Assertions.assertEquals(3, entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().size());
        Assertions.assertEquals("NOMBRE1", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(0).getNombre());
        Assertions.assertEquals("NOMBRE2", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(1).getNombre());
        Assertions.assertEquals("NOMBRE3", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(2).getNombre());

        Assertions.assertEquals("CHACO", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(0).getLocalizacion().getNombre());
        Assertions.assertEquals("LANUS", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(1).getLocalizacion().getNombre());
        Assertions.assertEquals("SAN JUAN", entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(2).getLocalizacion().getNombre());


        TransportePublico transPublic = (TransportePublico) entidadPrestadoraServicioPublico.getServiciosPublicosOfrecidos().get(0);
        Assertions.assertEquals(TipoTransporte.SUBTERRANEO, transPublic.getTipoTransporte());
    }

}

package test;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.servicio.Agrupacion;
import domain.models.entities.servicio.Servicio;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComunidadTest {
    protected Comunidad comunidadTest;
    protected Incidente incidente = Common.getIncidente();
    private void initializecomunidadTest(){
        this.comunidadTest =  Common.getComunidad();
    }

    @BeforeEach
    public void initialize() {
        this.initializecomunidadTest();
    }

    @AfterEach
    public void clean(){

    }

    @Test
    void sePuedeCrearUnaComunidad() {
        Assertions.assertEquals("nombreComunidad", comunidadTest.getNombre());
    }


    @Test
    void sePuedeCrearServicios() {
       Servicio servicio = comunidadTest.crearServicio("unServicio",true,"Baño");
        Assertions.assertEquals("unServicio", servicio.getNombre());
    }

    @Test
    void sePuedeCrearAgrupaciones() {
        List<Servicio> servicios = Common.getListaDeServicioS();
        Agrupacion agrupacion = comunidadTest.crearAgrupacion("unaAgrupacion", servicios);
        Assertions.assertEquals("unaAgrupacion", agrupacion.getNombre());
        Assertions.assertEquals(servicios, agrupacion.getServicios());
    }

    @Test
    public void alertarNuevoIncidente() {
        comunidadTest.agregarMiembro(Common.getMiembroConMail(Common.getComunidad()));
        comunidadTest.agregarIncidente(Common.getIncidente());
    }

    @Test
    public void alertarIncidenteCerrado(){
        comunidadTest.agregarMiembro(Common.getMiembroConMail(Common.getComunidad()));
        Incidente incidente = Common.getIncidente();
        comunidadTest.agregarIncidente(incidente);
        comunidadTest.cerrarIncidente(incidente);
    }

    @Test
    public void seNotificaAlMiembroDeIncidentesCercanos(){
        incidente.getServicio().setEstacion(Common.estacionAlem());
        comunidadTest.agregarMiembro(Common.getMiembroConMail(comunidadTest));
        comunidadTest.agregarIncidente(incidente);
        comunidadTest.agregarIncidente(Common.getIncidenteEnEstacionAlem());
        List<Incidente> incidentesCercanosAUnaUbicacion =
            comunidadTest.encontrarIncidentesCercanos(Common.getCentroide1());
        Assertions.assertTrue(incidentesCercanosAUnaUbicacion.contains(incidente));
        Assertions.assertFalse(incidentesCercanosAUnaUbicacion.contains(Common.getIncidente2()));
    }
}

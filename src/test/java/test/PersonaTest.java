package test;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import domain.models.entities.comunicacion.AlertaCuandoSucede;
import domain.models.entities.comunicacion.AlertaSinApuro;
import domain.models.entities.comunicacion.ComunicacionMail;
import domain.models.entities.comunicacion.Notificacion;
import domain.models.entities.usuario.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.miembro.MiembroAdministrador;
import domain.models.entities.miembro.MiembroNormal;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.serviciospublicos.entidad.Entidad;
import domain.models.entities.usuario.Persona;

public class PersonaTest {
    protected Persona personaTest;
    protected Usuario usuarioTest;
    private void initializePersona(){
        this.personaTest =  Common.getPersona();
        this.usuarioTest = new Usuario("UsernamePersona", null, personaTest);
    }

    @BeforeEach
    public void initialize() {
        this.initializePersona();
    }

    @AfterEach
    public void clean(){

    }

    @Test
    void sePuedeCrearUnOrganismoDeControl() {
        Assertions.assertEquals("UsernamePersona", usuarioTest.getUsername());
        Assertions.assertEquals("EmailPersona", personaTest.getEmail());
        Assertions.assertEquals("NombrePersona", personaTest.getNombre());
        Assertions.assertEquals("ApellidoPersona", personaTest.getApellido());
    }

    @Test
    void sePuedeUnirAComunidad() {
        Comunidad comunidad = Common.getComunidad();
        Comunidad comunidad2 = Common.getComunidad2();

        personaTest.unirseComunidad(comunidad);
        personaTest.unirseComunidad(comunidad2);

        Assertions.assertEquals(2, personaTest.getMiembros().size());
        Assertions.assertEquals("nombreComunidad", personaTest.getMiembros().get(0).getComunidad().getNombre());
        Assertions.assertEquals(comunidad2, personaTest.getMiembros().get(1).getComunidad());
    }

    @Test
    void sePuedeAbandonarComunidad() {
        Comunidad comunidad = Common.getComunidad();
        Comunidad comunidad2 = Common.getComunidad2();

        personaTest.unirseComunidad(comunidad);
        personaTest.unirseComunidad(comunidad2);

        personaTest.abandonarComunidad(comunidad);

        Assertions.assertEquals(1, personaTest.getMiembros().size());
        Assertions.assertEquals("nombreComunidad2", personaTest.getMiembros().get(0).getComunidad().getNombre());
        Assertions.assertEquals(comunidad2, personaTest.getMiembros().get(0).getComunidad());
    }

    @Test
    void sePuedenAgregarEntidades() {
        Entidad transportePublico = Common.getSubteB();
        Entidad sucursal = Common.getBancoNacion();
        Entidad estacion = Common.estacionAlem();

        personaTest.agregarEntidad(transportePublico);
        personaTest.agregarEntidad(sucursal);
        personaTest.agregarEntidad(estacion);

        Assertions.assertEquals(3, personaTest.getEntidadesAsociadas().size());
        Assertions.assertEquals("Subte B", personaTest.getEntidadesAsociadas().get(0).getNombre());
        Assertions.assertEquals("Banco Nación", personaTest.getEntidadesAsociadas().get(1).getNombre());
        Assertions.assertEquals("Alem", personaTest.getEntidadesAsociadas().get(2).getNombre());
    }

    @Test
    void sePuedenEliminarEntidades() {
        Entidad transportePublico = Common.getSubteB();
        Entidad sucursal = Common.getBancoNacion();
        Entidad estacion = Common.estacionAlem();

        personaTest.agregarEntidad(transportePublico);
        personaTest.agregarEntidad(sucursal);
        personaTest.agregarEntidad(estacion);

        personaTest.eliminarEntidad(transportePublico);

        Assertions.assertEquals(2, personaTest.getEntidadesAsociadas().size());
        Assertions.assertEquals("Banco Nación", personaTest.getEntidadesAsociadas().get(0).getNombre());
        Assertions.assertEquals("Alem", personaTest.getEntidadesAsociadas().get(1).getNombre());
    }

    @Test
    void sePuedenAgregarServicios() {
        Servicio servicio1 = Common.getServicio1();
        Servicio servicio2 = Common.getServicio2();
        Servicio servicio3 = Common.getServicio3();

        personaTest.agregarServico(servicio1);
        personaTest.agregarServico(servicio2);
        personaTest.agregarServico(servicio3);

        Assertions.assertEquals(3, personaTest.getServiciosAsociados().size());
        Assertions.assertEquals("nombreServicio1", personaTest.getServiciosAsociados().get(0).getNombre());
        Assertions.assertEquals("nombreServicio2", personaTest.getServiciosAsociados().get(1).getNombre());
        Assertions.assertEquals("nombreServicio3", personaTest.getServiciosAsociados().get(2).getNombre());
    }

    @Test
    void sePuedenEliminarServicios() {
        Servicio servicio1 = Common.getServicio1();
        Servicio servicio2 = Common.getServicio2();
        Servicio servicio3 = Common.getServicio3();

        personaTest.agregarServico(servicio1);
        personaTest.agregarServico(servicio2);
        personaTest.agregarServico(servicio3);

        personaTest.eliminarServicio(servicio2);

        Assertions.assertEquals(2, personaTest.getServiciosAsociados().size());
        Assertions.assertEquals("nombreServicio1", personaTest.getServiciosAsociados().get(0).getNombre());
        Assertions.assertEquals("nombreServicio3", personaTest.getServiciosAsociados().get(1).getNombre());
    }

    @Test
    void crearComunidad() {
        personaTest.crearComunidad("NuevaComunidad");

        Assertions.assertEquals(1, personaTest.getMiembros().size());
        Assertions.assertEquals("NuevaComunidad", personaTest.getMiembros().get(0).getComunidad().getNombre());
    }

    @Test
    void crearComunidad2() {
        personaTest.crearComunidad("NuevaComunidad");

        Comunidad comunidad = Common.getComunidad();
        Comunidad comunidad2 = Common.getComunidad2();

        personaTest.unirseComunidad(comunidad);
        personaTest.unirseComunidad(comunidad2);

        List<Miembro> miembrosNormales = personaTest.getMiembros().stream()
                .filter(miembro -> (miembro instanceof MiembroNormal)).toList();

        List<Miembro> miembrosAdministradores = personaTest.getMiembros().stream()
                .filter(miembro -> (miembro instanceof MiembroAdministrador)).toList();

        Assertions.assertEquals(3, personaTest.getMiembros().size());
        Assertions.assertEquals(2, miembrosNormales.size());
        Assertions.assertEquals(1, miembrosAdministradores.size());
        Assertions.assertEquals("NuevaComunidad", personaTest.getMiembros().get(0).getComunidad().getNombre());
        Assertions.assertEquals("nombreComunidad", personaTest.getMiembros().get(1).getComunidad().getNombre());
    }

    @Test
    public void recibirNotificacionCuandoSucede(){
        personaTest.setEmail("dds2023tpa@gmail.com");
        personaTest.setMedioComunicacionPreferido(new ComunicacionMail());
        this.personaTest.setTipoAlerta(new AlertaCuandoSucede());
        Notificacion notificacion = new Notificacion("ASUNTO3", "CUERPO3");
        Notificacion notificacion2 = new Notificacion("ASUNTO4", "CUERPO4");
        personaTest.recibirNotificacion(notificacion);
        personaTest.recibirNotificacion(notificacion2);
    }

    @Test
    public void recibirNotificacionSinApuro(){
        personaTest.setEmail("dds2023tpa@gmail.com");
        ComunicacionMail comunicacionMail = new ComunicacionMail();
        personaTest.setMedioComunicacionPreferido(comunicacionMail);

        List<LocalTime> horarios = new ArrayList<>();
        horarios.add(LocalTime.of(8, 10));
        horarios.add(LocalTime.of(21, 42));
        horarios.add(LocalTime.of(13, 14));
        horarios.add(LocalTime.of(10, 52));
        this.personaTest.setTipoAlerta(new AlertaSinApuro(horarios));
        Notificacion notificacion = new Notificacion("12121212121", "12121212121");
        Notificacion notificacion2 = new Notificacion("2323232323", "2323232323");
        personaTest.recibirNotificacion(notificacion);
        personaTest.recibirNotificacion(notificacion2);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void recibirNotificacionSinApuroEnUnHorarioAgregado(){

        personaTest.setEmail("dds2023tpa@gmail.com");
        ComunicacionMail comunicacionMail = new ComunicacionMail();
        personaTest.setMedioComunicacionPreferido(comunicacionMail);

        List<LocalTime> horarios = new ArrayList<>();
        horarios.add(LocalTime.of(8, 10));
        horarios.add(LocalTime.of(21, 42));
        horarios.add(LocalTime.of(12, 55));
        horarios.add(LocalTime.of(10, 52));
        AlertaSinApuro alertaSinApuro = new AlertaSinApuro(horarios);
        alertaSinApuro.agregarHorarioDeEnvio(LocalTime.of(13, 16));
        this.personaTest.setTipoAlerta(new AlertaSinApuro(horarios));
        Notificacion notificacion = new Notificacion("12121212121", "12121212121");
        Notificacion notificacion2 = new Notificacion("2323232323", "2323232323");
        personaTest.recibirNotificacion(notificacion);
        personaTest.recibirNotificacion(notificacion2);

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

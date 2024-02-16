package test;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class IncidenteTest {

    private Incidente incidente;
    private Comunidad comunidad;
    private Miembro miembro;
    private Persona persona;
    private Servicio servicio;

    @BeforeEach
    void initialize() {
        this.servicio = Common.getServicio1();
        this.miembro = Common.getMiembroNormal();
        this.incidente = new Incidente(servicio, "Observaciones servicio",Common.getCentroide1());
    }

    @Test
    void crearIncidente(){
        Persona persona = miembro.getPersona();
        AlertaTest alertaTest = new AlertaTest();
        persona.setTipoAlerta(alertaTest);
        miembro.getComunidad().agregarMiembro(miembro);
        miembro.informarIncidente(servicio, "Observaciones servicio",Common.getCentroide1());

        Assertions.assertEquals(persona.getTipoAlerta().getClass(), AlertaTest.class);
        Assertions.assertEquals(alertaTest.getAlertText(),"Nuevo Incidente: nombreServicio1 - Observaciones servicio");

    }

}

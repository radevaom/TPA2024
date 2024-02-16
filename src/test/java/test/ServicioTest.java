package test;

import domain.models.entities.servicio.ServicioSimple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ServicioTest {
    protected ServicioSimple servicioTest;
    private void initializeServicio(){
        this.servicioTest = Common.getServicio1();
    }

    @BeforeEach
    public void initialize() {
        this.initializeServicio();
    }

    @AfterEach
    public void clean(){

    }

    @Test
    void sePuedeCrearUnServicio() {
        Assertions.assertEquals(true, servicioTest.getPrestado());
        Assertions.assertEquals("unTipoDeServicio1", servicioTest.getTipoDeServicio());
        Assertions.assertEquals("nombreServicio1", servicioTest.getNombre());
    }

    @Test
    public void cambiarDeEstado(){

        Boolean estadoServicioActual = this.servicioTest.getPrestado();
        Boolean nuevoEstadoServicio = false;

        this.servicioTest.setPrestado(nuevoEstadoServicio);

        Assertions.assertEquals(estadoServicioActual, true);
        Assertions.assertEquals(false, this.servicioTest.getPrestado());
    }
}

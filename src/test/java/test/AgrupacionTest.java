package test;

import domain.models.entities.servicio.Agrupacion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AgrupacionTest {


    protected Agrupacion agrupacionTest;

    private void initializeAgrupacion(){
        this.agrupacionTest = Common.getAgrupacion();
    }


    @BeforeEach
    public void initialize() {
        this.initializeAgrupacion();
    }

    @AfterEach
    public void clean(){

    }

    @Test
    void sePuedeCrearUnaAgrupacion() {
        Assertions.assertEquals(3, agrupacionTest.getServicios().size());
        Assertions.assertEquals("nombreServicio1", agrupacionTest.getServicios().get(0).getNombre());
    }

    @Test
    public void agregarServicio(){

        int cantServicios = this.agrupacionTest.getServicios().size();

        this.agrupacionTest.agregarServicio(Common.getServicio1());
        this.agrupacionTest.agregarServicio(Common.getServicio2());
        this.agrupacionTest.agregarServicio(Common.getServicio3());

        Assertions.assertEquals(3, cantServicios);
        Assertions.assertEquals(6, this.agrupacionTest.getServicios().size());
    }

    @Test
    public void removerServicio(){

        int cantServicios = this.agrupacionTest.getServicios().size();

        this.agrupacionTest.removerServicio(agrupacionTest.getServicios().get(0));

        Assertions.assertEquals(3, cantServicios);
        Assertions.assertEquals(2, this.agrupacionTest.getServicios().size());
    }

}

package test;

import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import domain.models.entities.usuario.OrganismoDeControl;
import domain.models.entities.usuario.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrganismoDeControlTest {
    protected OrganismoDeControl organismoDeControlTest;
    private void initializeOrganismosDeControl(){
        this.organismoDeControlTest =  Common.getOrganismoDeControl();
    }

    @BeforeEach
    public void initialize() {
        this.initializeOrganismosDeControl();
    }

    @AfterEach
    public void clean(){

    }

    @Test
    void sePuedeCrearUnOrganismoDeControl() {
        Usuario usuario = new Usuario("UsernameOrg", null, this.organismoDeControlTest);
        Assertions.assertEquals("NombreOrg", organismoDeControlTest.getNombre());
        Assertions.assertEquals("UsernameOrg", usuario.getUsername());
    }

    @Test
    void sePuedenAgregarEntidadesPrestadorasSP() {
        EntidadPrestadoraServicioPublico entidadPrestadora1 = Common.getEntidadPrestadoraServicioPublico();
        EntidadPrestadoraServicioPublico entidadPrestadora2 = Common.getEntidadPrestadoraServicioPublico("Otra");

        organismoDeControlTest.agregarEntidadPrestadoraServicioPublico(entidadPrestadora1);
        organismoDeControlTest.agregarEntidadPrestadoraServicioPublico(entidadPrestadora2);

        Assertions.assertEquals(2, organismoDeControlTest.getEntidadesControladas().size());
        Assertions.assertEquals("unNombre", organismoDeControlTest.getEntidadesControladas().get(0).getNombre());
        Assertions.assertEquals("Otra", organismoDeControlTest.getEntidadesControladas().get(1).getNombre());
    }

    @Test
    void sePuedenRemoverEntidadesPrestadorasSP() {

        EntidadPrestadoraServicioPublico entidadPrestadora1 = Common.getEntidadPrestadoraServicioPublico();
        EntidadPrestadoraServicioPublico entidadPrestadora2 = Common.getEntidadPrestadoraServicioPublico("Otra");

        organismoDeControlTest.agregarEntidadPrestadoraServicioPublico(entidadPrestadora1);
        organismoDeControlTest.agregarEntidadPrestadoraServicioPublico(entidadPrestadora2);

        organismoDeControlTest.removerEntidadPrestadoraServicioPublico(entidadPrestadora1);
        organismoDeControlTest.removerEntidadPrestadoraServicioPublico(entidadPrestadora2);

        Assertions.assertEquals(0, organismoDeControlTest.getEntidadesControladas().size());
    }

}

package test;

import domain.models.entities.miembro.MiembroAdministrador;
import domain.models.entities.usuario.Persona;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MiembroAdministradorTest {
  protected Persona usuarioTest;
  protected Persona usuarioTest2;
  protected MiembroAdministrador administradorTest;

  private void initializeMiembroAdministrador(){
    this.usuarioTest = Common.getPersona();
    usuarioTest2 = Common.getPersona2();
    administradorTest = Common.getMiembroAdministrador(usuarioTest);
    usuarioTest.agregarMiembro(administradorTest);
  }

  @BeforeEach
  public void initialize(){
    initializeMiembroAdministrador();
  }

  @AfterEach
  public void clean(){

  }

  @Test
  void sePuedeAgregarUnMiembro(){
    Assertions.assertEquals(0, usuarioTest2.getMiembros().size());
    administradorTest.agregarMiembroAComunidad(usuarioTest2);
    Assertions.assertEquals(1, usuarioTest2.getMiembros().size());
    Assertions.assertTrue(usuarioTest2.getMiembros().stream().anyMatch(miembro -> miembro.getComunidad().equals(administradorTest.getComunidad())));
  }

  @Test
  void sePuedeEliminarUnMiembro(){
    Assertions.assertEquals(0, usuarioTest2.getMiembros().size());
    administradorTest.agregarMiembroAComunidad(usuarioTest2);
    Assertions.assertEquals(1, usuarioTest2.getMiembros().size());
    administradorTest.eliminarMiembroDeComunidad(usuarioTest2);
    Assertions.assertEquals(0, usuarioTest2.getMiembros().size());
  }

  @Test
  void puedeCambiarNombreDeComunidad(){
    usuarioTest2.unirseComunidad(administradorTest.getComunidad());

    administradorTest.cambiarNombreDeLaComunidad("nuevoNombre");

    Assertions.assertEquals("nuevoNombre", usuarioTest2.getMiembros().get(0).getComunidad().getNombre());
  }
}

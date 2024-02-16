package test;

import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.serviciospublicos.entidad.TipoTransporte;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransportePublicoTest {

  protected TransportePublico subteBRosasAlem;
  protected TransportePublico ferrocarrilSarmiento;

  private void initializeTransportePublicoTest(){
    this.subteBRosasAlem =  Common.getTPSUBTERRANEO();
    this.ferrocarrilSarmiento =  Common.getTPFERROCARRIL();
  }

  @BeforeEach
  public void initialize() {
    this.initializeTransportePublicoTest();
  }

  @AfterEach
  public void clean(){

  }

  @Test
  void sePuedeCrearUnaLineaDeSubte() {
    Assertions.assertEquals(TipoTransporte.SUBTERRANEO, subteBRosasAlem.getTipoTransporte());
  }

  @Test
  void sePuedeCrearUnaLineaDeFerrocarril() {
    Assertions.assertEquals(TipoTransporte.FERROCARRIL, ferrocarrilSarmiento.getTipoTransporte());
  }


  @Test
  void sePuedenAgregarEstaciones() {
    Assertions.assertEquals(2, subteBRosasAlem.getEstaciones().size());

    TransportePublico subteBRosasAlem =  Common.getSubteB();
    Estacion nuevaEstacion = Common.estacionMedrano();
    subteBRosasAlem.agregarEstacion(nuevaEstacion);

    Assertions.assertEquals(3, subteBRosasAlem.getEstaciones().size());
  }

  @Test
  void sePuedenEliminarEstaciones() {
    Assertions.assertEquals(2, subteBRosasAlem.getEstaciones().size());

    subteBRosasAlem.eliminarEstacion(subteBRosasAlem.getEstaciones().get(0));

    Assertions.assertEquals(1, subteBRosasAlem.getEstaciones().size());
  }
}

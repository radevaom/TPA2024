package test;

import domain.models.entities.serviciospublicos.entidad.Entidad;
import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.serviciospublicos.establecimiento.Establecimiento;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import domain.models.entities.serviciospublicos.establecimiento.Sucursal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EntidadTest {

  protected TransportePublico subteB;
  protected Sucursal sucursal;
  protected Estacion estacion;

  List<Establecimiento> establecimientos = new ArrayList<>();
  List<Entidad> entidades = new ArrayList<>();

  private void initializeServicio(){
    this.subteB = Common.getSubteB();
    this.sucursal =  Common.getBancoNacion();
    this.estacion = Common.estacionAlem();
  }


  @BeforeEach
  public void initialize() {
    this.initializeServicio();
  }

  @AfterEach
  public void clean(){}


    @Test
  void unaEntidadPuedeSerUnEstablecimientoOTransportePublico() {
    entidades.add(subteB);
    entidades.add(sucursal);

    Assertions.assertEquals(2,entidades.size());
  }

  @Test
  void unEstablecimientoPuedeSerSucursalOEstacion(){
    establecimientos.add(sucursal);
    establecimientos.add(estacion);

    Assertions.assertEquals(2,establecimientos.size());
  }

  @Test
  void permiteAgregarLocalizacionASucursal(){

    Assertions.assertSame(sucursal.getLocalizacion().getClass(), Common.Caba().getClass());
  }

  @Test
  void permiteAgregarLocalizacionATransportePublico(){

    Assertions.assertSame(subteB.getLocalizacion().getClass(), Common.Caba().getClass());
  }

  @Test
  void permiteAgregarLocalizacionAEstacion(){

    Assertions.assertSame(estacion.getLocalizacion().getClass(), Common.Caba().getClass());
  }
}

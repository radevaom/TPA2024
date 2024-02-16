package test;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.ranking.Ranking;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RankingTest {

  protected EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublico;
  protected EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublicoDos;
  protected Ranking ranking;
  protected TransportePublico subte;
  protected TransportePublico tren;
  protected Incidente incidente;
  protected Incidente incidenteDos;
  protected Comunidad comunidadTest;
  protected Comunidad comunidadTestDOs;
  protected Estacion estacionAlem = Common.estacionAlem();
  protected Estacion estacionOtra = Common.estacionMedrano();
  protected Servicio servicio = Common.getServicio1();
  protected Servicio servicioDos = Common.getServicio2();

  private void initializeEntidadPrestadoraServicioPublico(){
    this.entidadPrestadoraServicioPublico =
        Common.getEntidadPrestadoraServicioPublico("Subtes S.A");
    this.entidadPrestadoraServicioPublicoDos =
        Common.getEntidadPrestadoraServicioPublico("Trenes S.A");
    this.subte = Common.getSubteB();
    this.tren = Common.getTPFERROCARRIL();
    this.comunidadTest =  Common.getComunidad();
    this.comunidadTestDOs = Common.getComunidad2();
    this.incidente = new Incidente(servicio,"",Common.getCentroide1());
    this.incidenteDos = new Incidente(servicioDos,"",Common.getCentroide1());
    this.estacionAlem.setServicios(List.of(servicio));
    this.estacionOtra.setServicios(List.of(servicioDos));

    subte.agregarEstacion(estacionAlem);
    subte.agregarEstacion(estacionOtra);
    tren.agregarEstacion(estacionAlem);

    incidente.setFecha(LocalDateTime.
        of(2023, 9, 1, 0, 0, 0));
    incidente.setCierre(LocalDateTime.
        of(2023, 9, 5, 0, 0, 0));

    incidenteDos.setFecha(LocalDateTime.
        of(2023, 9, 1, 0, 0, 0));
    incidenteDos.setCierre(LocalDateTime.
        of(2023, 9, 3, 0, 0, 0));

    entidadPrestadoraServicioPublico.setServiciosPublicosOfrecidos(List.of(subte));
    entidadPrestadoraServicioPublicoDos.setServiciosPublicosOfrecidos(List.of(tren));

    Common.getMiembroConMail(comunidadTest);
    Common.getMiembroConMail(comunidadTest);
    Common.getMiembroConMail(comunidadTest);
    Common.getMiembroConMail(comunidadTest);

    Common.getMiembroConMail(comunidadTestDOs);
    Common.getMiembroConMail(comunidadTestDOs);
    Common.getMiembroConMail(comunidadTestDOs);
    Common.getMiembroConMail(comunidadTestDOs);
    Common.getMiembroConMail(comunidadTestDOs);

    
    

    comunidadTest.agregarIncidente(incidente);


    this.ranking = new Ranking(List.of(incidente,incidenteDos),
        List.of(comunidadTest,comunidadTestDOs),
        List.of(entidadPrestadoraServicioPublico,entidadPrestadoraServicioPublicoDos),
        LocalDate.of(2023, 9, 1),
        LocalDate.of(2023, 9, 5));
  }


  @BeforeEach
  public void initialize() {
    this.initializeEntidadPrestadoraServicioPublico();
  }

  @AfterEach
  public void clean(){

  }

  @Test
  void rankingTiempoCierreIncidente(){

    Map<String, Double> rankingCierreEntidad = ranking.tiempoCierrePorEntidad();

    Assertions.assertEquals(rankingCierreEntidad.size(),2);
    Assertions.assertEquals(rankingCierreEntidad.get("Subtes S.A"),3.0);
    Assertions.assertEquals(rankingCierreEntidad.get("Trenes S.A"),4.0);
  }

  @Test
  void rankingTiempoCierreIncidenteEntidadEspecifica(){

    Map<String, Double> rankingCierreEntidad =
        ranking.tiempoCierrePorEntidadEspecifica(entidadPrestadoraServicioPublico);
    Assertions.assertEquals(rankingCierreEntidad.get("Subtes S.A"),3.0);
    Assertions.assertEquals(rankingCierreEntidad.size(),1);
  }

  @Test
  void rankingCantidadIncidentesReportadosPorEntidad(){

    Map<String, Integer> rankingCantidadReportados =
        ranking.cantidadIncidentesReportadosPorEntidad();

    Assertions.assertEquals(rankingCantidadReportados.size(),2);
    Assertions.assertEquals(rankingCantidadReportados.get("Subtes S.A"),2);
    Assertions.assertEquals(rankingCantidadReportados.get("Trenes S.A"),1);

  }

  @Test
  void rankingCantidadIncidentesReportadosPorEntidadEspecifica(){

    Map<String, Integer> rankingCantidadReportados =
        ranking.cantidadIncidentesReportadosPorEntidadEspecifica(entidadPrestadoraServicioPublico);

    Assertions.assertEquals(rankingCantidadReportados.size(),1);
    Assertions.assertEquals(rankingCantidadReportados.get("Subtes S.A"),2);

  }
  @Test
  void rankingGradoDeImpactoPorComunidad(){

    Map<String, Integer> rankingImpactoComunidad =
        ranking.gradoDeImpactoPorComunidad();

    Assertions.assertEquals(rankingImpactoComunidad.size(),2);
    Assertions.assertEquals(rankingImpactoComunidad.get("nombreComunidad"),4);
    Assertions.assertEquals(rankingImpactoComunidad.get("nombreComunidad2"),5);


  }

}

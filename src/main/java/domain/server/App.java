package domain.server;

import domain.models.entities.comunicacion.AlertaCuandoSucede;
import domain.models.entities.comunicacion.AlertaSinApuro;
import domain.models.entities.comunicacion.ComunicacionMail;
import domain.models.entities.comunicacion.ComunicacionWhatsapp;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.MiembroNormal;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.services.georef.entities.Municipio;
import domain.models.entities.services.georef.entities.Provincia;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.servicio.ServicioSimple;
import domain.models.entities.serviciospublicos.entidad.TipoTransporte;
import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;

import domain.models.entities.usuario.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

public class App implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

  public static void main(String[] args) {
    new App().run();
    Server.init();

  }


  public void run() {
    withTransaction(() -> {

      Centroide centroideCaba = new Centroide(-34.6144,-58.4458);
      Centroide centroideBsas = new Centroide(-36.6773,-60.5584);

      Centroide centroideMoron = new Centroide(-34.6655,-58.664);

      ServicioSimple servicioSimple = new ServicioSimple("nombreServicio1",
          true, "unTipoDeServicio1");
      Provincia caba = new Provincia();
      Municipio moron = new Municipio("moron");
      Estacion rosas = new Estacion("Rosas", caba, List.of(servicioSimple));
      Estacion alem = new Estacion("Alem", caba, List.of(servicioSimple));
      Estacion estacionMoron = new Estacion("Moron", moron, List.of(servicioSimple));
      caba.setCentroide(centroideCaba);
      moron.setCentroide(centroideMoron);
      rosas.setLocalizacion(caba);
      alem.setLocalizacion(caba);
      List<Estacion> estaciones = List.of(rosas, alem);
      TransportePublico subteB = new TransportePublico("subteB", rosas, alem,
          estaciones, TipoTransporte.SUBTERRANEO, caba);

      Centroide centroide1 = new Centroide(10, 11);
      Centroide centroide2 = new Centroide(20, 22);
      Centroide centroide3 = new Centroide(30, 33);
      Centroide centroide4 = new Centroide(40, 44);

      List<Servicio> servicios1 = new ArrayList<>();
      Municipio municipio = new Municipio("munucipio");
      municipio.setCentroide(centroide1);
      Estacion estacion = new Estacion("Estacion", municipio, servicios1);
      Servicio servicio1 = new ServicioSimple("Servicio1", true, "tipoServicio1", rosas);
      Servicio servicio2 = new ServicioSimple("Servicio2", true, "tipoServicio2", rosas);
      Servicio servicio3 = new ServicioSimple("Servicio3", true, "tipoServicio3", rosas);

      List<Servicio> servicios = new ArrayList<>();
      servicios.add(servicio1);
      servicios.add(servicio2);
      servicios.add(servicio3);

      Municipio municipio1 = new Municipio("munucipio1");
      municipio1.setCentroide(centroide1);
      Estacion estacion1 = new Estacion("Estacion1", municipio1, servicios);

      //servicio1.setEstacion(estacion1);
      //servicio2.setEstacion(estacion1);
      //servicio3.setEstacion(estacion1);

      Servicio servicio4 = new ServicioSimple("Servicio4", true, "tipoServicio4", rosas);
      Servicio servicio5 = new ServicioSimple("Servicio5", true, "tipoServicio5", estacionMoron);

      Incidente incidente1 = new Incidente(servicio1, "observasiones incidente 1", centroide1);
      Incidente incidente2 = new Incidente(servicio2, "observasiones incidente 2", centroide2);
      Incidente incidente3 = new Incidente(servicio3, "observasiones incidente 3", centroide3);
      Incidente incidente4 = new Incidente(servicio4, "observasiones incidente 4", centroide4);
      Incidente incidente5 = new Incidente(servicio5, "observasiones incidente 5", centroideMoron);
      incidente1.setFecha(LocalDateTime.of(2024, 2, 12, 0, 0));
      incidente2.setFecha(LocalDateTime.of(2024, 2, 13, 0, 0));
      incidente3.setFecha(LocalDateTime.of(2024, 2, 14, 0, 0));
      incidente4.setFecha(LocalDateTime.of(2024, 2, 11, 0, 0));

      rosas.setServicios(List.of(servicio1, servicio2, servicio3, servicio4));
      alem.setServicios(List.of(servicio3, servicio4, servicio5));

      AlertaCuandoSucede alertaCuandoSucede = new AlertaCuandoSucede();
      AlertaSinApuro alertaSinApuro = new AlertaSinApuro();
      ComunicacionMail comunicacionMail = new ComunicacionMail();
      ComunicacionWhatsapp comunicacionWhatsapp = new ComunicacionWhatsapp();

      Persona persona1 = new Persona("grupo17@gmail.com", "Grupo",
          "17", alertaCuandoSucede);
      persona1.setLocalizacion(caba);
      persona1.setMedioComunicacionPreferido(comunicacionMail);
      Usuario usu1 = new Usuario("grupo17", "grupo17", persona1);

      Comunidad comunidad1 = new Comunidad("Comunidad 1");
      Comunidad comunidad2 = new Comunidad("Comunidad 2 hola");
      Comunidad comunidad3 = new Comunidad("Comunidad 3 calle");
      Comunidad comunidad4 = new Comunidad("Comunidad 4 auto");
      Comunidad comunidad5 = new Comunidad("transporte Comunidad 5 transporte");
      Comunidad comunidad6 = new Comunidad("Comunidad 6");

      MiembroNormal miembroNormal1 = new MiembroNormal(comunidad1, persona1);
      MiembroNormal miembroNormal2 = new MiembroNormal(comunidad2, persona1);
      MiembroNormal miembroNormal3 = new MiembroNormal(comunidad3, persona1);
      //MiembroNormal miembroNormal4 = new MiembroNormal(comunidad4, persona3);
      MiembroNormal miembroNormal5 = new MiembroNormal(comunidad5, persona1);
      MiembroNormal miembroNormal6 = new MiembroNormal(comunidad6, persona1);

      comunidad1.setIncidentesReportados(List.of(incidente1, incidente2, incidente3, incidente4, incidente5));

      EntidadPrestadoraServicioPublico grupo17SA =
          new EntidadPrestadoraServicioPublico("grupo17 S.A", "grupo17@gmail.com");








      Persona persona2 = new Persona("grupo17@gmail.com", "Nahuel",
          "Esper Madenso", alertaCuandoSucede);
      persona2.setLocalizacion(caba);
      persona2.setMedioComunicacionPreferido(comunicacionWhatsapp);
      Usuario usu2 = new Usuario("entidad", "entidad", persona2);

      grupo17SA.setPersonaDesignada(persona2);
      grupo17SA.agregarServicioOfrecido(subteB);


      MiembroNormal miembroNormal4 = new MiembroNormal(comunidad4, persona2);





      Persona persona3 = new Persona("grupo17@gmail.com", "Ana",
          "Busado Demi", alertaCuandoSucede);

      persona3.setMedioComunicacionPreferido(comunicacionMail);

      Usuario usu3 = new Usuario("orga", "orga", persona3);

      OrganismoDeControl organismoDeControl =
          new OrganismoDeControl(persona3, "organismoControl");
      organismoDeControl.agregarEntidadPrestadoraServicioPublico(grupo17SA);

      incidente1.setComunidad(comunidad1);
      incidente2.setComunidad(comunidad1);
      incidente3.setComunidad(comunidad1);
      incidente4.setComunidad(comunidad1);






      Administrador personaAdministrador = new Administrador();
      Usuario administrador = new Usuario("admin", "admin", personaAdministrador);

      persist(personaAdministrador);
      persist(administrador);

      persist(centroideCaba);
      persist(centroideBsas);
      persist(centroideMoron);

      persist(centroide1);
      persist(centroide2);
      persist(centroide3);
      persist(centroide4);

      persist(municipio1);
      persist(moron);
      persist(servicio1);
      persist(servicio2);
      persist(servicio3);
      persist(servicio4);
      persist(servicio5);
      persist(estacion1);
      persist(estacionMoron);


      persist(comunidad1);
      persist(comunidad2);
      persist(comunidad3);
      persist(comunidad4);
      persist(comunidad5);
      persist(comunidad6);

      persist(miembroNormal1);
      persist(miembroNormal2);
      persist(miembroNormal3);
      persist(miembroNormal4);
      persist(miembroNormal5);
      persist(miembroNormal6);

      incidente4.setCierre(LocalDateTime.of(2024, 2, 15, 0, 0));
      persist(incidente4);
      persist(incidente1);
      persist(incidente2);
      persist(incidente3);
      persist(incidente5);

      persist(caba);
      persist(subteB);

      persist(alertaCuandoSucede);
      persist(alertaSinApuro);
      persist(persona1);
      persist(usu1);
      persist(persona2);
      persist(persona3);
      persist(usu2);
      persist(usu3);
      persist(grupo17SA);
      persist(organismoDeControl);

    });
  }
}
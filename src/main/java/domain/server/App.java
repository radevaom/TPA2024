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

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class App {
  public static EntityManagerFactory entityManagerFactory;

  public static void main(String[] args) throws Exception {
    entityManagerFactory =  createEntityManagerFactory();

    new App().run();
    Server.init();







  }
  public static EntityManagerFactory createEntityManagerFactory() throws Exception {
    // https://stackoverflow.com/questions/8836834/read-environment-variables-in-persistence-xml-file
    Map<String, String> env = System.getenv();
    Map<String, Object> configOverrides = new HashMap<String, Object>();

    String[] keys = new String[] {
        "DATABASE_URL",
        "javax__persistence__jdbc__driver",
        "javax__persistence__jdbc__password",
        "javax__persistence__jdbc__url",
        "javax__persistence__jdbc__user",
        "hibernate__hbm2ddl__auto",
        "hibernate__connection__pool_size",
        "hibernate__show_sql" };

    for (String key : keys) {

      try{
        if (key.equals("DATABASE_URL")) {

          // https://devcenter.heroku.com/articles/connecting-heroku-postgres#connecting-in-java
          String value = env.get(key);
          URI dbUri = new URI(value);
          String username = dbUri.getUserInfo().split(":")[0];
          String password = dbUri.getUserInfo().split(":")[1];
          //javax.persistence.jdbc.url=jdbc:postgresql://localhost/dblibros
          value = "jdbc:mysql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath();// + "?sslmode=require";
          configOverrides.put("javax.persistence.jdbc.url", value);
          configOverrides.put("javax.persistence.jdbc.user", username);
          configOverrides.put("javax.persistence.jdbc.password", password);
          configOverrides.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");

          //  configOverrides.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        }
        // no se pueden poner variables de entorno con "." en la key
        String key2 = key.replace("__",".");
        if (env.containsKey(key)) {
          String value = env.get(key);
          configOverrides.put(key2, value);
        }
      } catch(Exception ex){
        System.out.println("Error configurando " + key);
      }
    }
    System.out.println("Config overrides ----------------------");
    for (String key : configOverrides.keySet()) {
      System.out.println(key + ": " + configOverrides.get(key));
    }
    return Persistence.createEntityManagerFactory("db", configOverrides);
  }

  public void run() {
    EntityManager entity = entityManagerFactory.createEntityManager();

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
      entity.getTransaction().begin();

      entity.persist(personaAdministrador);
      entity.persist(administrador);

      entity.persist(centroideCaba);
      entity.persist(centroideBsas);
      entity.persist(centroideMoron);

      entity.persist(centroide1);
      entity.persist(centroide2);
      entity.persist(centroide3);
      entity.persist(centroide4);

      entity.persist(municipio1);
      entity.persist(moron);
      entity.persist(servicio1);
      entity.persist(servicio2);
      entity.persist(servicio3);
      entity.persist(servicio4);
      entity.persist(servicio5);
      entity.persist(estacion1);
      entity.persist(estacionMoron);


      entity.persist(comunidad1);
      entity.persist(comunidad2);
      entity.persist(comunidad3);
      entity.persist(comunidad4);
      entity.persist(comunidad5);
      entity.persist(comunidad6);

      entity.persist(miembroNormal1);
      entity.persist(miembroNormal2);
      entity.persist(miembroNormal3);
      entity.persist(miembroNormal4);
      entity.persist(miembroNormal5);
      entity.persist(miembroNormal6);

      incidente4.setCierre(LocalDateTime.of(2024, 2, 15, 0, 0));
      entity.persist(incidente4);
      entity.persist(incidente1);
      entity.persist(incidente2);
      entity.persist(incidente3);
      entity.persist(incidente5);

      entity.persist(caba);
      entity.persist(subteB);

      entity.persist(alertaCuandoSucede);
      entity.persist(alertaSinApuro);
      entity.persist(persona1);
      entity.persist(usu1);
      entity.persist(persona2);
      entity.persist(persona3);
      entity.persist(usu2);
      entity.persist(usu3);
      entity.persist(grupo17SA);
      entity.persist(organismoDeControl);

      entity.getTransaction().commit();
      entity.close();
  }
}
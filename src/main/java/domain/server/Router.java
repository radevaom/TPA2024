package domain.server;


import static domain.server.App.createEntityManagerFactory;
import static domain.server.App.entityManagerFactory;

import domain.Repositorios.*;
import domain.models.Controllers.*;
import io.javalin.http.HttpStatus;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class Router {
  public static void init() {
    Router.configure();

  }

  static RepoMiembro repoMiembro = new RepoMiembro();

  static SesionController sesionController = new SesionController(entityManagerFactory);

  static UIComunidadController uiComunidadController = new UIComunidadController(entityManagerFactory);
  static RegistroController registroController = new RegistroController(entityManagerFactory);

  static AltaComunidadController altaComunidadController = new AltaComunidadController(entityManagerFactory);

  static ListaRankingIncidenteController listaRankingIncidenteController = new ListaRankingIncidenteController(entityManagerFactory);

  static AltaIncidenteController altaIncidenteController = new AltaIncidenteController(entityManagerFactory);

  static CierreIncidenteController cierreIncidenteController = new CierreIncidenteController(entityManagerFactory);

  static ListaIncidenteController listaIncidenteController = new ListaIncidenteController(entityManagerFactory);

  static SugerenciaRevisionIncidenteController sugerenciaRevisionIncidenteController = new SugerenciaRevisionIncidenteController(entityManagerFactory);

  static ListaComunidadController listaComunidadController = new ListaComunidadController(entityManagerFactory);

  static BorrarComunidadController borrarComunidadController = new BorrarComunidadController(entityManagerFactory);

  static CargaMasivaController cargaMasivaController = new CargaMasivaController(entityManagerFactory);

  static ListaIncidenteControllerDeComunidad listaIncidenteControllerDeComunidad = new ListaIncidenteControllerDeComunidad(entityManagerFactory);

  static HomeController homeController = new HomeController(entityManagerFactory);

  static UIMiembroController uiMiembroController = new UIMiembroController(repoMiembro);
  private static void configure() {

    Server.app().before(ctx -> {
      if (!(ctx.path().equals("/login")) && !(ctx.path().equals("/registro")) && ctx.sessionAttribute("user_id") == null) {
        new FiltroDeAutenticacion().handle(ctx);
      }
    });

    Server.app().get("/", sesionController::mostrarLogin);

    Server.app().get("/login", sesionController);
    Server.app().post("/login", sesionController::iniciarSesion);

    Server.app().get("/logout", sesionController::cerrarSesion);

    // TODO CARGA MASIVA DE DATOS - TENDRIAMOS QUE CREAR UNA PANTALLA ESPECFICA PARA CARGAR ARCHIVOS Y EL SISTEMA, A PARTIR DE UN
    // CSV CREA LAS ENTIDADES PRESTADORAS Y LOS ORGANISMOS DE CONTROL CON LA CLASE CSVLOADER

    Server.app().get("/cargaMasiva", cargaMasivaController);
    Server.app().post("/cargaMasiva", cargaMasivaController);

    Server.app().post("/incidente", altaIncidenteController);

    Server.app().post("/incidentes/{incidenteId}/cierre", cierreIncidenteController);
    Server.app().get("/incidentes/cierre", cierreIncidenteController);

    Server.app().get("/incidentes", listaIncidenteController);
    Server.app().get("/incidentes/nuevo", altaIncidenteController);

   // Server.app().get("/incidentes/{incidenteId}", new UIIncidenteController(repoIncidente));

    Server.app().get("/incidentes/sugerenciaRevision/{incidenteId}", sugerenciaRevisionIncidenteController);
    Server.app().get("/incidentes/sugerenciaRevision", sugerenciaRevisionIncidenteController);
    Server.app().post("/incidentes/sugerenciaRevision", sugerenciaRevisionIncidenteController);

    Server.app().get("/incidentes/ranking", listaRankingIncidenteController);




    Server.app().get("/home", homeController);

    //Server.app().post("/comunidades", new AltaComunidadController(repoComunidad));

    Server.app().get("/comunidades", listaComunidadController);

    Server.app().delete("/comunidades/{comunidadId}", borrarComunidadController);

    Server.app().get("/comunidades/{comunidadId}", uiComunidadController);

    Server.app().get("/unirmeComunidad/{comunidadId}", uiComunidadController::unirmeComunidad);

    Server.app().get("/salirComunidad/{comunidadId}", uiComunidadController::salirComunidad);

    Server.app().get("/incidentesDeComunidad/{comunidadId}", listaIncidenteControllerDeComunidad);


    Server.app().get("/miembros/{miembroId}", uiMiembroController);

    Server.app().get("/miembros", uiMiembroController);

    Server.app().get("/registro", registroController);
    Server.app().post("/registro", registroController);


    Server.app().get("/crearComunidad", altaComunidadController);
    Server.app().post("/crearComunidad", altaComunidadController);


    Server.app().after(ctx -> {
      EntityManager entity = entityManagerFactory.createEntityManager();
      entity.clear();
    });
  }
}
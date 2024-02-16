package domain.server;


import domain.Repositorios.*;
import domain.models.Controllers.*;
import io.javalin.http.HttpStatus;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

public class Router {

  public static void init() {
    Router.configure();
  }


  static RepoComunidad repoComunidad = new RepoComunidad();
  static RepoMiembro repoMiembro = new RepoMiembro();
  static RepoUsuario repoUsuario = new RepoUsuario();

  static RepoIncidente repoIncidente = new RepoIncidente();

  static RepoRanking repoRanking = new RepoRanking();

  static RepoEntidadesPrestadoras repoEntidadesPrestadoras = new RepoEntidadesPrestadoras();
  static RepoOrganismosDeControl repoOrganismosDeControl = new RepoOrganismosDeControl();

  static SesionController sesionController = new SesionController();

  static UIComunidadController uiComunidadController = new UIComunidadController(repoComunidad);
  static RegistroController registroController = new RegistroController();

  static AltaComunidadController altaComunidadController = new AltaComunidadController();

  static ListaRankingIncidenteController listaRankingIncidenteController = new ListaRankingIncidenteController();
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

    Server.app().get("/cargaMasiva", new CargaMasivaController());
    Server.app().post("/cargaMasiva", new CargaMasivaController());

    Server.app().post("/incidente", new AltaIncidenteController(repoIncidente));

    Server.app().post("/incidentes/{incidenteId}/cierre", new CierreIncidenteController(repoIncidente));
    Server.app().get("/incidentes/cierre", new CierreIncidenteController(repoIncidente));

    Server.app().get("/incidentes", new ListaIncidenteController(repoIncidente));
    Server.app().get("/incidentes/nuevo", new AltaIncidenteController(repoIncidente));

   // Server.app().get("/incidentes/{incidenteId}", new UIIncidenteController(repoIncidente));

    Server.app().get("/incidentes/sugerenciaRevision/{incidenteId}", new SugerenciaRevisionIncidenteController(repoIncidente));
    Server.app().get("/incidentes/sugerenciaRevision", new SugerenciaRevisionIncidenteController(repoIncidente));
    Server.app().post("/incidentes/sugerenciaRevision", new SugerenciaRevisionIncidenteController(repoIncidente));

    Server.app().get("/incidentes/ranking", listaRankingIncidenteController);




    Server.app().get("/home", new HomeController());

    //Server.app().post("/comunidades", new AltaComunidadController(repoComunidad));

    Server.app().get("/comunidades", new ListaComunidadController(repoComunidad));

    Server.app().delete("/comunidades/{comunidadId}", new BorrarComunidadController(repoComunidad));

    Server.app().get("/comunidades/{comunidadId}", new UIComunidadController(repoComunidad));

    Server.app().get("/unirmeComunidad/{comunidadId}", uiComunidadController::unirmeComunidad);

    Server.app().get("/salirComunidad/{comunidadId}", uiComunidadController::salirComunidad);

    Server.app().get("/incidentesDeComunidad/{comunidadId}", new ListaIncidenteControllerDeComunidad());


    Server.app().get("/miembros/{miembroId}", new UIMiembroController(repoMiembro));

    Server.app().get("/miembros", new UIListMiembroController(repoMiembro));

    Server.app().get("/registro", registroController);
    Server.app().post("/registro", registroController);


    Server.app().get("/crearComunidad", altaComunidadController);
    Server.app().post("/crearComunidad", altaComunidadController);


    Server.app().after(ctx -> {
      PerThreadEntityManagers.getEntityManager().clear();
    });
  }
}
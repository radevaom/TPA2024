package domain.models.Controllers;

import domain.Repositorios.RepoIncidente;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.TipoUsuario;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AltaIncidenteController extends HandlerTP {

  private EntityManagerFactory entityManagerFactory;

  public AltaIncidenteController(EntityManagerFactory entityManagerFactory){
    super(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);
    if(ctx.method() == HandlerType.GET)
      handleGetRequest(ctx);
    else
      handlePostRequest(ctx);

  }

  public void handleGetRequest(Context context) {
    Map<String, Object> model = inicializarModelo(context);

    if (context.sessionAttribute("user_id") != null) {
      EntityManager entity = entityManagerFactory.createEntityManager();

      entity.getTransaction().begin();

      Usuario usuario = entity.createQuery("SELECT u FROM Usuario u where u.id = :userId", Usuario.class)
          .setParameter("userId", context.sessionAttribute("user_id"))
          .getSingleResult();

      List<Comunidad> comunidades = usuario.getTipoUsuario().getMiembros().stream().map(Miembro::getComunidad).toList();

      List<Servicio> servicios = entity.createQuery(
              "SELECT s FROM Servicio s",
              Servicio.class)
          .getResultList();

      model.put("servicios", servicios);
      model.put("comunidades", comunidades);

      entity.close();
    }
    context.render("/aperturaIncidente.hbs", model);

  }

  public void handlePostRequest(Context context) {

    EntityManager entity = entityManagerFactory.createEntityManager();

    entity.getTransaction().begin();

    Long servId = Long.parseLong(context.formParam("servicio"));
    Servicio servicio2 = entity.createQuery(
            "SELECT s FROM Servicio s where s.id = :servId",
            Servicio.class)
            .setParameter("servId", servId)
            .getSingleResult();

    System.out.println("EL SERVID ES: " + servId);


    Long comId = Long.parseLong(context.formParam("comunidad"));
    Comunidad comunidad = entity.createQuery(
            "SELECT s FROM Comunidad s where s.id = :comId",
            Comunidad.class)
        .setParameter("comId", comId)
        .getSingleResult();

    Centroide centroide = entity.createQuery(
            "SELECT c FROM Centroide c",
            Centroide.class).getResultList().get(0);

    Incidente incidente = new Incidente(servicio2, context.formParam("observaciones"), centroide);


    comunidad.agregarIncidente(incidente);

    entity.persist(comunidad);
    entity.persist(incidente);

    entity.getTransaction().commit();

    entity.close();

    context.status(HttpStatus.CREATED);
    //context.result("Incidente agregado correctamente");

    System.out.println("EL id de la comunidad ES: " +  comunidad.getId());
    System.out.println("EL nombre de la comunidad ES: " +  comunidad.getNombre());

    context.redirect("/incidentesDeComunidad/" + comunidad.getId());
  }

}

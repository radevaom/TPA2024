package domain.models.Controllers;

import domain.Repositorios.RepoIncidente;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;


import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CierreIncidenteController extends HandlerTP {

  private EntityManagerFactory entityManagerFactory;

  public CierreIncidenteController(EntityManagerFactory entityManagerFactory){
    super(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    // String incidenteIdStr = ctx.pathParam("incidenteId");
//    Incidente incidente = repoIncidente.findByid(Long.parseLong(incidenteIdStr));

    //TODO tiene que agregarle la fecha de cierre al incidente, NO ELIMIARLO DE LA BASE

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


      List<Incidente> incidentes = comunidades.stream().flatMap(comunidad -> comunidad.getIncidentesReportados().stream()).toList();

      model.put("comunidades", comunidades);
      model.put("incidentes", incidentes.stream().filter(incidente -> incidente.getCierre() == null).toList());

      entity.close();
    }
    model.put("mostrarLinkCierre", true);

    context.render("/listadoIncidentes.hbs", model);

  }

  public void handlePostRequest(Context context) {
    Map<String, Object> model = inicializarModelo(context);
    EntityManager entity = entityManagerFactory.createEntityManager();

    entity.getTransaction().begin();

    String incidenteId = context.pathParam("incidenteId");

    Incidente incidente = entity.createQuery("SELECT i from Incidente i where i.id = :id", Incidente.class)
            .setParameter("id", Long.parseLong(incidenteId)).getSingleResult();

    Usuario usuario = entity.createQuery("SELECT u FROM Usuario u where u.id= :id", Usuario.class)
            .setParameter("id", context.sessionAttribute("user_id"))
            .getSingleResult();

    incidente.setUsuarioCierre(usuario);
    incidente.setCierre(LocalDateTime.now());

    entity.persist(incidente);

    entity.getTransaction().commit();
    entity.close();

    //context.result("Incidente cerrado correctamente");
    context.redirect("/incidentesDeComunidad/" + incidente.getComunidad().getId());
  }
}

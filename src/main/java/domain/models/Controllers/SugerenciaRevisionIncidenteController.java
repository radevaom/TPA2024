package domain.models.Controllers;


import com.github.jknack.handlebars.Handlebars;
import domain.Repositorios.RepoIncidente;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class SugerenciaRevisionIncidenteController extends HandlerTP {

  private EntityManagerFactory entityManagerFactory;

  public SugerenciaRevisionIncidenteController(EntityManagerFactory entityManagerFactory){
    super(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
  }


  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);
    Map<String, Object> model = inicializarModelo(ctx);
    if(ctx.method() == HandlerType.GET)
      handleGetRequest(ctx, model);
    else
      handlePostRequest(ctx, model);


    //String incidenteIdStr = ctx.pathParam("incidenteId");
//    Incidente incidente = repoIncidente.findByid(Long.parseLong(incidenteIdStr));
    //TODO tiene nofiticar que se sugiere revisar este incidente
  }

  public void handleGetRequest(Context ctx, Map<String, Object> model) {

    Usuario usuario = getUsuario(ctx);

    Centroide centroide = new Centroide(ctx.sessionAttribute("lat"), ctx.sessionAttribute("lon"));


    List<Comunidad> comunidades = usuario.getTipoUsuario().getMiembros().stream().map(Miembro::getComunidad).toList();
    List<Incidente> incidentesCercanos = comunidades.stream().flatMap(comunidad -> comunidad.encontrarIncidentesCercanos(centroide).stream()).toList();
    //List<Incidente> incidentes = comunidades.stream().flatMap(comunidad -> comunidad.getIncidentesReportados().stream()).toList();

    //model.put("incidentes", incidentes);
    model.put("incidentesCercanos", incidentesCercanos);

    ctx.render("/sugerenciaRevisionIncidente.hbs", model);

  }
  public void handlePostRequest(Context ctx, Map<String, Object> model) {
    Usuario usuario = getUsuario(ctx);
    Centroide centroide = ctx.bodyAsClass(Centroide.class);

    ctx.sessionAttribute("lat", centroide.getLat());
    ctx.sessionAttribute("lon", centroide.getLon());
/*
    List<Comunidad> comunidades = usuario.getTipoUsuario().getMiembros().stream().map(Miembro::getComunidad).toList();
    List<Incidente> incidentesCercanos = comunidades.stream().flatMap(comunidad -> comunidad.encontrarIncidentesCercanos(centroide).stream()).toList();
    List<Incidente> incidentes = comunidades.stream().flatMap(comunidad -> comunidad.getIncidentesReportados().stream()).toList();

    model.put("incidentes", incidentes);
    model.put("incidentesCercanos", incidentesCercanos);
*/
    ctx.redirect("/incidentes/sugerenciaRevision");
  }

}

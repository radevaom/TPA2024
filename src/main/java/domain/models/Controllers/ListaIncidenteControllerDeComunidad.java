package domain.models.Controllers;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;


import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListaIncidenteControllerDeComunidad extends HandlerTP {
  private EntityManagerFactory entityManagerFactory;
  public ListaIncidenteControllerDeComunidad(EntityManagerFactory entityManagerFactory) {
    super(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);


    Map<String, Object> model = inicializarModelo(ctx);
    //    ctx.json(repoIncidente.all());
    EntityManager entity = entityManagerFactory.createEntityManager();

    entity.getTransaction().begin();

    // Realizar la consulta para obtener los datos de la tabla
    // Aquí debes reemplazar YourEntityClass con el nombre de tu clase de entidad
    List<Incidente> todosLosIncidentes = entity.createQuery("SELECT e FROM Incidente e", Incidente.class)
            .getResultList();

    //results.forEach(incidente -> incidente.setServicio(null));

    Comunidad comunidad = comunidad(ctx);


    model.put("todosLosIncidentes", todosLosIncidentes);
    model.put("incidentes", comunidad.getIncidentesReportados());

    ctx.render("listadoIncidentes.hbs", model);
    entity.close();

  }

  public Comunidad comunidad(Context ctx){

    String comunidadId = ctx.pathParam("comunidadId"); // Reemplaza con el nombre correcto del parámetro

    Long comunidadId1 = Long.valueOf(comunidadId);

    Long userId = ctx.sessionAttribute("user_id");

    EntityManager entity = entityManagerFactory.createEntityManager();
    Usuario usuarioEnSesion = entity.find(Usuario.class, userId);

    List<Comunidad> comunidades = obtenerComunidades(usuarioEnSesion.getTipoUsuario().getMiembros());

    return comunidades.stream().filter(comunidad -> comunidad.getId() == comunidadId1).findFirst().orElse(null);
  }


  private static List<Comunidad> obtenerComunidades(List<Miembro> listaMiembros) {
    List<Comunidad> listaComunidades = new ArrayList<>();

    for (Miembro miembro : listaMiembros) {
      Comunidad comunidad = miembro.getComunidad();

      // Verificar si la comunidad ya está en la lista
      if (!listaComunidades.contains(comunidad)) {
        listaComunidades.add(comunidad);
      }
    }

    return listaComunidades;
  }

}

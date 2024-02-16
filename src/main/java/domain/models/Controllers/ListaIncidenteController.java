package domain.models.Controllers;

import domain.Repositorios.RepoComunidad;
import domain.Repositorios.RepoIncidente;
import domain.models.entities.incidente.Incidente;
import io.javalin.http.Context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;


import javax.persistence.EntityManager;

public class ListaIncidenteController extends HandlerTP {

  private EntityManagerFactory entityManagerFactory;

  public ListaIncidenteController(EntityManagerFactory entityManagerFactory){
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
    List<Incidente> results = entity.createQuery("SELECT e FROM Incidente e", Incidente.class)
            .getResultList();

    //results.forEach(incidente -> incidente.setServicio(null));


    model.put("incidentes", results);

    ctx.render("/listadoIncidentes.hbs", model);
    entity.close();

//    entity.getTransaction().commit();
    //  entity.close();

    //ctx.json(results); // Devolver los resultados como JSON
  }
}

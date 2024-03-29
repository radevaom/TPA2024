package domain.models.Controllers;

import domain.Repositorios.RepoComunidad;
import domain.models.entities.comunidad.Comunidad;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

public class BorrarComunidadController implements Handler {
  private EntityManagerFactory entityManagerFactory;

  public BorrarComunidadController(EntityManagerFactory entityManagerFactory){
    super();
    this.entityManagerFactory = entityManagerFactory;
  }
  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    String comunidadIdStr = ctx.pathParam("comunidadId");
//
//    Comunidad comunidad = repoComunidad.findByid(Long.parseLong(comunidadIdStr));
//    repoComunidad.remove(comunidad);
    ctx.result("Comunidad eliminada correctamente");
  }
}

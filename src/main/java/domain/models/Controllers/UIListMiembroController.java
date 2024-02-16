package domain.models.Controllers;

import domain.Repositorios.RepoMiembro;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UIListMiembroController extends HandlerTP {


  private RepoMiembro repoMiembro;
  public UIListMiembroController(RepoMiembro repoMiembro, EntityManagerFactory entityManagerFactory) {
    super(entityManagerFactory);
    this.repoMiembro = repoMiembro;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);
    Map<String, Object> model = new HashMap<>();

    model.put("miembro", repoMiembro.all());
    ctx.render("miembros.hbs", model);
  }
}

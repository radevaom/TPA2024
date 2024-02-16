package domain.models.Controllers;


import domain.Repositorios.RepoMiembro;
import domain.models.entities.miembro.Miembro;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class UIMiembroController implements Handler {

  private RepoMiembro repoMiembro;
  public UIMiembroController(RepoMiembro repoMiembro) {
      super();
      this.repoMiembro = repoMiembro;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    Map<String, Object> model = new HashMap<>();
    String miembroIdStr = ctx.pathParam("miembroId");
    Miembro miembro = repoMiembro.findByid(Long.parseLong(miembroIdStr));
    model.put("miembro", miembro);
    ctx.render("miembro.hbs", model);
  }

}

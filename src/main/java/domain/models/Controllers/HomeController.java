package domain.models.Controllers;


import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class HomeController extends HandlerTP {

  public HomeController() {
    super();
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    Map<String, Object> model = this.inicializarModelo(ctx);
    ctx.render("home.hbs", model);
  }
}

package domain.models.Controllers;

import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class FiltroDeAutenticacion implements Handler {

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    if (!usuarioHaIniciadoSesion(ctx)) {
      ctx.attribute("usuarioNoAutenticado", true);
    }
  }

  // Método de ejemplo para verificar si el usuario ha iniciado sesión
  private boolean usuarioHaIniciadoSesion(Context ctx) {
    return ctx.sessionAttribute("user_id") != null;
  }
}
package domain.models.Controllers;

import domain.Repositorios.RepoUsuario;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

public class SesionController extends HandlerTP {

  public SesionController() {
  }

  public void mostrarLogin(@NotNull Context ctx) {
    if (ctx.sessionAttribute("user_id") != null) {
      ctx.redirect("/home");
    } else {
      ctx.redirect("/login");
    }
  }
  public void iniciarSesion(@NotNull Context ctx) {

    EntityManager entity = PerThreadEntityManagers.getEntityManager();

    try {
      Usuario usuario = entity.createQuery(
              "select U from Usuario U where U.username = :username and U.password = :password",
              Usuario.class)
          .setParameter("username", ctx.formParam("username"))
          .setParameter("password", ctx.formParam("password"))
          .getSingleResult();

      entity.close();
      ctx.sessionAttribute("user_id", usuario.getId());
      ctx.redirect("/");

    } catch (Exception e) {
      entity.close();

      ctx.sessionAttribute("user_id", null);
      ctx.redirect("/");
    }
  }

  public void cerrarSesion(@NotNull Context ctx) {
    ctx.sessionAttribute("user_id", null);
    ctx.redirect("/");
  }


  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);
    if(ctx.method() == HandlerType.GET)
      handleGetRequest(ctx);
    else
      handlePostRequest(ctx);
  }

  public void handleGetRequest(Context ctx) {
    if (ctx.path().startsWith("/login")) {
      Map<String, Object> model = new HashMap<>();
      ctx.render("inicioDeSesion.hbs", model);
    }
    if (ctx.path().startsWith("/miembros")) {
      Map<String, Object> model = new HashMap<>();
      ctx.render("miembros.hbs", model);
    }

  }

  public void handlePostRequest(Context ctx) {
//    String username = ctx.formParam("username");
//    String password = ctx.formParam("password");
//
//    if (isValidUser(username, password)) {
//      // Autenticación exitosa
//      ctx.status(HttpStatus.OK);
//      ctx.result("Inicio de sesión exitoso. ¡Bienvenido, " + username + "!");
//      ctx.sessionAttribute("sesionIniciada", true);
//      ctx.sessionAttribute("username", username);
//      ctx.sessionAttribute("password", password);
//      ctx.redirect("/home");
//    } else {
//      // Autenticación fallida
//      ctx.status(HttpStatus.UNAUTHORIZED);
//      ctx.result("Error de inicio de sesión. Verifica tus credenciales.");
//    }
  }
}

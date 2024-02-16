package domain.models.Controllers;

import domain.models.entities.usuario.Administrador;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;


import javax.persistence.EntityManager;
import java.util.HashMap;
import java.util.Map;

public abstract class HandlerTP implements Handler {

  private EntityManagerFactory entityManagerFactory;

  public HandlerTP(EntityManagerFactory entityManagerFactory) {
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    if (ctx.attribute("usuarioNoAutenticado") != null) {
      ctx.redirect("/login");
    }
  }
  public Map<String, Object> inicializarModelo(Context context) {
    Map<String, Object> modelo = new HashMap<>();

    if (context.sessionAttribute("user_id") != null) {
      Usuario usuario = getUsuario(context);
      modelo.put("usuario", usuario);
      modelo.put("sesionIniciada", usuario != null && usuario.getId() != null);
      modelo.put("esAdministrador", usuario.getTipoUsuario() instanceof Administrador);
    }

    return modelo;
  }

  public Usuario getUsuario(Context context) {
    EntityManager entity = entityManagerFactory.createEntityManager();

    entity.getTransaction().begin();

    Usuario usuario = entity.createQuery("SELECT u FROM Usuario u where u.id= :id", Usuario.class)
        .setParameter("id", context.sessionAttribute("user_id"))
        .getSingleResult();
    entity.close();

    return usuario;

  }
}

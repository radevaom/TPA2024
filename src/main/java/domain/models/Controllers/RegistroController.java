package domain.models.Controllers;

import domain.models.entities.comunicacion.AlertaCuandoSucede;
import domain.models.entities.comunicacion.MedioComunicacion;
import domain.models.entities.comunicacion.TipoAlerta;
import domain.models.entities.converters.MedioComunicacionConverter;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import javax.persistence.EntityManager;
import org.jetbrains.annotations.NotNull;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

public class RegistroController extends HandlerTP {

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);
    if(ctx.method() == HandlerType.GET)
      handleGetRequest(ctx);
    else
      handlePostRequest(ctx);
  }
  public void handleGetRequest(Context ctx) {
    Map<String, Object> model = inicializarModelo(ctx);
    if (ctx.sessionAttribute("primera_vez") == null){
      model.put("primeraVez", true);
    }else {
      model.put("primeraVez", ctx.sessionAttribute("primera_vez"));
      model.put("valorNombre", ctx.sessionAttribute("nombre"));
      model.put("valorApellido", ctx.sessionAttribute("apellido"));
      model.put("valorNroDocumento", ctx.sessionAttribute("nro_documento"));
      model.put("valorUsername", ctx.sessionAttribute("username"));
    }
    ctx.render("registro.hbs", model);
  }
  public void handlePostRequest(Context ctx) {

    if (Objects.equals(ctx.formParam("tipoUsu"), "Persona")) {

      EntityManager entity = PerThreadEntityManagers.getEntityManager();
      entity.getTransaction().begin();

      TipoAlerta alerta = entity.createQuery("select a from TipoAlerta a where a.id = :id", TipoAlerta.class)
              .setParameter("id", Long.parseLong(ctx.formParam("tipoAlerta")))
              .getSingleResult();

      AlertaCuandoSucede alertaCuandoSucede = new AlertaCuandoSucede();



      Persona persona = new Persona(ctx.formParam("email"),
          ctx.formParam("nombre"),
          ctx.formParam("apellido"),
          alerta);

      persona.setMedioComunicacionPreferido(new MedioComunicacionConverter().convertToEntityAttribute(ctx.formParam("medioComu")));

      Usuario usuario = new Usuario(ctx.formParam("username"),
          ctx.formParam("password"),
          persona);

      entity.persist(alertaCuandoSucede);
      entity.persist(persona);
      entity.persist(usuario);
      entity.getTransaction().commit();
      entity.close();
      ctx.redirect("/");
    }
  }

}

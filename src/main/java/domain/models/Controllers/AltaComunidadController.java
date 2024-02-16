package domain.models.Controllers;

import domain.Repositorios.RepoComunidad;
import domain.Repositorios.RepoIncidente;
import domain.models.entities.comunicacion.AlertaCuandoSucede;
import domain.models.entities.comunicacion.TipoAlerta;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.converters.MedioComunicacionConverter;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.miembro.MiembroAdministrador;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HandlerType;
import io.javalin.http.HttpStatus;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;


import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

public class AltaComunidadController extends HandlerTP {
  private EntityManagerFactory entityManagerFactory;
  public AltaComunidadController( EntityManagerFactory entityManagerFactory){
    super(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
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
    Map<String, Object> model = inicializarModelo(ctx);

    ctx.render("crearComunidad.hbs", model);
  }


  public void handlePostRequest(Context ctx) {
    Map<String, Object> model = inicializarModelo(ctx);

    // Obtener el usuario en sesión
    Long userId = ctx.sessionAttribute("user_id");

    EntityManager entity = entityManagerFactory.createEntityManager();
    //entity.getTransaction().begin();

    Usuario usuarioEnSesion = entity.find(Usuario.class, userId);

      if (usuarioEnSesion != null) {
        entity.getTransaction().begin();

         Persona persona = (Persona) usuarioEnSesion.getTipoUsuario();
         persona.crearComunidad(ctx.formParam("nombreComunidad"));

        List<Comunidad> comunidades = persona.getMiembros().stream().map(Miembro::getComunidad).collect(Collectors.toList());
        Optional<Comunidad> comunidad = comunidades.stream()
            .filter(comunidad1 -> comunidad1.getNombre().equals(ctx.formParam("nombreComunidad")))
            .findFirst();

        Comunidad comunidad2 = comunidad.get();


        List<Miembro> miembrosDeLaComunidad = new ArrayList<>();

        comunidad.ifPresent(comunidadPresente -> {
          miembrosDeLaComunidad.addAll(comunidadPresente.getMiembros());
        });


        List<MiembroAdministrador> administradores = miembrosDeLaComunidad.stream()
            .filter(miembro -> miembro instanceof MiembroAdministrador)
            .map(miembro -> (MiembroAdministrador) miembro)
            .collect(Collectors.toList());

        MiembroAdministrador miembroAdministrador = administradores.get(0);

        entity.persist(comunidad2);
        entity.persist(miembroAdministrador);
        entity.persist(persona);
        entity.getTransaction().commit();
        entity.close();

        ctx.redirect("/comunidades");

      }

  }
}


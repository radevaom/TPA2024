package domain.models.Controllers;

import domain.Repositorios.RepoComunidad;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.miembro.MiembroNormal;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UIComunidadController extends HandlerTP {
  public UIComunidadController(RepoComunidad repoComunidad) {

  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    Map<String, Object> model = inicializarModelo(ctx);

    EntityManager entity = PerThreadEntityManagers.getEntityManager();

    Comunidad comunidad = obtenerComunidadDesdeRepositorio(ctx, entity);
    model.put("comunidad", comunidad);


    Comunidad comunidadNoPertence = obtenerComunidadNoPertence(ctx, entity);
    model.put("comunidadNoPertence", comunidadNoPertence);


    entity.close();
    ctx.render("comunidad.hbs", model);
  }

  private Comunidad obtenerComunidadNoPertence(Context ctx, EntityManager entity) {
    String comunidadId = ctx.pathParam("comunidadId");

    //EntityManager entity = PerThreadEntityManagers.getEntityManager();
    entity.getTransaction().begin();
    List<Comunidad> comunidades = entity.createQuery("SELECT e FROM Comunidad e", Comunidad.class)
        .getResultList();

    Comunidad comunidadEncontrada = new Comunidad() ;
//        = comunidades.stream()
//        .filter(comunidad -> comunidad.getId().toString().equals(4))
//        .findFirst()
//        .orElse(null);

    System.out.println("Hola, mundo: " +  comunidades.get(4).getId() + " y " + comunidades.get(4).getNombre());
    System.out.println("EL ID BUSCADO ES: " +  comunidadId);

    for (Comunidad comunidad : comunidades) {
      if (comunidad.getId().toString() == comunidadId) {
        comunidadEncontrada = comunidad;
        System.out.println("encontrarmos el id 4: " +  comunidadEncontrada.getId());
        break;
      }
    }
    int numeroInt = Integer.parseInt(comunidadId);
    comunidadEncontrada = comunidades.get(numeroInt-1);
    System.out.println("encontrarmos la comunidad de id: " +  comunidadEncontrada.getId());
    System.out.println("Hola, mundo: " +  comunidadEncontrada.getId() + " y " + comunidadEncontrada.getNombre());

    return comunidadEncontrada;
  }

  private Miembro nuevoMiembro() {
    Comunidad comunidad = new Comunidad("NuevaComunidad");
    Persona persona = new Persona();
    Miembro miembro = new MiembroNormal(comunidad, persona);
    return miembro;
  }


  private Comunidad obtenerComunidadDesdeRepositorio(Context ctx, EntityManager entity) {

    String comunidadId = ctx.pathParam("comunidadId"); // Reemplaza con el nombre correcto del parámetro

    Long comunidadId1 = Long.valueOf(comunidadId);

    Long userId = ctx.sessionAttribute("user_id");
    //EntityManager entity = PerThreadEntityManagers.getEntityManager();
    Usuario usuarioEnSesion = entity.find(Usuario.class, userId);

    List<Comunidad> comunidades = obtenerComunidades(usuarioEnSesion.getTipoUsuario().getMiembros());

    // Retorno de ejemplo para evitar errores de compilación
    return comunidades.stream().filter(comunidad -> comunidad.getId() == comunidadId1).findFirst().orElse(null);
  }


  private static List<Comunidad> obtenerComunidades(List<Miembro> listaMiembros) {
    List<Comunidad> listaComunidades = new ArrayList<>();

    for (Miembro miembro : listaMiembros) {
      Comunidad comunidad = miembro.getComunidad();

      // Verificar si la comunidad ya está en la lista
      if (!listaComunidades.contains(comunidad)) {
        listaComunidades.add(comunidad);
      }
    }

    return listaComunidades;
  }

  public void unirmeComunidad(Context context) {
    EntityManager entity = PerThreadEntityManagers.getEntityManager();


    Comunidad comunidad = obtenerComunidadNoPertence(context, entity);


    Long userId = context.sessionAttribute("user_id");
    //EntityManager entity = PerThreadEntityManagers.getEntityManager();
    Usuario usuarioEnSesion = entity.find(Usuario.class, userId);
    Persona persona = (Persona) usuarioEnSesion.getTipoUsuario();
    persona.unirseComunidad(comunidad);


    //Miembro miembro1 = persona.getMiembros().stream().filter(miembro -> miembro.getComunidad() == comunidad).findFirst();


    Miembro miembroEncontrado = persona.getMiembros().stream()
        .filter(miembro -> miembro.getComunidad() == comunidad)
        .findFirst()
        .orElseGet(() -> {
          // Lógica para manejar el caso en que no se encuentra el miembro
          System.out.println("Miembro no encontrado");
          return null; // o proporciona un valor por defecto
        });


    System.out.println("Miembro comunidad: " +  miembroEncontrado.getComunidad().getNombre());
    System.out.println("Miembro persona: " +  miembroEncontrado.getPersona().getNombre());

    entity.persist(miembroEncontrado);
    entity.getTransaction().commit();
    entity.close();

    context.redirect("/comunidades");
  }

  public void salirComunidad(Context context) {
    EntityManager entity = PerThreadEntityManagers.getEntityManager();

    Comunidad comunidad = obtenerComunidadNoPertence(context, entity);

    Long userId = context.sessionAttribute("user_id");
    Usuario usuarioEnSesion = entity.find(Usuario.class, userId);
    Persona persona = (Persona) usuarioEnSesion.getTipoUsuario();
    persona.abandonarComunidad(comunidad);

    entity.persist(persona);
    entity.getTransaction().commit();
    entity.close();

    context.redirect("/comunidades");
  }
}

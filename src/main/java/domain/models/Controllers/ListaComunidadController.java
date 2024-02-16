package domain.models.Controllers;

import domain.Repositorios.RepoComunidad;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.TipoUsuario;
import domain.models.entities.usuario.Usuario;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.uqbarproject.jpa.java8.extras.PerThreadEntityManagers;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ListaComunidadController extends HandlerTP {
  private RepoComunidad repoComunidad;

  public ListaComunidadController(RepoComunidad repoComunidad) {
    super();
    this.repoComunidad = repoComunidad;
  }

//  @Override
//  public void handle(@NotNull Context ctx) throws Exception {
//    super.handle(ctx);
//
//    Map<String, Object> model = inicializarModelo(ctx);
//    EntityManager entity = PerThreadEntityManagers.getEntityManager();
//    entity.getTransaction().begin();
//    List<Comunidad> results = entity.createQuery("SELECT e FROM Comunidad e", Comunidad.class)
//        .getResultList();
//
//    model.put("comunidades", results);
//
//    ctx.render("/listadoComunidades.hbs", model);
//    entity.close();
//
//  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);

    Map<String, Object> model = inicializarModelo(ctx);

    // Obtener el usuario en sesión
    Long userId = ctx.sessionAttribute("user_id");
    EntityManager entity = PerThreadEntityManagers.getEntityManager();
    Usuario usuarioEnSesion = null;
    if (userId != null) {
      usuarioEnSesion = entity.find(Usuario.class, userId);
      // Resto del código relacionado con el usuario autenticado...
    } else {
      // Manejar el caso en el que el usuario no está autenticado
      // Puedes redirigir a una página de inicio de sesión u otra acción
      // ...
    }

    // Verificar si el usuario está autenticado
    if (usuarioEnSesion != null) {
    entity.getTransaction().begin();
    List<Comunidad> comunidades = entity.createQuery("SELECT e FROM Comunidad e", Comunidad.class)
        .getResultList();

    System.out.println("El total de comunidades del sistema es: " + comunidades.size() + " comunidades");

      List<Miembro> miembros = usuarioEnSesion.getTipoUsuario().getMiembros();

      System.out.println("El uruario actual pertenece a : " + miembros.size() + " comunidadess");
      //System.out.println("y solo hay: " + miembros.stream().filter(miembro -> miembro.getVigente() == Boolean.TRUE).toList().size() + " miembros vigentes");

      //List<Miembro> miembrosVigentes = miembros.stream().filter(miembro -> miembro.getVigente() == Boolean.TRUE).toList();

      List<Comunidad> comunidadesFiltradas = comunidades.stream()
          .filter(comunidad -> comunidad.getMiembros().stream().anyMatch(miembros::contains))
          .collect(Collectors.toList());

      System.out.println("Y ahora lista tiene: " + comunidadesFiltradas.size() + " comunidades");

      List<Comunidad> misComunidades = obtenerComunidades(usuarioEnSesion.getTipoUsuario().getMiembros());
    //comunidades.stream().filter(comunidad -> usuarioEnSesion.getTipoUsuario().getMiembros() );

      Iterator<Comunidad> iterator = comunidades.iterator();
      while (iterator.hasNext()) {
        Comunidad comunidad = iterator.next();
        if (comunidadesFiltradas.contains(comunidad)) {
          iterator.remove();
        }
      }

    model.put("comunidades", comunidades);
    model.put("misComunidades", comunidadesFiltradas);

    ctx.render("/listadoComunidades.hbs", model);
    entity.close();
    } else {
      // Manejar el caso en el que el usuario no esté autenticado
      // Puedes redirigir a una página de inicio de sesión u otra acción
      // ...

      //entity.close();
    }
  }

  // Método para obtener el usuario en sesión desde el contexto
  private Usuario obtenerUsuarioEnSesion(Context ctx) {
    Long userId = ctx.sessionAttribute("user_id");
    if (userId != null) {
      EntityManager entity = PerThreadEntityManagers.getEntityManager();
      try {
        return entity.find(Usuario.class, userId);
      } finally {
        entity.close();
      }
    }
    return null;
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
}

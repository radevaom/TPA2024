package domain.models.Controllers;

import domain.Repositorios.RepoRanking;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.ranking.Ranking;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import domain.models.entities.usuario.TipoUsuario;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;


public class ListaRankingIncidenteController extends HandlerTP {

  private EntityManagerFactory entityManagerFactory;

  public ListaRankingIncidenteController(EntityManagerFactory entityManagerFactory) {
    super(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);
    Map<String, Object> model = inicializarModelo(ctx);

    EntityManager entity = entityManagerFactory.createEntityManager();

    List<Comunidad> comunidad = entity.createQuery("select C from Comunidad C",
        Comunidad.class).getResultList();
    List<Incidente> incidentes = entity.createQuery("select I from Incidente I",
        Incidente.class).getResultList();
    List<EntidadPrestadoraServicioPublico> entidadPrestadoraServicioPublicos =
        entity.createQuery("SELECT t from TipoUsuario t", TipoUsuario.class).getResultList()
        .stream().filter(tipoUsuario -> tipoUsuario instanceof EntidadPrestadoraServicioPublico)
            .map(tipoUsuario -> (EntidadPrestadoraServicioPublico) tipoUsuario).toList();


    Ranking ranking = new Ranking(incidentes, comunidad, entidadPrestadoraServicioPublicos,
        LocalDate.of(2024, 2, 10),
        LocalDate.of(2024, 2, 20));

    Map<String, Double> rankingCierreEntidad = ranking.tiempoCierrePorEntidad();
    Map<String, Integer> rankingCantidadReportados = ranking.cantidadIncidentesReportadosPorEntidad();

    String primeraClave = new ArrayList<>(rankingCierreEntidad.keySet()).get(0);
    Double primerValor = rankingCierreEntidad.get(primeraClave);

    //ESTO NO SE ESTA USANDO
    model.put("rankingClave", primeraClave);
    model.put("rankingValor", primerValor);






    List<String> grupos = new ArrayList<>(rankingCierreEntidad.keySet());
    List<Double> puntajes = new ArrayList<>(rankingCierreEntidad.values());
    model.put("grupos", grupos);
    model.put("puntajes", puntajes);






    entity.close();
    ctx.render("ranking.hbs", model);
  }
}

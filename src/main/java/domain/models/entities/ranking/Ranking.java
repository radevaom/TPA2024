package domain.models.entities.ranking;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Setter
@Getter
public class Ranking {

  @Id
  @GeneratedValue
  private Long id;

  private List<Incidente> incidentes;
  private List<Comunidad> comunidades;
  private LocalDate fechaInicio;
  private LocalDate fechaFin;
  private List<EntidadPrestadoraServicioPublico> entidadesPrestadoras;

  public Ranking(List<Incidente> incidentes, List<Comunidad> comunidades,
                 List<EntidadPrestadoraServicioPublico> entidadesPrestadoras, LocalDate fechaInicio, LocalDate fechaFin) {
    this.incidentes = incidentes;
    this.comunidades = comunidades;
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
    this.entidadesPrestadoras = entidadesPrestadoras;
  }

  public Map<String, Double> tiempoCierrePorEntidad() {
    Map<String, Double> rankingCierreEntidad = new HashMap<String, Double>();

    for (EntidadPrestadoraServicioPublico entidad: entidadesPrestadoras) {
      List<Incidente> incidentesEntidad =
          obtenerIncidentesPorEntidad(entidad.serviciosDeEntidades());
      Double tiempoCierre = incidentesEntidad.stream().mapToDouble(incidente ->
          Period.between(incidente.getFecha().toLocalDate(),
          incidente.getCierre().toLocalDate()).getDays()).sum() / incidentesEntidad.size();
      rankingCierreEntidad.put(entidad.getNombre(), tiempoCierre);
    }

    return rankingCierreEntidad;
  }

  public Map<String, Double> tiempoCierrePorEntidadEspecifica(
      EntidadPrestadoraServicioPublico entidad) {
    Map<String, Double> rankingCierreEntidad = new HashMap<String, Double>();

    List<Incidente> incidentesEntidad = obtenerIncidentesPorEntidad(entidad.serviciosDeEntidades());

    Double tiempoCierre = incidentesEntidad.stream().mapToDouble(incidente ->
        Period.between(incidente.getFecha().toLocalDate(),
            incidente.getCierre().toLocalDate()).getDays()).sum() / incidentesEntidad.size();
    rankingCierreEntidad.put(entidad.getNombre(), tiempoCierre);

    return rankingCierreEntidad;
  }

  public Map<String, Integer> cantidadIncidentesReportadosPorEntidad() {
    Map<String, Integer> rankingCantidadReportados = new HashMap<String, Integer>();

    for (EntidadPrestadoraServicioPublico entidad : entidadesPrestadoras) {
      rankingCantidadReportados.put(entidad.getNombre(),
          obtenerIncidentesPorEntidad(entidad.serviciosDeEntidades()).size());
    }

    return rankingCantidadReportados;
  }

  public Map<String, Integer> cantidadIncidentesReportadosPorEntidadEspecifica(
      EntidadPrestadoraServicioPublico entidad) {
    Map<String, Integer> rankingCantidadReportados = new HashMap<String, Integer>();


    rankingCantidadReportados.put(entidad.getNombre(),
        obtenerIncidentesPorEntidad(entidad.serviciosDeEntidades()).size());


    return rankingCantidadReportados;
  }

  public Map<String, Integer> gradoDeImpactoPorComunidad() {
    Map<String, Integer> rankingImpactoComunidad = new HashMap<String, Integer>();

    for (Comunidad comunidad: comunidades) {
      Integer cantidadMiembros = comunidad.getMiembros().size();
      rankingImpactoComunidad.put(comunidad.getNombre(), cantidadMiembros);
    }

    return rankingImpactoComunidad;
  }

  public List<Incidente> obtenerIncidentesPorEntidad(List<Servicio> servicios) {
    List<Incidente> incidentesEntidad = new ArrayList<>();
    for (Servicio servicio : servicios) {
      incidentesEntidad.addAll(this.incidentes.stream().filter(incidente ->
           incidente.getServicio() == servicio).collect(Collectors.toList()));
    }
    return incidentesEntidad;
  }
}

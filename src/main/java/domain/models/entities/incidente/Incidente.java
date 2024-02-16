package domain.models.entities.incidente;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.converters.LocalDateTimeConverter;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Usuario;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Incidente")
public class Incidente {

  @Id
  @GeneratedValue
  private Long id;
  @OneToOne(fetch = FetchType.LAZY)
  private Servicio servicio;
  @Convert(converter = LocalDateTimeConverter.class)
  private LocalDateTime fecha;
  @Convert(converter = LocalDateTimeConverter.class)
  private LocalDateTime cierre;
  @Column
  private String observaciones;
  @OneToOne
  private Usuario usuarioInformador;
  @OneToOne
  private Usuario usuarioCierre;

  @ManyToOne
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  private Comunidad comunidad;

  public Incidente(Servicio servicio, String observaciones, Centroide ubicacion) {
    this.servicio = servicio;
    this.observaciones = observaciones;
    this.fecha = LocalDateTime.now();
    this.cierre = null;
  }

  public Incidente() {}

  public void cerrar() {
    this.cierre = LocalDateTime.now();
  }

  public String mensaje() {
    return "%s - %s".formatted(this.servicio.getNombre(), this.getObservaciones());
  }

  public Centroide getUbicacion() {
    return servicio.getEstacion().getLocalizacion().getUbicacion();
  }

}

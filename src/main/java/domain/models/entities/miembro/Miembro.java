package domain.models.entities.miembro;

import domain.models.entities.comunicacion.Notificacion;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "administrador")
public abstract class Miembro {

  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  @JoinColumn(name = "comunidad_id", referencedColumnName = "id")
  protected Comunidad comunidad;
  @ManyToOne
  @JoinColumn(name = "persona_id", referencedColumnName = "id")
  protected Persona persona;

  @OneToMany(cascade = CascadeType.ALL)
  protected List<Incidente> incidentesReportados;

  public Miembro(Comunidad comunidad, Persona persona) {
    this.comunidad = comunidad;
    this.persona = persona;
  }

  public Miembro() {}

  public void informarIncidente(Servicio servicio, String observaciones, Centroide ubicacion) {
    Incidente nuevoIncidente = new Incidente(servicio, observaciones,ubicacion);
    this.comunidad.agregarIncidente(nuevoIncidente);
  }

  public void recibirNotificacion(Notificacion notificacion) {
    this.persona.recibirNotificacion(notificacion);
  }

  public Centroide obtenerUbicacion() {
    return persona.getLocalizacion().getUbicacion();
  }
}

package domain.models.entities.serviciospublicos.establecimiento;

import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.servicio.Servicio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity(name = "estacion")
@DiscriminatorValue(value = "estacion")
public class Estacion extends Establecimiento {

  @OneToMany(mappedBy = "estacion", fetch = FetchType.LAZY)
  private List<Servicio> servicios;

  public Estacion(String nombre, Localizacion localizacion, List<Servicio> servicios) {
    this.setLocalizacion(localizacion);
    this.setNombre(nombre);
    this.servicios = servicios;
    asignarEstacionServicio();
  }

  public Estacion() {}

  public void asignarEstacionServicio() {
    this.servicios.forEach(servicio -> servicio.setEstacion(this));
  }

}

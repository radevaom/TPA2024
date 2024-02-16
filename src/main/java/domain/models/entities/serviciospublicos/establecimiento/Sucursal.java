package domain.models.entities.serviciospublicos.establecimiento;

import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.servicio.Servicio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Entity
@DiscriminatorValue(value = "sucursal")
public class Sucursal extends Establecimiento {

  @ManyToOne
  @JoinColumn(name = "organizacion_id", referencedColumnName = "id")
  private Organizacion organizacion;

  public Sucursal(Localizacion localizacion) {
    this.setLocalizacion(localizacion);
  }

  public Sucursal(String nombre, Localizacion localizacion) {
    this.setLocalizacion(localizacion);
    this.setNombre(nombre);
  }
  public Sucursal(){};

  public List<Servicio> getServicios() {
      return List.of(); //Probablemente sucursal tambien tenga servicios
  }


}
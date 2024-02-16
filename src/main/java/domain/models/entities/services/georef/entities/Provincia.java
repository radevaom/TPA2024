package domain.models.entities.services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Getter @Setter
@Entity
@DiscriminatorValue("Provincia")
public class Provincia extends Localizacion {

  @OneToOne
  private Centroide centroide;

  public Provincia(String nombre) {
    this.setNombre(nombre);
  }

  public Provincia() {}

  public Provincia(Centroide centroide) { this.setCentroide(centroide); }

  @Override
  public Centroide getUbicacion() {
    return centroide;
  }
}

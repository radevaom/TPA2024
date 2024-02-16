package domain.models.entities.services.georef.entities;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Table(name = "Localizacion")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_localizacion")
public abstract class Localizacion {
  @Id
  @GeneratedValue
  private Long id;
  @Column
  private String nombre;

  public abstract Centroide getUbicacion();
}

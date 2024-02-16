package domain.models.entities.servicio;

import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_servicio")
public abstract class Servicio {

  @Id
  @GeneratedValue
  private Long id;
  @Column
  private String nombre;
  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "estacion_id", referencedColumnName = "id")
  private Estacion estacion;


  public Servicio(String nombre) {
    this.nombre = nombre;
  }
  public Servicio(String nombre, Estacion estacion) {
    this.nombre = nombre;
    this.estacion = estacion;
  }

  public Servicio(){}

  public abstract void marcarPrestado();

  public abstract void marcarNoPrestado();

  public abstract Boolean getPrestado();

}

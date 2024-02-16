package domain.models.entities.servicio;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Transient;

import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;


@Setter
@Getter
@Entity
@DiscriminatorValue(value = "Servicio_Simple")
public class ServicioSimple extends Servicio {


  @Column
  private Boolean prestado;
  @Column
  private String tipoDeServicio;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public ServicioSimple(String nombre, Boolean prestado, String tipoDeServicio) {
    super(nombre);
    this.prestado = prestado;
    this.tipoDeServicio = tipoDeServicio;
  }
  public ServicioSimple(String nombre, Boolean prestado, String tipoDeServicio, Estacion estacion) {
    super(nombre, estacion);
    this.prestado = prestado;
    this.tipoDeServicio = tipoDeServicio;
  }

  public ServicioSimple() {}

  public void marcarPrestado() {
    this.prestado = true;
  }

  public void marcarNoPrestado() {
    this.prestado = false;
  }

  public Boolean getPrestado() {
    return this.prestado;
  }

}

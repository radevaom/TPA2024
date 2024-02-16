package domain.models.entities.servicio;

import java.util.List;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "agrupacion")
public class Agrupacion extends Servicio {


  @OneToMany
  @JoinColumn(name="servicio_agrupador_id",referencedColumnName = "id")
  private List<Servicio> servicios;

  @SuppressWarnings("checkstyle:MissingJavadocMethod")
  public Agrupacion(String nombre, List<Servicio> servicios) {
    super(nombre);
    this.servicios = servicios;
  }

  public Agrupacion() {}

  public void agregarServicio(Servicio servicio) {
    this.servicios.add(servicio);
  }

  public void removerServicio(Servicio servicio) {
    this.servicios.remove(servicio);
  }

  public void marcarPrestado() {
    this.servicios.forEach(Servicio::marcarPrestado);
  }

  public void marcarNoPrestado() {
    this.servicios.forEach(Servicio::marcarNoPrestado);
  }

  public Boolean getPrestado() {
    return this.servicios.stream().allMatch(Servicio::getPrestado);
  }

}

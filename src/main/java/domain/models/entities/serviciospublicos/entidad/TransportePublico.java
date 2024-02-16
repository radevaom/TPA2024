package domain.models.entities.serviciospublicos.entidad;

import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter @Setter
@Entity(name = "transporte_publico")
@DiscriminatorValue(value = "transporte_publico")
public class TransportePublico extends Entidad {


  @OneToOne
  private Estacion origen;
  @OneToOne
  private Estacion destino;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "entidad_estacion", inverseJoinColumns = @JoinColumn(name = "estacion_id"))
  private List<Estacion> estaciones;
  @Enumerated(EnumType.STRING)
  private TipoTransporte tipoTransporte;

  public TransportePublico(String nombre, Estacion origen, Estacion destino,
                           List<Estacion> estaciones,
                           TipoTransporte tipoTransporte, Localizacion localizacion) {

    this.setLocalizacion(localizacion);
    this.setNombre(nombre);
    this.origen = origen;
    this.destino = destino;
    this.estaciones = new ArrayList<>(estaciones);
    this.tipoTransporte = tipoTransporte;
  }

  public TransportePublico() {}

  public void agregarEstacion(Estacion nuevaEstacion) {
    this.estaciones.add(nuevaEstacion);
  }

  public void eliminarEstacion(Estacion estacionAEliminar) {
    this.estaciones.remove(estacionAEliminar);
  }

  public List<Servicio> getServicios() {
    return this.estaciones.stream().flatMap(estacion -> estacion.getServicios().stream()).collect(Collectors.toList());
  }

}

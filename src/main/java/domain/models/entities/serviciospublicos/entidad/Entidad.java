package domain.models.entities.serviciospublicos.entidad;

import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_entidad")
public abstract class Entidad {
  @Id
  @GeneratedValue
  private Long id;
  @Column
  private String nombre;
  @OneToOne
  private Localizacion localizacion;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "entidad_prestadora_id", referencedColumnName = "id")
  private EntidadPrestadoraServicioPublico entidadPrestadora;

  public abstract List<Servicio> getServicios();

}

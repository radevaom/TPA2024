package domain.models.entities.serviciospublicos.establecimiento;

import javax.persistence.*;
import java.util.List;

@Entity
public class Organizacion {
  @Id
  private Long id;
  @Column
  private String nombre;
  @OneToMany(mappedBy = "organizacion", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<Sucursal> sucursales;
}

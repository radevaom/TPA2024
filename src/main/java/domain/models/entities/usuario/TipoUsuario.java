package domain.models.entities.usuario;

import javax.persistence.*;

import domain.models.entities.miembro.Miembro;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "tipo_usuario")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario")
public abstract class TipoUsuario {

  @Id
  @GeneratedValue
  private Long id;
  @Column
  private String nombre;

  @OneToOne
  @JoinColumn(name = "usuario_id", referencedColumnName = "id")
  private Usuario usuario;

  public TipoUsuario() {

  }

  public abstract List<Miembro> getMiembros();
}

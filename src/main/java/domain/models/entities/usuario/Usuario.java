package domain.models.entities.usuario;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@Table(name = "Usuario")
public class Usuario {
  @Id
  @GeneratedValue
  private Long id;

  @Column
  private String username;
  @Column
  private String password;
  @OneToOne(mappedBy = "usuario")
  private TipoUsuario tipoUsuario;
  @Column
  private Double puntosConfianza;

    public Usuario(String username, String password, TipoUsuario tipoUsuario) {
        this.username = username;
        this.password = password;
        this.tipoUsuario = tipoUsuario;
        tipoUsuario.setUsuario(this);
        this.puntosConfianza = 5d;
    }

    public Usuario() {
        this.puntosConfianza = 5d;
    }
}

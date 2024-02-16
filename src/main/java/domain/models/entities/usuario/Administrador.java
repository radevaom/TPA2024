package domain.models.entities.usuario;

import domain.models.entities.miembro.Miembro;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("Administrador")
public class Administrador extends TipoUsuario {
  @OneToOne
  private Persona personaDesignada;

  public Administrador(Persona personaDesignada, String nombre) {
    this.setNombre(nombre);
    this.personaDesignada = personaDesignada;
  }

  public Administrador() {}

  @Override
  public List<Miembro> getMiembros() {
    return personaDesignada.getMiembros();
  }

}

package domain.models.entities.miembro;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.usuario.Persona;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@DiscriminatorValue(value = "normal")
public class MiembroNormal extends Miembro {

  @Enumerated(EnumType.STRING)
  private RolEnComunidad rolEnComunidad;

  public MiembroNormal(Comunidad comunidad, Persona persona) {
    super(comunidad, persona);
    comunidad.getMiembros().add(this);
  }

  public MiembroNormal() {}


  public RolEnComunidad getRolEnComunidad() {
    return this.rolEnComunidad;
  }

  public void cambiarRolEnComunidad(RolEnComunidad nuevoRol){
    this.rolEnComunidad = nuevoRol;
  }

  public void actualizarUbicacion(Centroide nuevaUbicacion) {
    this.comunidad.reportarUbicacionDeMiembro(this);
  }
}

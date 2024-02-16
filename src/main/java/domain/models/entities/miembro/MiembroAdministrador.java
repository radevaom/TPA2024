package domain.models.entities.miembro;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.usuario.Persona;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "administrador")
public class MiembroAdministrador extends Miembro{
  public MiembroAdministrador(Comunidad comunidad, Persona persona) {
    super(comunidad, persona);
  }

  public MiembroAdministrador() {}

  public void agregarMiembroAComunidad(Persona persona){
    persona.agregarMiembro(new MiembroNormal(comunidad, persona));
  }

  public void eliminarMiembroDeComunidad(Persona persona){
    persona.eliminarMiembroDeComunidad(comunidad);
  }

  public void cambiarNombreDeLaComunidad(String nuevoNombre){
    comunidad.setNombre(nuevoNombre);
  }


}

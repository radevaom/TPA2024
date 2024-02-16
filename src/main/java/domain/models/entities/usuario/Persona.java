package domain.models.entities.usuario;

import domain.models.entities.comunicacion.MedioComunicacion;
import domain.models.entities.converters.MedioComunicacionConverter;
import domain.models.entities.comunicacion.Notificacion;
import domain.models.entities.comunicacion.TipoAlerta;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.miembro.MiembroAdministrador;
import domain.models.entities.miembro.MiembroNormal;
import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.serviciospublicos.entidad.Entidad;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@DiscriminatorValue("Persona")
public class Persona extends TipoUsuario {

  @Column
  private String apellido;
  @OneToOne
  private Localizacion localizacion;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(inverseJoinColumns = @JoinColumn(name = "entidad_asociada_id"))
  private List<Entidad> entidadesAsociadas;
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(inverseJoinColumns = @JoinColumn(name = "servicio_asociado_id"))
  private List<Servicio> serviciosAsociados;
  @Getter
  @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL)
  private List<Miembro> miembros;
  @Convert(converter = MedioComunicacionConverter.class)
  private MedioComunicacion medioComunicacionPreferido;
  @OneToOne
  @JoinColumn(name = "alerta_id", referencedColumnName = "id")
  private TipoAlerta tipoAlerta;
  @Column
  private String email;

  public Persona(String email, String nombre, String apellido, TipoAlerta tipoAlerta) {
    this.email = email;
    this.miembros = new ArrayList<>();
    this.entidadesAsociadas = new ArrayList<>();
    this.serviciosAsociados = new ArrayList<>();
    this.apellido = apellido;
    this.tipoAlerta = tipoAlerta;
    this.setNombre(nombre);
  }

  public Persona(){}

  public void agregarEntidad(Entidad entidad) {entidadesAsociadas.add(entidad);}
  public void eliminarEntidad(Entidad entidad) {entidadesAsociadas.remove(entidad);}

  public void agregarMiembro(Miembro nuevoMiembro) {
    miembros.add(nuevoMiembro);
  }

  public void unirseComunidad(Comunidad comunidad) {
    Miembro nuevoMiembro = new MiembroNormal(comunidad, this);
    this.agregarMiembro(nuevoMiembro);
  }
  public void abandonarComunidad(Comunidad comunidad) {

    Optional<Miembro> miembroOptional = miembros.stream()
        .filter(miembro -> miembro.getComunidad().equals(comunidad))
        .findFirst();

    miembroOptional.ifPresent(miembro -> miembro.setPersona(null));

    miembros.removeIf(miembro -> miembro.getComunidad().equals(comunidad));
  }

  public void eliminarMiembroDeComunidad(Comunidad comunidad) {
    miembros.removeIf(miembro -> miembro.getComunidad().equals(comunidad));
  }

  public void agregarServico(Servicio servicio) {serviciosAsociados.add(servicio);}
  public void eliminarServicio(Servicio servicio) {serviciosAsociados.remove(servicio);}


  //Todo revisar si los servicios de interes son de unos servicios asociados, o entidades asociadas, o a partir de una entidad que se manda por parametro
  public List<Servicio> serviciosDeInteres() {
    return serviciosAsociados.stream().filter(Servicio::getPrestado).collect(Collectors.toList());
  }
  //Si se quiere consultar por una entidad puntual
  public List<Servicio> serviciosDeInteres(Entidad entidad) {
    return entidad.getServicios().stream().filter(Servicio::getPrestado).collect(Collectors.toList());
  }

  public void crearComunidad(String nombre){
    Comunidad comunidad = new Comunidad(nombre);
    Miembro nuevoMiembroAdministrador = new MiembroAdministrador(comunidad, this);
    comunidad.agregarMiembro(nuevoMiembroAdministrador);
    this.agregarMiembro(nuevoMiembroAdministrador);
  }
  public void asociarLocalizacion(Localizacion localizacion){
    //TODO
  }

  public void recibirNotificacion(Notificacion notificacion){
    tipoAlerta.alertar(notificacion, this.medioComunicacionPreferido, this.getEmail());
  }
}

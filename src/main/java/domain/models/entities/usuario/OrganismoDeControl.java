package domain.models.entities.usuario;

import domain.models.entities.miembro.Miembro;
import domain.models.entities.services.servicioCSV.CsvLoader;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@DiscriminatorValue("Organismo_Control")
public class OrganismoDeControl extends TipoUsuario {
  @OneToOne
  private Persona personaDesignada;
  @OneToMany(mappedBy = "organismoDeControl", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<EntidadPrestadoraServicioPublico> entidadesControladas;

  public OrganismoDeControl(Persona personaDesignada, String nombre) {
    this.setNombre(nombre);
    this.personaDesignada = personaDesignada;
    this.entidadesControladas = new ArrayList<>();
  }

  public OrganismoDeControl() {
    this.entidadesControladas = new ArrayList<>();
  }

  @Override
  public List<Miembro> getMiembros() {
    return personaDesignada.getMiembros();
  }

  public void agregarEntidadPrestadoraServicioPublico(EntidadPrestadoraServicioPublico entidad) {
    this.entidadesControladas.add(entidad);
    entidad.setOrganismoDeControl(this);
  }

  public void removerEntidadPrestadoraServicioPublico(EntidadPrestadoraServicioPublico entidad) {
    this.entidadesControladas.remove(entidad);
  }

  public String getEmail() {
    return this.getPersonaDesignada().getEmail();
  }

  public void cargarArchivoCSV(String rutaArchivo) {
    CsvLoader csvLoader = new CsvLoader();
    csvLoader.cargarCSVOrganismoDeControl(rutaArchivo, this);
  }
}

package domain.models.entities.usuario;

import domain.models.entities.miembro.Miembro;
import domain.models.entities.services.servicioCSV.CsvLoader;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.serviciospublicos.entidad.Entidad;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "entidad_prestadora")
@DiscriminatorValue("Entidad_Prestadora")
public class EntidadPrestadoraServicioPublico extends TipoUsuario {
  @Column
  private String email;
  @OneToOne
  private Persona personaDesignada;
  @OneToMany(mappedBy = "entidadPrestadora", cascade = CascadeType.ALL)
  private List<Entidad> serviciosPublicosOfrecidos; //Servicios Publicos

  @ManyToOne
  @JoinColumn(name = "organismo_control_id", referencedColumnName = "id")
  private OrganismoDeControl organismoDeControl;

  public EntidadPrestadoraServicioPublico(String nombre, String email) {
    this.setNombre(nombre);
    this.email = email;
    this.serviciosPublicosOfrecidos = new ArrayList<>();
  }

  public EntidadPrestadoraServicioPublico() {
    this.serviciosPublicosOfrecidos = new ArrayList<>();
  }

  @Override
  public List<Miembro> getMiembros() {
    return personaDesignada.getMiembros();
  }

  public void agregarServicioOfrecido(Entidad servicioPublico) {
    this.serviciosPublicosOfrecidos.add(servicioPublico);
    servicioPublico.setEntidadPrestadora(this);
  }

  public void removerServicioOfrecido(Entidad servicioPublico) {
    this.serviciosPublicosOfrecidos.remove(servicioPublico);
  }

  public void cargarArchivoCSV(String rutaArchivo) {
    CsvLoader csvLoader = new CsvLoader();
    csvLoader.cargarCSVEntdadPrestadora(rutaArchivo, this);
  }

  public List<Servicio> serviciosDeEntidades() {

    return this.serviciosPublicosOfrecidos.stream().flatMap(entidad ->
        entidad.getServicios().stream()).collect(Collectors.toList());
  }

}

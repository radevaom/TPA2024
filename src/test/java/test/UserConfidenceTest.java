package test;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.services.confidenceLevel.ConfidenceLevelService;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaComunidadOutput;
import domain.models.entities.services.confidenceLevel.entities.ConfianzaUsuarioOutput;
import domain.models.entities.services.georef.ServicioGeoref;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.services.georef.entities.ListadoDeMunicipios;
import domain.models.entities.services.georef.entities.ListadoDeProvincias;
import domain.models.entities.services.georef.entities.Provincia;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserConfidenceTest {
/*
  protected ConfidenceLevelService confidenceLevelService;
  private void initializeServicioConfianza(){
    this.confidenceLevelService = Common.getConfidenceLevelService();
  }

  @BeforeEach
  public void initialize() {
    this.initializeServicioConfianza();
  }

  @AfterEach
  public void clean(){

  }

  @Test
  void obtenerGradoDeConfianza() throws IOException {

    Usuario usuario = Common.getUsuario();

    Incidente incidente1 = crearIncidenteCompleto(usuario, Common.getUsuario2(), Common.getServicio1(), 1L, "No funciona este servicio",Common.getCentroide1(), Common.getComunidad(), 1l);
    Incidente incidente2 = crearIncidenteCompleto(Common.getUsuario2(), usuario, Common.getServicio2(), 2L, "No funciona este servicio",Common.getCentroide2(), Common.getComunidad(), 1l);
    Incidente incidente3 = crearIncidenteCompleto(usuario, Common.getUsuario2(), Common.getServicio3(), 3l,"No funciona este servicio",Common.getCentroide3(), Common.getComunidad(), 1l);

    incidente1.setCierre(LocalDateTime.now().plusMinutes(2));
    incidente2.setCierre(LocalDateTime.now().plusMinutes(2));
    incidente3.setCierre(LocalDateTime.now().plusDays(6));

    ConfianzaUsuarioOutput output = confidenceLevelService.obtenerGradoDeConfianzaDeUsuario(usuario, Arrays.asList(incidente1, incidente2, incidente3));

    Assertions.assertEquals(5.8, output.getNewConfidenceLevel());
  }

  @Test
  void obtenerGradoDeConfianzaComunidad() throws IOException {

    Miembro miembro1 = this.crearMiembroConGradoDeconfianza(1L, 1.5);
    Miembro miembro2 = this.crearMiembroConGradoDeconfianza(2L,2.0);
    Miembro miembro3 = this.crearMiembroConGradoDeconfianza(3L,3.0);
    Miembro miembro4 = this.crearMiembroConGradoDeconfianza(4L,3.5);
    Miembro miembro5 = this.crearMiembroConGradoDeconfianza(5L,4.0);
    Miembro miembro6 = this.crearMiembroConGradoDeconfianza(6L,4.5);

    Comunidad comunidad = new Comunidad();
    comunidad.setId(123L);
    comunidad.setMiembros(List.of(miembro1, miembro2, miembro3, miembro4, miembro5, miembro6));

    ConfianzaComunidadOutput output = confidenceLevelService.obtenerGradoDeConfianzaDeComunidad(comunidad);

    Assertions.assertEquals(2.6833333333333336, output.getConfidence());
  }

  private Miembro crearMiembroConGradoDeconfianza(Long idUsuario, Double gradoConfianza) {
    Miembro miembro = Common.getMiembroNormal();
    Usuario usuario = new Usuario("usuario", "1234", miembro.getPersona());
    usuario.setPuntosConfianza(gradoConfianza);
    usuario.setId(idUsuario);
    miembro.getPersona().setUsuario(usuario);
    return miembro;
  }

  private Incidente crearIncidenteCompleto(Usuario usuarioCreador, Usuario usuarioCierre, Servicio servicio, Long servicioId, String observaciones, Centroide centroide, Comunidad comunidad, Long comunidadId) {
    servicio.setId(servicioId);
    comunidad.setId(comunidadId);
    Incidente incidente1 = Common.getIncidente();
    incidente1.setId(1L);
    incidente1.setComunidad(comunidad);
    incidente1.setUsuarioInformador(usuarioCreador);
    incidente1.setUsuarioCierre(usuarioCierre);
    incidente1.setFecha(LocalDateTime.now());
    return incidente1;
  }
*/
}

package test;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.services.mergeCommunity.MergeCommunityService;
import domain.models.entities.services.mergeCommunity.entities.MergeCommunityResponse;
import domain.models.entities.services.mergeCommunity.entities.RecommendedMerges;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import domain.models.entities.usuario.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MergeCommunityTest {
/*
  protected MergeCommunityService mergeCommunityService;
  private void initializeServicioConfianza(){
    this.mergeCommunityService = Common.getMergeCommunityService();
  }

  @BeforeEach
  public void initialize() {
    this.initializeServicioConfianza();
  }

  @AfterEach
  public void clean(){

  }

  @Test
  void hayPosibleMerge() throws IOException {

    List<Comunidad> comunidadesAMergear = this.crearComunidadesMergeables();

    RecommendedMerges fusionesRecomendadas = mergeCommunityService.obtenerPosiblesFusiones(comunidadesAMergear);

    Assertions.assertEquals(fusionesRecomendadas.getPossibleMerges().size(), 1);
    Assertions.assertEquals(fusionesRecomendadas.getPossibleMerges().get(0).getCommunity1().getId(), comunidadesAMergear.get(0).getId().toString());
    Assertions.assertEquals(fusionesRecomendadas.getPossibleMerges().get(0).getCommunity2().getId(), comunidadesAMergear.get(1).getId().toString());
  }

  @Test
  void noHayPosibleMergePorMiembrosDistintos() throws IOException {

    List<Comunidad> comunidadesAMergear = this.crearComunidadesMergeables();
    comunidadesAMergear.get(0).getMiembros().get(0).setId(3L);

    RecommendedMerges fusionesRecomendadas = mergeCommunityService.obtenerPosiblesFusiones(comunidadesAMergear);

    Assertions.assertEquals(fusionesRecomendadas.getPossibleMerges().size(), 0);
  }

  @Test
  void mergeCommunities() throws IOException {

    List<Comunidad> comunidadesAMergear = this.crearComunidadesMergeables();

    MergeCommunityResponse response = mergeCommunityService.fusionarComunidades(comunidadesAMergear.get(0), comunidadesAMergear.get(1));

    Assertions.assertEquals(response.getMergedCommunity().size(), 3);
    Assertions.assertEquals(response.getMergedCommunity().get(0).getId(), comunidadesAMergear.get(0).getId().toString());
    Assertions.assertEquals(response.getMergedCommunity().get(1).getId(), comunidadesAMergear.get(1).getId().toString());
    Assertions.assertEquals(response.getMergedCommunity().get(2).getId(), "merged");
  }

  @Test
  void mergeCommunitiesThrowsError() throws IOException {

    List<Comunidad> comunidadesAMergear = this.crearComunidadesMergeables();
    comunidadesAMergear.get(0).getMiembros().get(0).setId(5L);

    MergeCommunityResponse response = mergeCommunityService.fusionarComunidades(comunidadesAMergear.get(0), comunidadesAMergear.get(1));

    Assertions.assertNull(response);
  }

  private List<Comunidad> crearComunidadesMergeables() {
    Comunidad comunidad1 = new Comunidad("Comunidad1");
    Comunidad comunidad2 = new Comunidad("Comunidad2");

    Incidente incidente1 = crearIncidenteCompleto(Common.getUsuario(), Common.getUsuario2(), Common.getServicio1(), 1L, "No funciona este servicio",Common.getCentroide1(), comunidad1, 1l);
    Incidente incidente2 = crearIncidenteCompleto(Common.getUsuario2(), Common.getUsuario(), Common.getServicio2(), 2L, "No funciona este servicio",Common.getCentroide2(), comunidad1, 1l);
    Incidente incidente3 = crearIncidenteCompleto(Common.getUsuario(), Common.getUsuario2(), Common.getServicio3(), 3L,"No funciona este servicio",Common.getCentroide3(), comunidad1, 1l);
    Incidente incidente4 = crearIncidenteCompleto(Common.getUsuario(), Common.getUsuario2(), Common.getServicio3(), 4L,"No funciona este servicio",Common.getCentroide3(), comunidad1, 1l);
    Incidente incidente5 = crearIncidenteCompleto(Common.getUsuario(), Common.getUsuario2(), Common.getServicio3(), 5L,"No funciona este servicio",Common.getCentroide3(), comunidad1, 1l);

    comunidad1.setIncidentesReportados(Arrays.asList(incidente1, incidente2, incidente3, incidente4));
    comunidad2.setIncidentesReportados(Arrays.asList(incidente1, incidente2, incidente3, incidente5));


    this.completarComunidad(comunidad1, 1L, LocalDateTime.now().minusYears(1L), 3D);
    this.completarComunidad(comunidad2, 2L, LocalDateTime.now().minusYears(1L), 3D);

    return List.of(comunidad1, comunidad2);
  }

  private void completarComunidad(Comunidad comunidad, Long id, LocalDateTime ultimaFusion, Double gradoConfianza) {
    comunidad.setId(id);
    comunidad.setFechaUltimaFusion(ultimaFusion);
    comunidad.setGradoConfianza(gradoConfianza);
    List<Miembro> miembros = new ArrayList<Miembro>();

    for(int i=0;i < 2; i++) {
      Miembro miembro = Common.getMiembroNormal();
      miembro.setId(Integer.toUnsignedLong(i));
      miembro.getPersona().setNombre("nombre".concat(String.valueOf(i)));
      miembros.add(miembro);
    }
    comunidad.setMiembros(miembros);
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
    servicio.setEstacion(new Estacion());
    servicio.getEstacion().setId(servicioId);
    comunidad.setId(comunidadId);
    Incidente incidente1 = Common.getIncidente();
    incidente1.setId(1L);
    incidente1.setComunidad(comunidad);
    incidente1.setUsuarioInformador(usuarioCreador);
    incidente1.setUsuarioCierre(usuarioCierre);
    incidente1.setFecha(LocalDateTime.now());
    incidente1.setServicio(servicio);
    return incidente1;
  }
*/
}

package test;

import domain.models.entities.services.georef.ServicioGeoref;
import domain.models.entities.services.georef.entities.ListadoDeMunicipios;
import domain.models.entities.services.georef.entities.ListadoDeProvincias;
import domain.models.entities.services.georef.entities.Provincia;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class GeorefTest {

  protected ServicioGeoref georefService;
  private void initializeServicioGeoref(){
    this.georefService = Common.getServicioGeoref();
  }

  @BeforeEach
  public void initialize() {
    this.initializeServicioGeoref();
  }

  @AfterEach
  public void clean(){

  }

  @Test
  void sePuedenListarProvincias() throws IOException {
    ListadoDeProvincias nuevoListado = georefService.listadoDeProvincias();
    Assertions.assertTrue(nuevoListado.cantidad > 0);
    Assertions.assertEquals(24, nuevoListado.provincias.size());
  }

  @Test
  void sePuedenListarLosMunicipiosDeBuenosAires() throws IOException {
    Provincia buenosAires = Common.getBuenosAires();
    ListadoDeMunicipios nuevoListado = georefService.listadoDeMunicipios(buenosAires.getId());
    Assertions.assertTrue(nuevoListado.cantidad > 0);
    Assertions.assertTrue(nuevoListado.municipios.stream().anyMatch(municipio -> municipio.getNombre().equals("Lomas de Zamora")));
  }

  @Test
  void sePuedenListarLosMunicipiosDeBuenosAiresConLosCampos() throws IOException {
    Provincia buenosAires = Common.getBuenosAires();
    ListadoDeMunicipios nuevoListado = georefService.listadoDeMunicipios(buenosAires.getId());
    Assertions.assertTrue(nuevoListado.cantidad > 0);
    Assertions.assertTrue(nuevoListado.municipios.stream().anyMatch(municipio -> municipio.getNombre().equals("Lomas de Zamora")));
  }


  @Test
  void sePuedenListarTodosLosMunicipiosDeArgentina() throws IOException {
    ListadoDeMunicipios nuevoListado = georefService.listadoDeTodosLosMunicipios();
    Assertions.assertTrue(nuevoListado.cantidad > 1800);
    Assertions.assertTrue(nuevoListado.municipios.stream().anyMatch(municipio -> municipio.getNombre().equals("Yavi")));
    Assertions.assertTrue(nuevoListado.municipios.stream().anyMatch(municipio -> municipio.getNombre().equals("Lomas de Zamora")));
  }

  @Test
  void sePuedenObtenerUnaProvincia() throws IOException {
    int id = 06; //ID DE BUENOS AIRES
    ListadoDeProvincias nuevoListado = georefService.listadoDeUnaProvincia(id);
    Assertions.assertEquals(1, nuevoListado.cantidad);
    Assertions.assertTrue(nuevoListado.provincias.stream().anyMatch(provincia -> provincia.getNombre().equals("Buenos Aires")));
  }

  @Test
  void sePuedenObtenerUnMunicipio() throws IOException {
    String id = "060490"; //ID DE LOMAS DE ZAMORA
    ListadoDeMunicipios nuevoListado = georefService.listadoDeUnMunicipio(id);
    Assertions.assertEquals(1, nuevoListado.cantidad);
    Assertions.assertTrue(nuevoListado.municipios.stream().anyMatch(municipio -> municipio.getNombre().equals("Lomas de Zamora")));
  }

}

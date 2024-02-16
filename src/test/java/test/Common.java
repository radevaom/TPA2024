package test;

import domain.models.entities.comunicacion.AlertaCuandoSucede;
import domain.models.entities.comunicacion.ComunicacionMail;
import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.miembro.MiembroAdministrador;
import domain.models.entities.miembro.MiembroNormal;
import domain.models.entities.services.confidenceLevel.ConfidenceLevelService;
import domain.models.entities.services.georef.ServicioGeoref;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.services.georef.entities.Provincia;
import domain.models.entities.services.mergeCommunity.MergeCommunityService;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.servicio.ServicioSimple;
import domain.models.entities.serviciospublicos.entidad.Entidad;
import domain.models.entities.serviciospublicos.entidad.TipoTransporte;
import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import domain.models.entities.serviciospublicos.establecimiento.Sucursal;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import domain.models.entities.usuario.OrganismoDeControl;
import domain.models.entities.servicio.Agrupacion;
import domain.models.entities.usuario.Persona;
import domain.models.entities.usuario.Usuario;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Common {

    public static Agrupacion getAgrupacion() {
        return new Agrupacion("Servicio.Agrupacion test", getListaDeServicioS());
    }

    public static ServicioSimple getServicio1() {
        return new ServicioSimple("nombreServicio1", true, "unTipoDeServicio1");
    }

    public static ServicioSimple getServicio2() {
        return new ServicioSimple("nombreServicio2", true, "unTipoDeServicio2");
    }

    public static ServicioSimple getServicio3() {
        return new ServicioSimple("nombreServicio3",true, "unTipoDeServicio3");
    }

    public static List<Servicio> getListaDeServicioS() {
        List<Servicio> servicios = new ArrayList<>();

        servicios.add(getServicio1());
        servicios.add(getServicio2());
        servicios.add(getServicio3());

        return servicios;
    }

    public static Comunidad getComunidad() {
        return new Comunidad("nombreComunidad");
    }

    public static Comunidad getComunidad2() {
        return new Comunidad("nombreComunidad2");
    }

    public static MiembroAdministrador getMiembroAdministrador(Persona persona) {
        return new MiembroAdministrador(new Comunidad("nombreComunidadMiembroAdmin"), persona);
    }

    public static Miembro getMiembroNormal() {
        Comunidad comunidad = new Comunidad("nombreComunidadMiembroNormal");
        Persona persona = getPersona();
        return new MiembroNormal(comunidad, persona);
    }

    public static Miembro getMiembroConMail(Comunidad comunidad) {
        Persona persona = getPersonaConMail();
        return new MiembroNormal(comunidad, persona);
    }

    public static Provincia Caba(){
        return new Provincia(new Centroide(-34.6144420654301, -58.4458763250916));
    }

    public static Estacion estacionRosas() {
        return new Estacion("Rosas", Caba(), List.of(getServicio1()));
    }

    public static Estacion estacionAlem() {
        return new Estacion("Alem", Caba(), List.of(getServicio1()));
    }

    public static Estacion estacionMedrano() {
        return new Estacion("Medrano", Caba(), List.of(getServicio1()));
    }

    public static List<Estacion> estaciones() {
        return List.of(estacionAlem(),estacionRosas());
    }

    public static TransportePublico getSubteB(){
        return new TransportePublico("Subte B",estacionRosas(),estacionAlem(),estaciones(), TipoTransporte.SUBTERRANEO,Caba());
    }

    public static TransportePublico getTPSUBTERRANEO(){
        return new TransportePublico("Subte B de Rosas a Alem", estacionRosas(), estacionAlem(), estaciones(), TipoTransporte.SUBTERRANEO, Caba());
    }

    public static TransportePublico getTPFERROCARRIL(){
        return new TransportePublico("Tren Sarmiento", estacionRosas(), estacionAlem(), estaciones(), TipoTransporte.FERROCARRIL, Caba());
    }

    public static Sucursal getBancoNacion(){
        return new Sucursal("Banco Nación",Caba());
    }

    public static ServicioGeoref getServicioGeoref(){
        return ServicioGeoref.getInstance();
    }
    public static ConfidenceLevelService getConfidenceLevelService(){
        return ConfidenceLevelService.getInstance();
    }
    public static MergeCommunityService getMergeCommunityService(){
        return MergeCommunityService.getInstance();
    }

    public static Provincia getBuenosAires() throws IOException {
        List<Provincia> todasLasProvincias = getServicioGeoref().listadoDeProvincias().provincias;
        Optional<Provincia> provinciaOptional = todasLasProvincias.stream()
            .filter(provincia -> provincia.getNombre().equals("Buenos Aires"))
            .findFirst();


        // Obtener el índice del objeto si se encuentra
        int indexBuenosAires = provinciaOptional.map(todasLasProvincias::indexOf).orElse(-1);

        /*
        if (provinciaOptional.isPresent()) {
            Provincia provincia = provinciaOptional.get();
            return provincia;

        } else {
            return null;
        }*/

        return todasLasProvincias.get(indexBuenosAires);
    }

    public static OrganismoDeControl getOrganismoDeControl() {
        return new OrganismoDeControl(new Persona(), "NombreOrg");
    }

    public static EntidadPrestadoraServicioPublico getEntidadPrestadoraServicioPublico() {
        return new EntidadPrestadoraServicioPublico("unNombre", "unMail");
    }

    public static EntidadPrestadoraServicioPublico getEntidadPrestadoraServicioPublico(String nombre) {
        return new EntidadPrestadoraServicioPublico(nombre, "unMail");
    }

    public static AlertaCuandoSucede getAlertaCuandoSucede(){
        return new AlertaCuandoSucede();
    }

    public static Persona getPersona() {
        return new Persona("EmailPersona", "NombrePersona", "ApellidoPersona",getAlertaCuandoSucede());
    }

    public static Persona getPersona2() {
        return new Persona("EmailPersona2", "NombrePersona2", "ApellidoPersona2",getAlertaCuandoSucede());
    }

    public static Usuario getUsuario() {
        Usuario usuario = new Usuario("usuario1", "abc1", getPersona());
        usuario.setId(123L);
        return usuario;
    }

    public static Usuario getUsuario2() {
        Usuario usuario = new Usuario("usuario2", "abc2", getPersona2());
        usuario.setId(456L);
        return usuario;
    }

    public static Persona getPersonaConMail() {
        Persona persona = new Persona("dds2023tpa@gmail.com", "NombrePersona", "ApellidoPersona",getAlertaCuandoSucede());
        persona.setTipoAlerta(new AlertaCuandoSucede());
        persona.setMedioComunicacionPreferido(new ComunicacionMail());
        return persona;
    }

    public static Entidad getTransportePublico() {
        return new TransportePublico("nombreTransporte",  estacionRosas(), estacionAlem(), estaciones(), TipoTransporte.SUBTERRANEO, Caba());
    }

    public static Centroide getCentroide1(){
        return new Centroide(-34.614490277967526, -58.44449641288464);
    }

    public static Centroide getCentroide2(){
        return new Centroide(-34.61077963617253, -58.41578532394953);
    }

    public static Centroide getCentroide3(){
        return new Centroide(-34.61646605718858, -58.43224335742777);
    }

    public static Centroide getCentroide4(){
        return new Centroide(-14.61646605718858, -18.43224335742777);
    }

    public static Incidente getIncidente() {
        return new Incidente(Common.getServicio1(), "No funciona este servicio",getCentroide1());
    }

    public static Incidente getIncidente2() {
        return new Incidente(Common.getServicio1(), "No funciona este servicio",getCentroide2());
    }

    public static Incidente getIncidente3() {
        return new Incidente(Common.getServicio1(), "No funciona este servicio",getCentroide4());
    }

    public static Incidente getIncidenteEnEstacionAlem() {
        Estacion estacion = estacionAlem();
        Incidente incidente = getIncidente();
        incidente.setServicio(estacion.getServicios().get(0));
        return incidente;
    }
}

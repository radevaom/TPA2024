package domain.models.entities.comunidad;

import domain.models.entities.comunicacion.Notificacion;
import domain.models.entities.converters.LocalDateTimeConverter;
import domain.models.entities.helpers.DistanceCalculator;
import domain.models.entities.miembro.Miembro;
import domain.models.entities.miembro.MiembroNormal;
import domain.models.entities.services.georef.entities.Centroide;
import domain.models.entities.incidente.Incidente;
import domain.models.entities.servicio.Servicio;
import domain.models.entities.servicio.Agrupacion;
import domain.models.entities.servicio.ServicioSimple;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SuppressWarnings("checkstyle:Indentation")
@Getter
@Setter
@Entity
@Table(name = "Comunidad")
public class Comunidad {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String nombre;
    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.ALL)
    private List<Incidente> incidentesReportados;
    @OneToMany(mappedBy = "comunidad", cascade = CascadeType.ALL)
    private List<Miembro> miembros;
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime fechaUltimaFusion;
    @Column
    private Double gradoConfianza;
    public Comunidad(String nombre) {
        this.nombre = nombre;
        this.miembros = new ArrayList<>();
        this.incidentesReportados = new ArrayList<>();
        //test
    }
    public Comunidad() {}

    public Servicio crearServicio(String nombre, Boolean estadoDeServicio, String tipoDeServicio){
        return new ServicioSimple(nombre, estadoDeServicio, tipoDeServicio);
    }
    public Agrupacion crearAgrupacion(String nombre, List<Servicio> servicios){
        return new Agrupacion(nombre, servicios);
    }

    public void reportarUbicacionDeMiembro(MiembroNormal miembroTrasladado){
        List<Incidente> incidentesCercanos =
            encontrarIncidentesCercanos(miembroTrasladado.obtenerUbicacion());

        incidentesCercanos.forEach(incidente -> {
            Notificacion notificacion = new Notificacion("Te acercaste a un incidente, ayudanos a mantenerlo actualizado",incidente.mensaje());
            miembroTrasladado.getPersona().recibirNotificacion(notificacion);
        });
    }

    public List<Incidente> encontrarIncidentesCercanos(Centroide ubicacion){
        return this.incidentesReportados.stream().filter(incidenteReportado -> {
            Double distance = DistanceCalculator.distance(incidenteReportado.getUbicacion().getLat(),
                incidenteReportado.getUbicacion().getLon(),
                ubicacion.getLat(),
                ubicacion.getLon());
            return distance <= 200;
        }).toList();
    }


    public void agregarIncidente(Incidente incidente){
        this.incidentesReportados.add(incidente);
        incidente.setComunidad(this);
        this.notificarIncidenteAgregado(incidente);
    }

    public void notificarIncidenteAgregado(Incidente incidente){
        Notificacion notificacion = new Notificacion("Nuevo Incidente", incidente.mensaje());

        //TODO HACE FALTA PASAR POR EL MIEBRO O PODEMOS NOTIFICAR DIRECTO A LAS PERSONAS?
        //this.miembros.forEach(miembro -> miembro.recibirNotificacion(notificacion));
        this.miembros.forEach(miembro -> miembro.getPersona().recibirNotificacion(notificacion));
    }


    public void cerrarIncidente(Incidente incidente){
        incidentesReportados.remove(incidente);
        this.notificarIncidenteCerrado(incidente);
    }
    public void notificarIncidenteCerrado(Incidente incidente){
        Notificacion notificacion = new Notificacion("Incidente Cerrado", incidente.mensaje());
        this.miembros.forEach(miembro -> miembro.getPersona().recibirNotificacion(notificacion));
    }

    public void agregarMiembro(Miembro miembro) {this.miembros.add(miembro);}

    public void setIncidentesReportados(List<Incidente> incidentes) {
        this.incidentesReportados = incidentes;
        incidentes.forEach(incidente -> incidente.setComunidad(this));
    }
}

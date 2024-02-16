package domain.models.entities.comunicacion;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@Entity
@DiscriminatorValue(value = "sin_apuro")
public class AlertaSinApuro extends TipoAlerta {

    @Transient
    private List<Notificacion> notificaciones;
    @ElementCollection(fetch = FetchType.LAZY)
    @Column(name = "horario", columnDefinition = "DateTime")
    @JoinTable(name = "horarios_alerta", joinColumns = @JoinColumn(name = "alerta_id", referencedColumnName = "id"))
    private List<LocalTime> horarios;
    @Transient
    private MedioComunicacion medioComunicacion;
    @Transient
    private String mailDestinatario;

    public AlertaSinApuro(){};

    public AlertaSinApuro(List<LocalTime> horarios) {
        this.horarios = horarios;
        this.notificaciones = new ArrayList<>();
        horarios.forEach(this::ejecutarDiariamente);
    }

    @Override
        public void alertar(Notificacion notificacion, MedioComunicacion medioComunicacion, String mailDestinatario) {
        this.notificaciones.add(notificacion);
        this.mailDestinatario = mailDestinatario;
        this.medioComunicacion = medioComunicacion;
    }


    public void agregarHorarioDeEnvio(LocalTime horario){
        horarios.add(horario);
        this.ejecutarDiariamente(horario);
    }



    ////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////TAREA PROGRAMABLE (VA EN OTRA CLASE?)
    ////////////////////////////////////////////////////////////////////

    @Transient
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void enviarNotificaciones() {
        this.notificaciones.forEach(this::notificar);
        notificaciones.clear();
    }

    public void notificar(Notificacion notificacion) {
        this.medioComunicacion.comunicar(notificacion,this.mailDestinatario);
    }

    public void ejecutarDiariamente(LocalTime horario) {
        // Obtiene el horario actual
        LocalTime ahora = LocalTime.now();

        // Calcula el tiempo hasta la próxima ejecución a las horario
        LocalTime horarioEjecucion = LocalTime.of(horario.getHour(), horario.getMinute());
        Duration tiempoHastaProximaEjecucion = Duration.between(ahora, horarioEjecucion);

        if (tiempoHastaProximaEjecucion.isNegative() || tiempoHastaProximaEjecucion.isZero()) {
            // Si ya es después de las horario hoy, programa para mañana a las horario
            tiempoHastaProximaEjecucion = tiempoHastaProximaEjecucion.plusDays(1);
        }

        // Programa la ejecución del método en el horario configurado
        scheduler.scheduleAtFixedRate(this::enviarNotificaciones, tiempoHastaProximaEjecucion.toMinutes(), 24 * 60, TimeUnit.MINUTES);
    }


}

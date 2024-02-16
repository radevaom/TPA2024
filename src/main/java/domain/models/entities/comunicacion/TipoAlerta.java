package domain.models.entities.comunicacion;

import domain.models.entities.usuario.Persona;
import javax.persistence.*;

@Table(name = "Alerta")
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_alerta")
public abstract class TipoAlerta {

    @Id
    @GeneratedValue
    private Long id;

    public abstract void alertar(Notificacion notificacion, MedioComunicacion medioComunicacion, String mailDestinatario);
}

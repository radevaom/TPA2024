package domain.models.entities.services.georef.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@DiscriminatorValue("Municipio")
public class Municipio extends Localizacion {

    @OneToOne
    public Provincia provincia;
    @OneToOne
    public Centroide centroide;

    public Municipio(String nombre) {
        this.setNombre(nombre);
    }
    public Municipio(){}

    @Override
    public Centroide getUbicacion() {
        return centroide;
    }
}

package domain.models.entities.services.georef.entities;

import javax.persistence.GeneratedValue;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter

@Entity
@Table(name = "Centroide")
public class Centroide {

  @Id
  @GeneratedValue
  private Long id;
  @Column
  private double lat;
  @Column
  private double lon;


  public Centroide(double lat, double lng){
    this.lat = lat;
    this.lon = lng;
  }

  public Centroide() {
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
}

package domain.models.entities.comunicacion;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Notificacion {
  private String asunto;
  private String mensaje;
  private String imagen = null;

  public Notificacion (String asunto, String cuerpo){
    this.asunto = asunto == null ? "" : asunto;
    this.mensaje = cuerpo == null ? "" : cuerpo;
  }

  public Notificacion (String asunto, String cuerpo, String imagen){
    this.asunto = asunto == null ? "" : asunto;
    this.mensaje = cuerpo == null ? "" : cuerpo;
    this.imagen = imagen == null ? "" : imagen;
  }



}

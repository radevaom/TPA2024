package domain.Repositorios;

import domain.models.entities.comunidad.Comunidad;

import domain.models.entities.incidente.Incidente;
import java.util.ArrayList;
import java.util.Collection;

public class RepoComunidad extends Repositorio<Comunidad> {

  public RepoComunidad() {
    super(new DBHibernate<Comunidad>(Comunidad.class));
  }

  public void add(Comunidad comunidad) {
    this.dbService.agregar(comunidad);
  }

//  public void remove(Comunidad comunidad) {
//    this.comunidades = this.comunidades.stream().filter(x-> !x.getId().equals(comunidad.getId())).toList();
//  }
//
//  public boolean exists(Long id){
//      return this.comunidades.stream().anyMatch(x-> x.getId().equals(id));
//  }
//
//  public Comunidad findByid(Long id){
//    return this.comunidades.stream().filter(x-> x.getId().equals(id)).findFirst().get();
//  }
//
//  public Collection<Comunidad> all(){
//    return this.comunidades;
//  }
}

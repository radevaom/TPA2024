package domain.Repositorios;

import domain.models.entities.comunidad.Comunidad;
import domain.models.entities.miembro.Miembro;

import java.util.ArrayList;
import java.util.Collection;

public class RepoMiembro {

  private static long secuencia = 0;

  private long nextId(){
    secuencia = secuencia +1;
    return secuencia;
  }

  private Collection<Miembro> miembros;

  public RepoMiembro() {
    this.miembros = new ArrayList<>();
  }

  public void add(Miembro miembro){
    miembro.setId(nextId());
    this.miembros.add(miembro);
  }

  public void remove(Miembro miembro){
    this.miembros = this.miembros.stream().filter(x-> !x.getId().equals(miembro.getId())).toList();
  }

  public boolean exists(Long id){
    return this.miembros.stream().anyMatch(x-> x.getId().equals(id));
  }

  public Miembro findByid(Long id){
    return this.miembros.stream().filter(x-> x.getId().equals(id)).findFirst().get();
  }

  public Collection<Miembro> all(){
    return this.miembros;
  }
}

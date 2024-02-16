package domain.Repositorios;

import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import java.util.ArrayList;
import java.util.Collection;

public class RepoEntidadesPrestadoras {

  private static long secuencia = 0;

  private long nextId(){
    secuencia = secuencia +1;
    return secuencia;
  }

  private Collection<EntidadPrestadoraServicioPublico> entidadPrestadoraServicioPublicos;

  public RepoEntidadesPrestadoras() {
    this.entidadPrestadoraServicioPublicos = new ArrayList<>();
  }

  public void add(EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublicos){
    entidadPrestadoraServicioPublicos.setId(nextId());
    this.entidadPrestadoraServicioPublicos.add(entidadPrestadoraServicioPublicos);
  }

  public void remove(EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublicos){
    this.entidadPrestadoraServicioPublicos = this.entidadPrestadoraServicioPublicos.stream().filter(x-> !x.getId().equals(entidadPrestadoraServicioPublicos.getId())).toList();
  }

  public boolean exists(Long id){
    return this.entidadPrestadoraServicioPublicos.stream().anyMatch(x-> x.getId().equals(id));
  }

  public EntidadPrestadoraServicioPublico findByid(Long id){
    return this.entidadPrestadoraServicioPublicos.stream().filter(x-> x.getId().equals(id)).findFirst().get();
  }

  public Collection<EntidadPrestadoraServicioPublico> all(){
    return this.entidadPrestadoraServicioPublicos;
  }
}

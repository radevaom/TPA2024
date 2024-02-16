package domain.Repositorios;

import domain.models.entities.miembro.Miembro;
import domain.models.entities.usuario.Usuario;

import java.util.ArrayList;
import java.util.Collection;

public class RepoUsuario {

  private static long secuencia = 0;

  private long nextId(){
    secuencia = secuencia +1;
    return secuencia;
  }

  private Collection<Usuario> usuarios;

  public RepoUsuario() {
    this.usuarios = new ArrayList<>();
  }

  public void add(Usuario usuario){
    usuario.setId(nextId());
    this.usuarios.add(usuario);
  }

  public void remove(Miembro miembro){
    this.usuarios = this.usuarios.stream().filter(x-> !x.getId().equals(miembro.getId())).toList();
  }

  public boolean exists(Long id){
    return this.usuarios.stream().anyMatch(x-> x.getId().equals(id));
  }

  public Usuario findByid(Long id){
    return this.usuarios.stream().filter(x-> x.getId().equals(id)).findFirst().get();
  }

  public Collection<Usuario> all(){
    return this.usuarios;
  }
}

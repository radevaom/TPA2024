package domain.Repositorios;


import domain.models.entities.incidente.Incidente;
import java.util.ArrayList;
import java.util.Collection;

public class RepoIncidente extends Repositorio<Incidente> {

  public RepoIncidente() {
    super(new DBHibernate<Incidente>(Incidente.class));
  }

  public void add(Incidente incidente) {
    this.dbService.agregar(incidente);
  }

//  public boolean buscar(Long id) {
//    this.dbService.buscar(incidente);
//  }
//
//  public Incidente findByid(Long id){
//    return this.incidentes.stream().filter(x-> x.getId().equals(id)).findFirst().get();
//  }
//
//  public Collection<Incidente> all(){
//    return this.incidentes;
//  }
}

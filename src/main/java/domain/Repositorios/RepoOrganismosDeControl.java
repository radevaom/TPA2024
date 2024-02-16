package domain.Repositorios;


import domain.models.entities.usuario.OrganismoDeControl;
import java.util.ArrayList;
import java.util.Collection;

public class RepoOrganismosDeControl {

  private static long secuencia = 0;

  private long nextId(){
    secuencia = secuencia +1;
    return secuencia;
  }

  private Collection<OrganismoDeControl> organismoDeControls;

  public RepoOrganismosDeControl() {
    this.organismoDeControls = new ArrayList<>();
  }

  public void add(OrganismoDeControl organismoDeControls){
    organismoDeControls.setId(nextId());
    this.organismoDeControls.add(organismoDeControls);
  }

  public void remove(OrganismoDeControl organismoDeControls){
    this.organismoDeControls = this.organismoDeControls.stream().filter(x-> !x.getId().equals(organismoDeControls.getId())).toList();
  }

  public boolean exists(Long id){
    return this.organismoDeControls.stream().anyMatch(x-> x.getId().equals(id));
  }

  public OrganismoDeControl findByid(Long id){
    return this.organismoDeControls.stream().filter(x-> x.getId().equals(id)).findFirst().get();
  }

  public Collection<OrganismoDeControl> all(){
    return this.organismoDeControls;
  }

}

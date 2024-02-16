package domain.Repositorios;

import domain.models.entities.incidente.Incidente;
import domain.models.entities.ranking.Ranking;

import java.util.ArrayList;
import java.util.Collection;

public class RepoRanking {

  private static long secuencia = 0;

  private long nextId(){
    secuencia = secuencia +1;
    return secuencia;
  }

  private Collection<Ranking> rankings;

  public RepoRanking() {
    this.rankings = new ArrayList<>();
  }

  public void add(Ranking rankings){
    rankings.setId(nextId());
    this.rankings.add(rankings);
  }

  public void remove(Incidente incidente){
    this.rankings = this.rankings.stream().filter(x-> !x.getId().equals(incidente.getId())).toList();
  }

  public boolean exists(Long id){
    return this.rankings.stream().anyMatch(x-> x.getId().equals(id));
  }

  public Ranking findByid(Long id){
    return this.rankings.stream().filter(x-> x.getId().equals(id)).findFirst().get();
  }

  public Collection<Ranking> all(){
    return this.rankings;
  }
}

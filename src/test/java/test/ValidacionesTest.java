package test;

import domain.models.entities.validador.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ValidacionesTest {

  @AfterEach
  public void clean(){

  }

  @Test
  void NoSePUedenContraseniasCortas(){
    Assertions.assertFalse(Validador.validar8Caracteres("Password"));
    Assertions.assertTrue(Validador.validar8Caracteres("Passwordd"));
  }

  @Test
  void NoSePuedenUsuariosPorDefecto(){
    Assertions.assertFalse(Validador.validarUsuarioPorDefecto("ADMIN","ADMIN"));
  }

  @Test
  void NoSePuedenCaracteresRepetidos(){
    Assertions.assertFalse(Validador.validarCaracteresRepetidos("PaaaP"));
  }

  @Test
  void NoSePuedenCaracteresConsecutivos(){
    Assertions.assertFalse(Validador.validarCaracteresConsecutivos("ABCDE"));
  }

  @Test
   void NoSePuedenPasswordsDentroDeLas10000MasComunes(){
    Assertions.assertFalse(Validador.validarContraseniasComunes("airplane"));
  }

  @Test
  void PermitePasswordNoComun(){
    Assertions.assertTrue(Validador.validarContraseniasComunes("holaPablitooo"));
  }
}

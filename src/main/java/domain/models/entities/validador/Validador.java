package domain.models.entities.validador;


import domain.models.entities.exceptions.ContrasenaDebilException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validador {
  public static List<String> clavesComunes() {
    try {
      return Files.readAllLines(
          Paths.get("src/main/resources/public/files/common-passwords.txt"),
          StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new ContrasenaDebilException("Error al leer archivo");
    }
  }
  public static Boolean validar8Caracteres(String password) {
    return password.length() > 8;
  }
  public static Boolean validarCaracteresConsecutivos(String password){
    Optional<String> error = Optional.empty();
    char[] contrasenaArray = password.toCharArray();
    for (int i = 0; i < contrasenaArray.length - 3; i++) {
      if ((contrasenaArray[i] == contrasenaArray[i + 1] - 1
          && contrasenaArray[i] == contrasenaArray[i + 2] - 2
          && contrasenaArray[i] == contrasenaArray[i + 3] - 3)
          || (contrasenaArray[i] == contrasenaArray[i + 1] + 1
          && contrasenaArray[i] == contrasenaArray[i + 2] + 2
          && contrasenaArray[i] == contrasenaArray[i + 3] + 3)) {
        return false;
      }
    }
    return true;
  }
  public static Boolean validarCaracteresRepetidos(String password){
    Pattern patronRepetitive = Pattern.compile("(.)\\1{2}");
    Matcher matcherRepetitive = patronRepetitive.matcher(password);
    return !matcherRepetitive.find();
  }
  public static Boolean validarContraseniasComunes(String password){
    return !clavesComunes().contains(password);
  }
  public static Boolean validarUsuarioPorDefecto(String usuario, String password) {
    return !usuario.equals(password);
  }
}

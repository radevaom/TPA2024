package domain.models.Controllers;


import domain.models.entities.services.servicioCSV.CsvLoader;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import io.javalin.http.Context;
import io.javalin.http.HandlerType;
import jakarta.servlet.MultipartConfigElement;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javax.persistence.EntityManagerFactory;
import org.jetbrains.annotations.NotNull;


import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

public class CargaMasivaController extends HandlerTP {
  private EntityManagerFactory entityManagerFactory;
  public CargaMasivaController(EntityManagerFactory entityManagerFactory) {
    super(entityManagerFactory);
    this.entityManagerFactory = entityManagerFactory;
  }

  @Override
  public void handle(@NotNull Context ctx) throws Exception {
    super.handle(ctx);
    if(ctx.method() == HandlerType.GET)
      handleGetRequest(ctx);
    else
      handlePostRequest(ctx);
  }


  private void cargarDatosDesdeCSV(String rutaArchivo) {
    // Llamar al método existente para cargar datos desde el CSV y persistir en la base de datos
    CsvLoader csvLoader = new CsvLoader();
    List<EntidadPrestadoraServicioPublico> entidadPrestadoraServicioPublicos = csvLoader.cargarCSVEntidadPrestadoraFebrero(rutaArchivo);


    EntityManager entity = entityManagerFactory.createEntityManager();
    entity.getTransaction().begin();
    entidadPrestadoraServicioPublicos.forEach(entidad -> entity.persist(entidad));
    entity.getTransaction().commit();
    entity.close();
  }

  public void handleGetRequest(Context context) {
    Map<String, Object> model = inicializarModelo(context);

    String mensaje = context.queryParam("mensaje");
    if (mensaje != null && !mensaje.isEmpty()) {
      model.put("mensaje", mensaje);
    }

    context.render("cargaMasiva.hbs", model);
  }

  public void handlePostRequest(Context ctx) {

    File uploadDir = new File("upload");
    uploadDir.mkdir(); // create the upload directory if it doesn't exist
    Path tempFile = Path.of("/upload");
    try {
      tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ctx.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

    try (InputStream input = ctx.uploadedFile("csvFile").content()) {// getPart needs to use same "name" as input field in form
      Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
      cargarDatosDesdeCSV(tempFile.toAbsolutePath().toString());


      Map<String, Object> model = inicializarModelo(ctx);
      model.put("mensaje", "Archivo cargado correctamente");

      //ctx.redirect("/cargaMasiva?mensaje=Archivo+cargado+correctamente");

      ctx.render("cargaMasiva.hbs", model);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}


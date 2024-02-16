package domain.models.entities.services.servicioCSV;

import domain.models.entities.services.georef.entities.Localizacion;
import domain.models.entities.services.georef.entities.Municipio;
import domain.models.entities.services.georef.entities.Provincia;
import domain.models.entities.serviciospublicos.entidad.Entidad;
import domain.models.entities.serviciospublicos.entidad.TipoTransporte;
import domain.models.entities.serviciospublicos.entidad.TransportePublico;
import domain.models.entities.serviciospublicos.establecimiento.Estacion;
import domain.models.entities.serviciospublicos.establecimiento.Sucursal;
import domain.models.entities.usuario.EntidadPrestadoraServicioPublico;
import domain.models.entities.usuario.OrganismoDeControl;
import domain.models.entities.usuario.Persona;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class CsvLoader {

    public void cargarCSVEntdadPrestadora(String rutaArchivo, EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublico) {

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            // Leer primer linea de encabezado del archivo CSV
            String[] encabezado = br.readLine().split(";");

            // Leer segunda linea de encabezado del archivo CSV
            String[] encabezado2 = br.readLine().split(";");


            Entidad entidad = null;
            Localizacion localizacion = null;

            // Leer todos los datos del archivo CSV dividios por ;
            while ((linea = br.readLine()) != null) {

                // Leer datos de cada línea del archivo CSV
                String[] datos = linea.split(";");


                String tipoDeEntidad = datos[0];
                String nombreEntidad = datos[1];
                String tipoDeLocalizacion = datos[2];
                String nombreLocalizacion = datos[3];
                String tipoTransportePublico;

                //Crear la localizacion (Municipio o Provincia)
                if (tipoDeLocalizacion.equals("PROVINCIA")) {
                    localizacion = new Provincia(nombreLocalizacion);
                }
                if (tipoDeLocalizacion.equals("MUNICIPIO")) {
                    localizacion = new Municipio(nombreLocalizacion);
                }


                if (tipoDeEntidad.equals("SERVICIOTRANSPORTE")) {
                    TipoTransporte tipoTransporte = null;
                    tipoTransportePublico = datos[4];

                    //Crear tipo Transporte(SUBTERRANEO O FERROCARIL)
                    if (tipoTransportePublico.equals("SUBTERRANEO")) {
                        tipoTransporte = TipoTransporte.SUBTERRANEO;
                    }
                    if (tipoTransportePublico.equals("FERROCARRIL")) {
                        tipoTransporte = TipoTransporte.FERROCARRIL;
                    }

                    entidad = new TransportePublico(
                            nombreEntidad, null, null, List.of(), tipoTransporte, localizacion);
                }


                if (tipoDeEntidad.equals("ESTACION")) {
                    entidad = new Estacion(nombreEntidad, localizacion, new ArrayList<>());
                }

                if (tipoDeEntidad.equals("SUCURSAL")) {
                    entidad = new Sucursal(nombreEntidad, localizacion);
                }

                entidadPrestadoraServicioPublico.agregarServicioOfrecido(entidad);

            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo CSV: " + e.getMessage());
        }
    }

    //TODO
    public void cargarCSVOrganismoDeControl(String rutaArchivo, OrganismoDeControl organismoDeControl) {

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            //TODO

        } catch (IOException e) {
            System.err.println("Error al leer archivo CSV: " + e.getMessage());
        }
    }


    public void cargarCSVOrganismoDeControl2(String rutaArchivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            // Leer primer linea de encabezado del archivo CSV
            String[] encabezado = br.readLine().split(";");

            // Leer todos los datos del archivo CSV dividios por ;
            while ((linea = br.readLine()) != null) {

                // Leer datos de cada línea del archivo CSV
                String[] datos = linea.split(";");


                String nombre = datos[0];
                String email = datos[1];

                //Crear la localizacion (Municipio o Provincia)
                Persona personaACargo = new Persona();
                personaACargo.setEmail(email);
                OrganismoDeControl organismoDeControl = new OrganismoDeControl(personaACargo, nombre);

                System.out.println("El nombre del nuevo OrganismoDeControl es: " + organismoDeControl.getNombre());
                System.out.println("El mail del nuevo OrganismoDeControl es: " + organismoDeControl.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo CSV: " + e.getMessage());
        }
    }


    public void cargarCSVEntidadPrestadora(String rutaArchivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            // Leer primer linea de encabezado del archivo CSV
            String[] encabezado = br.readLine().split(";");

            // Leer todos los datos del archivo CSV dividios por ;
            while ((linea = br.readLine()) != null) {

                // Leer datos de cada línea del archivo CSV
                String[] datos = linea.split(";");


                String nombre = datos[0];
                String email = datos[1];

                //Crear la localizacion (Municipio o Provincia)
                EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublico = new EntidadPrestadoraServicioPublico(nombre, email);

                System.out.println("El nombre de la nueva EntidadPrestadora es: " + entidadPrestadoraServicioPublico.getNombre());
                System.out.println("El mail de la nueva EntidadPrestadora es: " + entidadPrestadoraServicioPublico.getEmail());
            }
        } catch (IOException e) {
            System.err.println("Error al leer archivo CSV: " + e.getMessage());
        }
    }

    public List<EntidadPrestadoraServicioPublico> cargarCSVEntidadPrestadoraFebrero(String rutaArchivo) {

        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;

            // Leer primer linea de encabezado del archivo CSV
            String[] encabezado = br.readLine().split(";");



            List<EntidadPrestadoraServicioPublico> entidadPrestadoraServicioPublicos = new ArrayList<>();


            // Leer todos los datos del archivo CSV dividios por ;
            while ((linea = br.readLine()) != null) {

                // Leer datos de cada línea del archivo CSV
                String[] datos = linea.split(";");


                String nombre = datos[0];
                String email = datos[1];

                //Crear la localizacion (Municipio o Provincia)
                EntidadPrestadoraServicioPublico entidadPrestadoraServicioPublico = new EntidadPrestadoraServicioPublico(nombre, email);

                //System.out.println("El nombre de la nueva EntidadPrestadora es: " + entidadPrestadoraServicioPublico.getNombre());
                //System.out.println("El mail de la nueva EntidadPrestadora es: " + entidadPrestadoraServicioPublico.getEmail());
                entidadPrestadoraServicioPublicos.add(entidadPrestadoraServicioPublico);
            }
            return entidadPrestadoraServicioPublicos;
        } catch (IOException e) {
            System.err.println("Error al leer archivo CSV: " + e.getMessage());
            return null;
        }
    }


}


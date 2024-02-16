package domain;

import domain.models.entities.services.georef.ServicioGeoref;
import domain.models.entities.services.georef.entities.ListadoDeMunicipios;
import domain.models.entities.services.georef.entities.ListadoDeProvincias;
import java.io.IOException;
import java.util.Scanner;

public class Ejemplo {

    public static void main(String[] args) throws IOException {

        ServicioGeoref servicioGeoref = ServicioGeoref.getInstance();
        System.out.println("Seleccione una de las siguientes provincias ingresando su id");

        ListadoDeProvincias listadoDeProvincias = servicioGeoref.listadoDeProvincias();

        listadoDeProvincias.provincias.sort((p1, p2) -> p1.getId() >= p2.getId()? 1 : -1);
        listadoDeProvincias.provincias.forEach(p1 -> System.out.println(p1.getId() + ". " + p1.getNombre()));

        Scanner entradaScanner = new Scanner(System.in);
        Long idProvinciaElegida = Long.parseLong(entradaScanner.nextLine());

        ListadoDeMunicipios listadoDeMunicipios =  servicioGeoref.listadoDeMunicipios(idProvinciaElegida);

        listadoDeMunicipios.municipios.sort((m1, m2) -> m1.getId() >= m2.getId()? 1 : -1);
        listadoDeMunicipios.municipios.forEach(m1 -> System.out.println(m1.getNombre()));
    }
}

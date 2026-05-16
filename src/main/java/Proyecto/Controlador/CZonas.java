package Proyecto.Controlador;

import Proyecto.DAO.DZonas;
import Proyecto.Modelo.Zonas;

import java.util.ArrayList;

public final class CZonas {
    public static ArrayList<Zonas> seleccionarTodo(){
        return DZonas.seleccionarTodo();
    }

    // Mediante los datos necesarios para hacer una zona añado la zona comprobando los posibles fallos
    public static String anadir(String nombre){
        try {

            Zonas zona = new Zonas(nombre);
            DZonas.anadir(zona);
            return "Zona introducida con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la zona, causa:\n"+e.getMessage();


        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la zona, causa:\n"+e.getMessage();
        }
    }

    // Mediante el número de zona elimino una zona comprobando los posibles fallos
    public static String eliminarPorNumeroDeZona(int numeroDeZona){
        try {
            if (DZonas.eliminarPorNumero(numeroDeZona)) {
                return "Zona eliminada con éxito";
            } else {
                return "Ha ocurrido un error con los datos de la zona, causa:\nEl número de zona no existe\n";
            }

        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la eliminación de la zona, causa:\n"+e.getMessage();
        }
    }


    // Modifico los valores mediante la clave
    public static String modificar(String numeroDeZona, String nombreAnterior, String nombreNuevo){

        try {

            Zonas zonaNueva = new Zonas(nombreNuevo);
            Zonas zonaAntigua = new Zonas(nombreAnterior);

            if (!zonaAntigua.getNombre().equals(zonaNueva.getNombre())){
                DZonas.cambiarNombre(Integer.parseInt(numeroDeZona), nombreNuevo);
            }

            return "Zona modificada con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la zona, causa:\n"+e.getMessage();
        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la modificación de la zona, causa:\n"+e.getMessage();
        }
    }
}

package Proyecto.Controlador;

import Proyecto.DAO.DAtracciones;
import Proyecto.Modelo.Atracciones;

import java.util.ArrayList;

public final class CAtracciones {
    public static ArrayList<Atracciones> seleccionarTodo(){
        return DAtracciones.seleccionarTodo();
    }

    public static ArrayList<Atracciones> seleccionarPorNumeroDeZona(int numeroDeZona){
        return DAtracciones.seleccionarPorNumeroDeZona(numeroDeZona);
    }

    // Mediante los datos necesarios para hacer una atracción añado la atracción comprobando los posibles fallos
    public static String anadir(String nombre, String numeroDeZona){
        try {

            DAtracciones.anadir(new Atracciones(nombre, numeroDeZona));
            return "Atracción introducida con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la atracción, causa:\n"+e.getMessage();

        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la atracción, causa:\n"+e.getMessage();
        }
    }



    // Mediante el número de atracción elimino una atracción comprobando los posibles fallos
    public static String eliminarPorNumeroDeAtraccion(int numeroDeAtraccion){
        try {

            if (DAtracciones.eliminarPorNumero(numeroDeAtraccion)) {
                return "Atracción eliminada con éxito";
            } else {
                return "Ha ocurrido un error con los datos de la atracción, causa:\nEl numero de atracción no existe\n";
            }

        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la atracción, causa:\n"+e.getMessage();
        }
    }

    // Modifico los valores mediante la clave
    public static String modificar(String numeroDeAtraccion, String nombreAnterior, String numeroDeZonaAnterior, String nombreNuevo, String numeroDeZonaNuevo){

        try {

            Atracciones atraccionAntigua = new Atracciones(nombreAnterior, numeroDeZonaAnterior);
            Atracciones atraccionNueva = new Atracciones(nombreNuevo, numeroDeZonaNuevo);

            if (!atraccionAntigua.getNombre().equals(atraccionNueva.getNombre())){
                DAtracciones.cambiarNombre(Integer.parseInt(numeroDeAtraccion), atraccionNueva.getNombre());
            }

            if (!(atraccionAntigua.getNumeroDeZona()==atraccionNueva.getNumeroDeZona())){
                DAtracciones.cambiarNumeroDeZona(Integer.parseInt(numeroDeAtraccion), atraccionNueva.getNumeroDeZona());
            }
            return "Atraccion modificada con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la atracción, causa:\n"+e.getMessage();
        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la atracción, causa:\n"+e.getMessage();
        }
    }
}

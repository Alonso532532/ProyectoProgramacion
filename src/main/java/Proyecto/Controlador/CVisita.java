package Proyecto.Controlador;

import Proyecto.DAO.DVisita;
import Proyecto.Modelo.Visita;

import java.util.ArrayList;

public final class CVisita {

    // Selecciónes
    public static ArrayList<Visita> seleccionarTodo(){
        return DVisita.seleccionarTodo();
    }

    public static ArrayList<Visita> seleccionarPorNumeroDeZona(int numeroDeZona){
        return DVisita.seleccionarPorNumeroDeZona(numeroDeZona);
    }

    public static ArrayList<Visita> seleccionarPorDni(String dni){
        return DVisita.seleccionarPorDni(dni);
    }

    // Mediante los datos necesarios para hacer una visita añado la visita comprobando los posibles fallos
    public static String anadir(String dni, String numeroDeZona, String fecha){
        try {

            Visita visita = new Visita(dni, numeroDeZona, fecha, true);
            DVisita.anadir(visita);
            return "Visita introducida con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la visita, causa:\n"+e.getMessage();


        }catch (RuntimeException e){
            // Fallos de SQL
            e.printStackTrace();
            return "Ha ocurrido un error en la introducción de la visita, causa:\n"+e.getMessage();
        }
    }

    // Mediante el conjunto de dni y número de zona elimino una visita comprobando los posibles fallos
    public static String eliminarPorClave(String dni, int numeroDeZona, String fecha){
        try {

            if (DVisita.eliminarPorClave(dni, numeroDeZona, fecha)) {
                return "Visita eliminada con éxito";
            } else {
                return "Ha ocurrido un error con los datos de la visita, causa:\nEl conjunto de dni y número de zona no existen\n";
            }

        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la visita, causa:\n"+e.getMessage();
        }
    }

    // Modifico los valores mediante la clave
    public static String modificar(String dniAnterior, String numeroDeZonaAnterior, String fechaAnterior, String dniNuevo, String numeroDeZonaNuevo, String fechaNueva){

        try {

            Visita visitaAnterior = new Visita(dniAnterior, numeroDeZonaAnterior, fechaAnterior, false);
            Visita visitaNueva = new Visita(dniNuevo, numeroDeZonaNuevo, fechaNueva, true);

            if (!visitaAnterior.getDni().equals(dniNuevo)){
                DVisita.cambiarDni(visitaAnterior, dniNuevo);
                visitaAnterior.setDni(dniNuevo);
            }

            if (visitaAnterior.getNumeroDeZona() != visitaNueva.getNumeroDeZona()){
                DVisita.cambiarNumeroDeZona(visitaAnterior, visitaNueva.getNumeroDeZona());
                visitaAnterior.setNumeroDeZona(numeroDeZonaNuevo);
            }

            if (!visitaAnterior.getFecha().equals(visitaNueva.getFecha())){
                DVisita.cambiarFecha(visitaAnterior, fechaNueva);
                visitaAnterior.setFecha(fechaNueva);
            }

            return "Visita modificada con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la visita, causa:\n"+e.getMessage();
        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la visita, causa:\n"+e.getMessage();
        }
    }
}

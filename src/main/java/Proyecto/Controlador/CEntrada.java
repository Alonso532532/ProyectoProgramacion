package Proyecto.Controlador;

import Proyecto.DAO.DEntrada;
import Proyecto.Modelo.Entrada;

import java.util.ArrayList;

public final class CEntrada {
    public static ArrayList<Entrada> seleccionarTodo(){
        return DEntrada.seleccionarTodo();
    }

    public static ArrayList<Entrada> seleccionarPorDni(String dni){
        return DEntrada.seleccionarPorDni(dni);
    }

    // Mediante los datos necesarios para hacer una entrada añado la entrada comprobando los posibles fallos
    public static String anadir(String tipo, String precio, String dni){
        try {

            Entrada entrada = new Entrada(tipo, precio, dni);
            DEntrada.anadir(entrada);
            return "Entrada introducida con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la entrada, causa:\n"+e.getMessage();


        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la entrada, causa:\n"+e.getMessage();
        }
    }

    // Mediante el número de entrada elimino una entrada comprobando los posibles fallos
    public static String eliminarPorNumeroDeEntrada(int numeroDeEntrada){
        try {

            if (DEntrada.eliminarPorNumero(numeroDeEntrada)) {
                return "Entrada eliminada con éxito";
            } else {
                return "Ha ocurrido un error con los datos de la entrada, causa:\nEl numero de entrada no existe\n";
            }

        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la entrada, causa:\n"+e.getMessage();
        }
    }

    public static String modificar(int numeroDeEntrada, String tipoAnterior, String precioAnterior, String dniAnterior, String tipoNuevo, String precioNuevo, String dniNuevo){

        try {

            Entrada entradaAntigua = new Entrada(tipoAnterior, precioAnterior, dniAnterior);
            Entrada entradaNueva = new Entrada(tipoNuevo, precioNuevo, dniNuevo);

            if (!entradaAntigua.getTipo().equals(entradaNueva.getTipo())){
                DEntrada.cambiarTipo(numeroDeEntrada, tipoNuevo);
            }

            if (entradaAntigua.getPrecio()!=entradaNueva.getPrecio()){
                DEntrada.cambiarPrecio(numeroDeEntrada, entradaNueva.getPrecio());
            }

            if (!entradaAntigua.getDni().equals(entradaNueva.getDni())){
                DEntrada.cambiarDni(numeroDeEntrada, dniNuevo);
            }
            return "Entrada modificada con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la entrada, causa:\n"+e.getMessage();
        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la entrada, causa:\n"+e.getMessage();
        }
    }
}

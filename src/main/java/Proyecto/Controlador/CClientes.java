package Proyecto.Controlador;

import Proyecto.DAO.DClientes;
import Proyecto.Modelo.Clientes;

import java.util.ArrayList;

public final class CClientes {
    public static ArrayList<Clientes> seleccionarTodo(){
        return DClientes.seleccionarTodo();
    }

    // Mediante los datos necesarios para hacer un cliente añado el cliente comprobando los posibles fallos
    public static String anadir(String dni, String edad, String nombre){
        try {

            Clientes cliente = new Clientes(dni, edad, nombre, true);
            DClientes.anadir(cliente);
            return "Cliente introducido con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos del cliente, causa:\n"+e.getMessage();


        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción del cliente, causa:\n"+e.getMessage();
        }
    }

    // Mediante el DNI elimino un cliente comprobando los posibles fallos
    public static String eliminarPorDni(String dni){
        try {

            if (DClientes.eliminarPorDni(dni)) {
                return "Cliente eliminado con éxito";
            } else {
                return "Ha ocurrido un error con los datos del cliente, causa:\nEl dni no existe\n";
            }

        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la eliminación del cliente, causa:\n"+e.getMessage();
        }
    }

    // Modifico los valores mediante la clave
    public static String modificar(String dniAnterior, String edadAnterior, String nombreAnterior, String dniNuevo, String edadNuevo, String nombreNuevo){

        try {

            Clientes clienteAntiguo = new Clientes(dniAnterior, edadAnterior, nombreAnterior, false);
            Clientes clienteNuevo = new Clientes(dniNuevo, edadNuevo, nombreNuevo, false);

            if (clienteAntiguo.getEdad()!=clienteNuevo.getEdad()){
                DClientes.cambiarEdad(clienteAntiguo.getDni(), clienteNuevo.getEdad());
            }

            if (!clienteAntiguo.getNombre().equals(clienteNuevo.getNombre())){
                DClientes.cambiarNombre(clienteAntiguo.getDni(), clienteNuevo.getNombre());
            }

            if (!clienteAntiguo.getDni().equals(clienteNuevo.getDni())){
                DClientes.cambiarDni(clienteAntiguo.getDni(), clienteNuevo.getDni());
            }

            return "Cliente modificado con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos del cliente, causa:\n"+e.getMessage();
        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la modificación del cliente, causa:\n"+e.getMessage();
        }
    }
}

package Proyecto.Controlador;

import Proyecto.DAO.DUsuarios;
import Proyecto.Modelo.Usuario;

import java.util.ArrayList;

public final class CUsuarios {
    public static ArrayList<Usuario> seleccionarTodo(){
        return DUsuarios.seleccionarTodo();
    }

    public static boolean comprobarInicioDeSesion(Usuario usuario){
        return DUsuarios.buscarPorNombreYContrasena(usuario.getNombre(), usuario.getContrasena());
    }

    public static String anadir(String nombre, String contrasena, boolean admin){
        try {

            Usuario usuario = new Usuario(nombre, contrasena, admin, true);
            if (DUsuarios.anadir(usuario)) {
                return "Usuario introducido con éxito";
            } else {
                return "Ha ocurrido un error al introducir el usuario";
            }
        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos de la entrada, causa:\n"+e.getMessage();

        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción de la entrada, causa:\n"+e.getMessage();
        }
    }

    public static String eliminarPorNombre(String nombre){
        if (DUsuarios.eliminarPorNombre(nombre)) {
            return "Usuario eliminado con éxito";
        } else {
            return "Ha ocurrido un error al eliminar el usuario";
        }
    }

    public static String modificar(String nombreAntiguo, boolean adminAntiguo, String nombreNuevo, String contrasenaNueva, boolean adminNuevo){
        try {

            Usuario usuarioAnterior = new Usuario(nombreAntiguo, adminAntiguo, false);
            // Si no introduce una nueva contraseña no compruebo la contraseña y tampoco la cambio,
            // también compruebo si cambia el nombre que ese nombre no esté asignado ya a otro usuario
            if (!contrasenaNueva.isEmpty() && !nombreAntiguo.equals(nombreNuevo)) {
                Usuario usuarioNuevo = new Usuario(nombreNuevo, contrasenaNueva, adminNuevo, true);
            } else if (contrasenaNueva.isEmpty() && !nombreAntiguo.equals(nombreNuevo)){
                Usuario usuarioNuevo = new Usuario(nombreNuevo, adminNuevo, true);
            } else if (!contrasenaNueva.isEmpty()){
                Usuario usuarioNuevo = new Usuario(nombreNuevo, contrasenaNueva, adminNuevo, false);
            } else {
                Usuario usuarioNuevo = new Usuario(nombreNuevo, adminNuevo, false);
            }

            if (!nombreAntiguo.equals(nombreNuevo)){
                DUsuarios.cambiarNombre(nombreAntiguo, nombreNuevo);
            }

            if (!contrasenaNueva.isEmpty()){
                DUsuarios.cambiarContrasena(nombreNuevo, contrasenaNueva);
            }

            if (!adminAntiguo == adminNuevo){
                DUsuarios.cambiarAdmin(nombreNuevo, adminNuevo);
            }

            return "Usuario modificado con éxito";

        }catch (IllegalArgumentException e){
            // Fallos producidos al intentar insertar datos incorrectos
            return "Han ocurrido errores con los datos del usuario, causa:\n"+e.getMessage();
        }catch (RuntimeException e){
            // Fallos de SQL
            return "Ha ocurrido un error en la introducción del usuario, causa:\n"+e.getMessage();
        }
    }
}

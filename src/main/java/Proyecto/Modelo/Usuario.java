package Proyecto.Modelo;

import Proyecto.DAO.DUsuarios;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Usuario {
    private String nombre;
    private String contrasena;
    private boolean esAdmin;

    public Usuario(String nombre, String contrasena, boolean esAdmin, boolean comprobarConcurrencia) {
        String error = "";
        if (!setNombre(nombre)) error+="El nombre contiene carácteres no permitidos, solo se permiten letras y espacios\n";
        if (nombre.length()>20) error+="Nombre demasiado largo, máximo 20 carácteres\n";
        //Compruebo que no hayan espacios irregulares
        Matcher matcher = Pattern.compile(" {2}").matcher(nombre);
        if (matcher.find()) error+="El nombre contiene dos o más espacios juntos, solo se permiten espacios únicos\n";
        if (nombre.equals(" ")) error+="El nombre es inválido, no puede ser un espacio\n";
        if (comprobarConcurrencia){
            try {
                if (DUsuarios.comprobarPorNombre(nombre)) error+="El nombre ya existe\n";
            } catch (RuntimeException e){
                error+="La comprobación de concurrencia no se ha ejecutado correctamente\n";
            }
        }
        if (!setContrasena(contrasena))error+="La contraseña tiene que tener entre 5 y 20 caracteres\n";
        this.esAdmin = esAdmin;
        if (!error.isEmpty()) throw new IllegalArgumentException(error);
    }

    // Este constructor sirve para que si al modificar no se cambia la contraseña, esta no se cambie ni se compruebe
    public Usuario(String nombre, boolean esAdmin, boolean comprobarConcurrencia) {
        String error = "";
        if (!setNombre(nombre))error+="El nombre es demasiado largo/corto o contiene carácteres no permitidos, solo se permiten letras y espacios\n";
        //Compruebo que no hayan espacios irregulares
        Matcher matcher = Pattern.compile(" {2}").matcher(nombre);
        if (matcher.find()) error+="El nombre contiene dos o más espacios juntos, solo se permiten espacios únicos\n";
        if (nombre.equals(" ")) error+="El nombre es inválido, no puede ser un espacio\n";
        if (comprobarConcurrencia){
            try {
                if (DUsuarios.comprobarPorNombre(nombre)) error+="El nombre ya existe\n";
            } catch (RuntimeException e){
                error+="La comprobación de concurrencia no se ha ejecutado correctamente\n";
            }
        }
        this.esAdmin = esAdmin;
        if (!error.isEmpty()) throw new IllegalArgumentException(error);
    }

    public String getNombre() {
        return nombre;
    }

    public boolean setNombre(String nombre) {
        Matcher matcher = Pattern.compile("[A-Za-zñÑáéíóúÁÉÍÓÚ ]+").matcher(nombre);
        if (matcher.matches()){
            this.nombre = nombre;
            return true;
        }
        return false;
    }

    public String getContrasena() {
        return contrasena;
    }

    public boolean setContrasena(String contrasena) {
        if (contrasena.length()>20 || contrasena.length()<5){
            return false;
        }
        this.contrasena = contrasena;
        return true;
    }

    public boolean isEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nombre='" + nombre + '\'' +
                ", contrasena='" + "*".repeat(contrasena.length()) + '\'' +
                ", esAdmin=" + esAdmin +
                '}';
    }
}

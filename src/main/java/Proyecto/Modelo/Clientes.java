package Proyecto.Modelo;

import Proyecto.DAO.DClientes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Clientes {
    private String dni;
    private int edad;
    private String nombre;

    public Clientes(String dni, String edad, String nombre, boolean comprobarConcurrencia) {
        String error = "";
        // Para evitar el problema de claves primarias duplicadas permito que al llamar al constructor tenga la opción de comprobar o no,
        // porque si compruebo siempre no puedo crear un objeto al seleccionar datos de la tabla, ya que siempre que seleccione una fila
        // esa clave primaria ya va a existir impidiéndome crear un objeto a raíz de esos datos
        if (comprobarConcurrencia){
            try {
                if (DClientes.comprobarPorDni(dni)) error += "El dni ya existe\n";
            } catch (RuntimeException e){
                error+="La comprobación de concurrencia no se ha ejecutado correctamente\n";
            }
        }
        if (!setDni(dni)) error+="El dni es incorrecto, ej: 11111111A\n";
        try {
            if (!setEdad(edad)) error+="Demasiada edad o edad negativa\n";
        } catch (NumberFormatException e){
            error+="El formato de la edad es incorrecto\n";
        }
        if (!setNombre(nombre)) error+="El nombre contiene carácteres no permitidos, solo se permiten letras y espacios\n";
        if (nombre.length()>50) error+="Nombre demasiado largo, máximo 50 carácteres\n";
        if (!error.isEmpty()) throw new IllegalArgumentException(error);
    }

    public String getDni() {
        return dni;
    }

    public boolean setDni(String dni) {
        Matcher matcher = Pattern.compile("\\d{8}[A-Za-z]").matcher(dni);
        if (matcher.matches()){
            this.dni = dni.substring(0,8)+dni.substring(8).toUpperCase();
            return true;
        }
        return false;
    }

    public int getEdad() {
        return edad;
    }

    public boolean setEdad(String edadString) {
        int edad = Integer.parseInt(edadString);
        if (edad<=130 && edad > 0){
            this.edad = edad;
            return true;
        }
        return false;
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

    @Override
    public String toString() {
        return "Clientes{" +
                "dni='" + dni + '\'' +
                ", edad=" + edad +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

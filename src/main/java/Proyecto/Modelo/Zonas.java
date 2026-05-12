package Proyecto.Modelo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Zonas {
    private int numeroDeZona;
    private String nombre;

    public Zonas(int numeroDeZona, String nombre) {
        this.numeroDeZona = numeroDeZona;
        this.nombre = nombre;
    }

    public Zonas(String nombre) {
        String error = "";
        if (!setNombre(nombre)) error+="El nombre contiene carácteres no permitidos, solo se permiten letras y espacios\n";
        if (nombre.length()>50) error+="Nombre demasiado largo, máximo 50 carácteres\n";
        if (!error.isEmpty()) throw new IllegalArgumentException(error);
    }

    public int getNumeroDeZona() {
        return numeroDeZona;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean setNombre(String nombre) {
        Matcher matcher = Pattern.compile("[a-záéíóúÁÉÍÓÚñÑ ]+").matcher(nombre);
        if (matcher.matches()){
            this.nombre = nombre;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Zonas{" +
                "numeroDeZona=" + numeroDeZona +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}

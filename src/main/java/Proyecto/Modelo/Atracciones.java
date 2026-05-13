package Proyecto.Modelo;

import Proyecto.DAO.DZonas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Atracciones {
    private int numeroDeAtraccion;
    private String nombre;
    private int numeroDeZona;

    public Atracciones(int numeroDeAtraccion, String nombre, int numeroDeZona) {
        this.numeroDeAtraccion = numeroDeAtraccion;
        this.nombre = nombre;
        this.numeroDeZona = numeroDeZona;
    }

    public Atracciones(String nombre, String numeroDeZona) {
        String error = "";
        if (!setNombre(nombre)) error+="El nombre contiene carácteres no permitidos, solo se permiten letras y espacios\n";
        if (nombre.length()>50) error+="Nombre demasiado largo, máximo 50 carácteres\n";
        try {
            if (!setNumeroDeZona(numeroDeZona)) error+="Numero de zona inexistente\n";
        } catch (NumberFormatException e){
            error+="Formato de numero de zona inválido\n";
        }
        if (!error.isEmpty()) throw new IllegalArgumentException(error);
    }

    public int getNumeroDeAtraccion() {
        return numeroDeAtraccion;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean setNombre(String nombre) {
        Matcher matcher = Pattern.compile("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+").matcher(nombre);
        if (matcher.matches()){
            this.nombre = nombre;
            return true;
        }
        return false;
    }

    public int getNumeroDeZona() {
        return numeroDeZona;
    }

    public boolean setNumeroDeZona(String numeroDeZonaString) {
        int numeroDeZona = Integer.parseInt(numeroDeZonaString);
        try {
            if (DZonas.comprobarNumeroDeZona(numeroDeZona)) {
                this.numeroDeZona = numeroDeZona;
                return true;
            }
        } catch (RuntimeException e){
            return false;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Atracciones{" +
                "numeroDeAtraccion=" + numeroDeAtraccion +
                ", nombre='" + nombre + '\'' +
                ", numeroDeZona=" + numeroDeZona +
                '}';
    }
}

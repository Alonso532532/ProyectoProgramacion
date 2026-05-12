package Proyecto.Modelo;

import Proyecto.DAO.DClientes;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entrada {
    private int numeroDeEntrada;
    private String tipo;
    private double precio = 0;
    private String dni;

    public Entrada(int numeroDeEntrada, String tipo, double precio, String dni) {
        this.numeroDeEntrada = numeroDeEntrada;
        this.tipo = tipo;
        this.precio = precio;
        this.dni = dni;
    }

    public Entrada(String tipo, String precio, String dni) {
        String error = "";
        if (!setTipo(tipo)) error+="Tipo de entrada incorrecto\n";
        try {
            if (!setPrecio(precio)) error+="Precio incorrecto, no puede ser negativo ni superar los 999.99€\n";
        } catch (NumberFormatException e){
            error+="Formato de precio inválido, ej: 23.45\n";
        }
        if (!setDni(dni)) error+="El DNI es inexistente\n";
        if (!error.isEmpty()) throw new IllegalArgumentException(error);
    }

    public int getNumeroDeEntrada() {
        return numeroDeEntrada;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean setTipo(String tipo) {
        Matcher matcher = Pattern.compile("Oferta|Normal|Familia numerosa").matcher(tipo);
        if (matcher.matches()){
            this.tipo = tipo;
            return true;
        }
        return false;
    }

    public double getPrecio() {
        return precio;
    }

    public boolean setPrecio(String precioString) {
        // Compruebo si ha puesto "," en vez de "."
        precioString = precioString.replace(",", ".");
        double precio = Double.parseDouble(precioString);
        if (precio>=0){
            // si es positivo y tiene "." compruebo el tamaño de la parte entera y decimal "3.2"
            if (precioString.contains(".") && (precioString.substring(precioString.indexOf(".")).length()>3 || precioString.substring(0, precioString.indexOf(".")).length()>3)) return false;
            // si no hay "." solo compruebo la entera
            if (!precioString.contains(".") && precioString.length()>3) return false;
            this.precio = precio;
            return true;
        }
        return false;
    }

    public String getDni() {
        return dni;
    }

    public boolean setDni(String dni) {
        if (DClientes.comprobarPorDni(dni)){
            this.dni = dni;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "numeroDeEntrada=" + numeroDeEntrada +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                ", dni='" + dni + '\'' +
                '}';
    }
}

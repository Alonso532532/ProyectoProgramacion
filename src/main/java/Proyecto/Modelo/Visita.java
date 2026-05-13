package Proyecto.Modelo;

import Proyecto.DAO.DClientes;
import Proyecto.DAO.DVisita;
import Proyecto.DAO.DZonas;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Visita {
    private String dni;
    private int numeroDeZona;
    private LocalDateTime fecha;
    private String fechaBonita;

    public Visita(String dni, String numeroDeZona, String fecha, boolean comprobarConcurrencia) {
        String error = "";
        // Para evitar el problema de claves primarias duplicadas permito que al llamar al constructor tenga la opción de comprobar o no,
        // porque si compruebo siempre no puedo crear un objeto al seleccionar datos de la tabla, ya que siempre que seleccione una fila
        // esa clave primaria ya va a existir impidiéndome crear un objeto a raíz de esos datos
        if (!setDni(dni)) error+="El DNI es inexistente\n";
        try {
            if (!setNumeroDeZona(numeroDeZona)) error += "El numero de zona es inexistente\n";
        } catch (NumberFormatException e){
            error += "El formato del numero de zona es inválido\n";
        }
        if (comprobarConcurrencia){
            try {
                if (DVisita.comprobarPorClave(dni, this.numeroDeZona, fecha)) error+="La visita ya existe\n";
            } catch (RuntimeException e){
                error+="La comprobación de concurrencia no se ha ejecutado correctamente\n";
            }
        }
        if (!setFecha(fecha)) error+="El formato de la hora es inválido\n";
        //Si no ha dado fallo el formato de la fecha, se comprueba que sea posible la fecha de la visita
        if (!error.contains("El formato de la hora es inválido\n") && (LocalDateTime.now().isBefore(this.fecha) || LocalDateTime.parse("1900-01-01T00:00:00").isAfter(this.fecha))) error += "La fecha es imposible\n";
        if (!error.isEmpty()) throw new IllegalArgumentException(error);
    }

    public String getDni() {
        return dni;
    }

    public boolean setDni(String dni) {
        try {
            if (DClientes.comprobarPorDni(dni)){
                this.dni = dni;
                return true;
            }
        } catch (RuntimeException e){
            return false;
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public boolean setFecha(String fecha) {
        this.fechaBonita=fecha;
        fecha = fecha.replace(" ", "T");
        try {
            this.fecha = LocalDateTime.parse(fecha);
            return true;
        } catch (DateTimeParseException e){
            return false;
        }
    }

    public String getFechaBonita() {
        return fechaBonita;
    }

    @Override
    public String toString() {
        return "Visita{" +
                "dni='" + dni + '\'' +
                ", numeroDeZona=" + numeroDeZona +
                ", fecha=" + fechaBonita +
                '}';
    }
}

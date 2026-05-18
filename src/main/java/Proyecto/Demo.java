package Proyecto;

import Proyecto.Coexion.Conexion;
import Proyecto.Vista.Inicio;

import java.sql.SQLException;

public class Demo {
    public static void main(String[] args) {
        try{
            Conexion.conectar();
            System.out.println("Conexión exitosa");
        }catch (SQLException e) {
            e.printStackTrace();
        }

        Inicio.ejecutar();
    }
}

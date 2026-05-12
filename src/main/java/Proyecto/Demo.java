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

//        DClientes.seleccionarTodo().forEach(System.out::println);
//        if (DClientes.cambiarDni("87878787S", "87878787H")){
//            System.out.println("BIEN");
//        } else {
//            System.out.println("MAL");
//        }
//        DClientes.seleccionarTodo().forEach(System.out::println);

//        CZonas.seleccionarTodo().forEach(System.out::println);
//        System.out.println(CZonas.eliminarPorNumeroDeZona(2));
//        CZonas.seleccionarTodo().forEach(System.out::println);

//        System.out.println(CVisita.anadir("29232320G", 1, "2026-04-11 18:52:33"));
//        CVisita.seleccionarTodo().forEach(System.out::println);

//        Usuario hola = new Usuario("Admin", "Sor2425$", true);
//        System.out.println(hola);
//        if (DUsuarios.buscarPorNombreYContrasena("Admin", "Sor2425$")) System.out.println("Correcto");

//        String[] campos = {"hola", "Pascual", "Y HECPROOLLL", "Iker"};
//        VAnadir.ejecutar(campos, 1);

        Inicio.ejecutar();
        //VTablas.ejecutar(true, new Point(200, 200), new Dimension(1000, 700));
    }
}

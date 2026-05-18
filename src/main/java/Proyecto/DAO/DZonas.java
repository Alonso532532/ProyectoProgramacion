package Proyecto.DAO;

import Proyecto.Coexion.Conexion;
import Proyecto.Modelo.Zonas;

import java.sql.*;
import java.util.ArrayList;

public final class DZonas {
    // Esta función selecciona todas las zonas
    public static ArrayList<Zonas> seleccionarTodo(){
        try{
            Connection conexion = Conexion.conectar();
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from zonas");
            ArrayList<Zonas> zonas = new ArrayList<>();
            while (resultSet.next()){
                zonas.add(new Zonas(resultSet.getInt("numeroDeZona"), resultSet.getString("nombre")));
            }
            return zonas;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // Esta función selecciona el nombre de zona por su número
    public static String seleccionarNombrePorNumeroDeZona(int numeroDeZona){
        try{
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select nombre from zonas where numeroDeZona = ?");

            preparedStatement.setInt(1, numeroDeZona);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return resultSet.getString("Nombre");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // Esta añade una zona mediante un objeto "Zona"
    public static boolean anadir(Zonas zona){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into zonas (nombre) values (?)");

            preparedStatement.setString(1, zona.getNombre());

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Esta elimina una zona mediante el numero de zona
    public static boolean eliminarPorNumero(int numeroDeZona){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from zonas where numeroDeZona = ?");

            preparedStatement.setInt(1, numeroDeZona);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // Esta comprueba si una zona existe mediante su numero de zona
    public static boolean comprobarNumeroDeZona(int numeroDeZona){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from zonas where numeroDeZona = ?");

            preparedStatement.setInt(1, numeroDeZona);

            // Si ".next" funciona significa que ha encontrado una zona con ese numero de zona y devolverá true, sino false
            return preparedStatement.executeQuery().next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarNombre(int numeroDeZona, String nombre){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update zonas set nombre = ? where numeroDeZona = ?");

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, numeroDeZona);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

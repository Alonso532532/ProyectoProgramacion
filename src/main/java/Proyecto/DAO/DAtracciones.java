package Proyecto.DAO;

import Proyecto.Coexion.Conexion;
import Proyecto.Modelo.Atracciones;

import java.sql.*;
import java.util.ArrayList;

public final class DAtracciones {
    // Esta función seleccióna todas las atracciones
    public static ArrayList<Atracciones> seleccionarTodo(){
        try{
            Connection conexion = Conexion.conectar();
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from atracciones");
            ArrayList<Atracciones> atracciones = new ArrayList<>();
            while (resultSet.next()){
                atracciones.add(new Atracciones(resultSet.getInt("numeroDeAtraccion"), resultSet.getString("nombre"), resultSet.getInt("numeroDeZona")));
            }
            return atracciones;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Selecciono las atracciones que pertenezcan a un número de zona, sirve para evitar los fallos por eliminar zonas con atracciones asignadas
    public static ArrayList<Atracciones> seleccionarPorNumeroDeZona(int numeroDeZona){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from atracciones where numeroDeZona = ?");

            preparedStatement.setInt(1, numeroDeZona);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Atracciones> atracciones = new ArrayList<>();
            while (resultSet.next()){
                atracciones.add(new Atracciones(resultSet.getInt("numeroDeAtraccion"), resultSet.getString("nombre"), resultSet.getInt("numeroDeZona")));
            }

            return atracciones;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Esta añade una atracción mediante un objeto "Atraccion"
    public static boolean anadir(Atracciones atraccion){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into atracciones (nombre, numeroDeZona) values (?, ?)");

            preparedStatement.setString(1, atraccion.getNombre());
            preparedStatement.setInt(2, atraccion.getNumeroDeZona());

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Esta elimina una atraccion mediante el numero de atraccion
    public static boolean eliminarPorNumero(int numeroDeAtraccion){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from atracciones where numeroDeAtraccion = ?");

            preparedStatement.setInt(1, numeroDeAtraccion);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarNombre(int numeroDeAtraccion, String nombre){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update atracciones set nombre = ? where numeroDeAtraccion = ?");

            preparedStatement.setString(1, nombre);
            preparedStatement.setInt(2, numeroDeAtraccion);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarNumeroDeZona(int numeroDeAtraccion, int numeroDeZona){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update atracciones set numeroDeZona = ? where numeroDeAtraccion = ?");

            preparedStatement.setInt(1, numeroDeZona);
            preparedStatement.setInt(2, numeroDeAtraccion);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

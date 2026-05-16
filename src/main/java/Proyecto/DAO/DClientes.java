package Proyecto.DAO;

import Proyecto.Coexion.Conexion;
import Proyecto.Modelo.Clientes;

import java.sql.*;
import java.util.ArrayList;

public final class DClientes {
    // Esta función seleccióna todos los clientes
    public static ArrayList<Clientes> seleccionarTodo(){
        try{
            Connection conexion = Conexion.conectar();
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Clientes");
            ArrayList<Clientes> clientes = new ArrayList<>();
            while (resultSet.next()){
                clientes.add(new Clientes(resultSet.getString("DNI"), resultSet.getString("edad"), resultSet.getString("nombre"), false));
            }
            return clientes;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    // Esta añade un cliente mediante un objeto "Cliente"
    public static boolean anadir(Clientes cliente){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Clientes values (?, ?, ?)");

            preparedStatement.setString(1, cliente.getDni());
            preparedStatement.setInt(2, cliente.getEdad());
            preparedStatement.setString(3, cliente.getNombre());

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Esta se encarga de comprobar si el DNI está en la tabla, lo que es util para comprobaciones
    public static boolean comprobarPorDni(String dni){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from clientes where DNI = ?");

            preparedStatement.setString(1, dni);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Si ".next" funciona significa que ha encontrado un cliente con ese DNI y devolverá true, sino false
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Esta elimina un cliente según su DNI
    public static boolean eliminarPorDni(String dni){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from Clientes where DNI = ?");

            preparedStatement.setString(1, dni);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // En estas modifico todos los atributos
    public static boolean cambiarEdad(String dni, int edad){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update Clientes set edad = ? where DNI = ?");

            preparedStatement.setInt(1, edad);
            preparedStatement.setString(2, dni);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarNombre(String dni, String nombre){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update Clientes set nombre = ? where DNI = ?");

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, dni);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarDni(String dniAntiguo, String dniNuevo){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update Clientes set DNI = ? where DNI = ?");

            preparedStatement.setString(1, dniNuevo);
            preparedStatement.setString(2, dniAntiguo);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}

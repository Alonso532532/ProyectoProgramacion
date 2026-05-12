package Proyecto.DAO;

import Proyecto.Coexion.Conexion;
import Proyecto.Modelo.Entrada;

import java.sql.*;
import java.util.ArrayList;

public final class DEntrada {

    // Esta función seleccióna todas las entradas
    public static ArrayList<Entrada> seleccionarTodo(){
        try{
            Connection conexion = Conexion.conectar();
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from entrada");
            ArrayList<Entrada> entradas = new ArrayList<>();
            while (resultSet.next()){
                entradas.add(new Entrada(resultSet.getInt("numeroDeEntrada") ,resultSet.getString("tipo"), resultSet.getDouble("precio"), resultSet.getString("DNI")));
            }
            return entradas;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Selecciono las entradas que pertenezcan a un DNI, sirve para evitar los fallos por eliminar clientes con entradas asignadas
    public static ArrayList<Entrada> seleccionarPorDni(String dni){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from entrada where dni = ?");

            preparedStatement.setString(1, dni);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Entrada> entradas = new ArrayList<>();
            while (resultSet.next()){
                entradas.add(new Entrada(resultSet.getInt("numeroDeEntrada"), resultSet.getString("tipo"), resultSet.getDouble("precio"), resultSet.getString("dni")));
            }

            return entradas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Esta añade una entrada mediante un objeto "Entrada"
    public static boolean anadir(Entrada entrada){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into entrada (tipo, precio, DNI) values (?, ?, ?)");

            preparedStatement.setString(1, entrada.getTipo());
            preparedStatement.setDouble(2, entrada.getPrecio());
            preparedStatement.setString(3, entrada.getDni());

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Esta elimina una entrada por su número de entrada
    public static boolean eliminarPorNumero(int numeroDeEntrada){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from Entrada where numeroDeEntrada = ?");

            preparedStatement.setInt(1, numeroDeEntrada);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // En estas modifico todos los atributos
    public static boolean cambiarTipo(int numeroDeEntrada, String tipo){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update Entrada set tipo = ? where numeroDeEntrada = ?");

            preparedStatement.setString(1, tipo);
            preparedStatement.setInt(2, numeroDeEntrada);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarPrecio(int numeroDeEntrada, double precio){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update Entrada set precio = ? where numeroDeEntrada = ?");

            preparedStatement.setDouble(1, precio);
            preparedStatement.setInt(2, numeroDeEntrada);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarDni(int numeroDeEntrada, String dni){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update Entrada set DNI = ? where numeroDeEntrada = ?");

            preparedStatement.setString(1, dni);
            preparedStatement.setInt(2, numeroDeEntrada);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

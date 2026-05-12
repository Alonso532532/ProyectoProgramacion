package Proyecto.DAO;

import Proyecto.Coexion.Conexion;
import Proyecto.Modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;

public final class DUsuarios {
    public static ArrayList<Usuario> seleccionarTodo(){
        try{
            Connection conexion = Conexion.conectar();
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from usuarios");
            ArrayList<Usuario> usuarios = new ArrayList<>();
            while (resultSet.next()){
                usuarios.add(new Usuario(resultSet.getString("nombre"), resultSet.getString("contrasena"), resultSet.getBoolean("esAdmin"), false));
            }
            return usuarios;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static boolean buscarPorNombreYContrasena(String nombre, String contrasena){
        try{
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from usuarios where nombre = ? and contrasena = ?");

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, contrasena);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) throw new IllegalArgumentException("No existe ningún usuario con esas credenciales");
            return resultSet.getBoolean(3);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean comprobarPorNombre(String nombre){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from usuarios where nombre = ?");

            preparedStatement.setString(1, nombre);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Si ".next" funciona significa que ha encontrado un usuario con ese nombre y devolverá true, sino false
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean anadir(Usuario usuario){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into usuarios () values (?, ?, ?)");

            preparedStatement.setString(1, usuario.getNombre());
            preparedStatement.setString(2, usuario.getContrasena());
            preparedStatement.setBoolean(3, usuario.isEsAdmin());

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean eliminarPorNombre(String nombre){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from usuarios where nombre = ?");

            preparedStatement.setString(1, nombre);

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarNombre(String nombre, String nombreNuevo){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update usuarios set nombre = ? where nombre = ?");

            preparedStatement.setString(1, nombreNuevo);
            preparedStatement.setString(2, nombre);

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarContrasena(String nombre, String nombreNuevo){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update usuarios set contrasena = ? where nombre = ?");

            preparedStatement.setString(1, nombreNuevo);
            preparedStatement.setString(2, nombre);

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarAdmin(String nombre, boolean admin){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update usuarios set esAdmin = ? where nombre = ?");

            preparedStatement.setBoolean(1, admin);
            preparedStatement.setString(2, nombre);

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

package Proyecto.DAO;

import Proyecto.Coexion.Conexion;
import Proyecto.Modelo.Visita;

import java.sql.*;
import java.util.ArrayList;

public final class DVisita {
    // Esta función seleccióna todas las visitas
    public static ArrayList<Visita> seleccionarTodo(){
        try{
            Connection conexion = Conexion.conectar();
            Statement statement = conexion.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from Visita");
            ArrayList<Visita> visitas = new ArrayList<>();
            while (resultSet.next()){
                visitas.add(new Visita(resultSet.getString("DNI"), resultSet.getString("numeroDeZona"), resultSet.getString("fecha"), false));
            }
            return visitas;
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    //Selecciono las visitas que pertenezcan a un número de zona, sirve para evitar los fallos por eliminar zonas con visitas asignadas
    public static ArrayList<Visita> seleccionarPorNumeroDeZona(int numeroDeZona){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from visita where numeroDeZona = ?");

            preparedStatement.setInt(1, numeroDeZona);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Visita> visitas = new ArrayList<>();
            while (resultSet.next()){
                visitas.add(new Visita(resultSet.getString("DNI"), resultSet.getString("numeroDeZona"), resultSet.getString("fecha"), false));
            }

            return visitas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    //Selecciono las visitas que pertenezcan a un DNI, sirve para evitar los fallos por eliminar clientes con visitas asignadas
    public static ArrayList<Visita> seleccionarPorDni(String dni){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from visita where dni = ?");

            preparedStatement.setString(1, dni);

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Visita> visitas = new ArrayList<>();
            while (resultSet.next()){
                visitas.add(new Visita(resultSet.getString("DNI"), resultSet.getString("numeroDeZona"), resultSet.getString("fecha"), false));
            }

            return visitas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // Este comprueba si una visita existe
    public static boolean comprobarPorClave(String dni, int numeroDeZona, String fecha){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("select * from visita where DNI = ? and numeroDeZona = ? and fecha = ?");

            preparedStatement.setString(1, dni);
            preparedStatement.setInt(2, numeroDeZona);
            preparedStatement.setString(3, fecha);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Si ".next" funciona significa que ha encontrado una visita con esos datos devolverá true, sino false
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Esta añade una visita mediante un objeto "Visita"
    public static boolean anadir(Visita visita){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("insert into Visita values (?, ?, ?)");

            preparedStatement.setString(1, visita.getDni());
            preparedStatement.setInt(2, visita.getNumeroDeZona());
            preparedStatement.setString(3, visita.getFecha().toString().replace("T", " "));

            return preparedStatement.executeUpdate()==1;
        }catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    // Esta elimina una visita mediante un objeto "Visita"
    public static boolean eliminarPorClave(String dni, int numeroDeZona, String fecha){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("delete from visita where DNI = ? and numeroDeZona = ? and fecha = ?");

            preparedStatement.setString(1, dni);
            preparedStatement.setInt(2, numeroDeZona);
            preparedStatement.setString(3, fecha);

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // En estas modifico todos los atributos
    public static boolean cambiarNumeroDeZona(Visita visita, int numeroDeZonaNuevo){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update visita set numeroDeZona = ? where dni = ? and numeroDeZona = ? and fecha = ?");

            preparedStatement.setInt(1, numeroDeZonaNuevo);
            preparedStatement.setString(2, visita.getDni());
            preparedStatement.setInt(3, visita.getNumeroDeZona());
            preparedStatement.setString(4, visita.getFechaBonita());

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarDni(Visita visita, String dniNuevo){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update visita set dni = ? where dni = ? and numeroDeZona = ? and fecha = ?");

            preparedStatement.setString(1, dniNuevo);
            preparedStatement.setString(2, visita.getDni());
            preparedStatement.setInt(3, visita.getNumeroDeZona());
            preparedStatement.setString(4, visita.getFechaBonita());

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean cambiarFecha(Visita visita, String fechaNueva){
        try {
            Connection connection = Conexion.conectar();
            PreparedStatement preparedStatement = connection.prepareStatement("update visita set fecha = ? where dni = ? and numeroDeZona = ? and fecha = ?");

            preparedStatement.setString(1, fechaNueva);
            preparedStatement.setString(2, visita.getDni());
            preparedStatement.setInt(3, visita.getNumeroDeZona());
            preparedStatement.setString(4, visita.getFechaBonita());

            return preparedStatement.executeUpdate()==1;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

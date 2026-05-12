package Proyecto.Coexion;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class Conexion {

    public static Connection conectar() throws SQLException {

        //Para realizar la conexión uso los datos del archivo "src/main/java/Proyecto/Coexion/ParametrosDeConexion.txt"
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/java/Proyecto/Coexion/ParametrosDeConexion.txt"))){
            //Separo los datos
            String[] parametros = bufferedReader.readLine().split(",");
            return DriverManager.getConnection(parametros[0],parametros[1],parametros[2]);
        } catch (SQLException e) {
            throw new SQLException("Error en la conexión de la base de datos");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
import java.sql.Connection;    // Necesario para la clase Connection
import java.sql.SQLException;   // Necesario para manejar excepciones de SQL
import com.utn.gestor.config.DB; 
import com.utn.gestor.config.DB;  // Asegúrate de que este import sea correcto.


public class TestConnection {
    public static void main(String[] args) {
        try {
            // Intentar conectar con la base de datos
            Connection conn = DB.getConnection();  // Conectar usando el método de DB
            System.out.println("Conexión exitosa a la base de datos.");
            conn.close(); // Cerrar conexión después de la prueba
        } catch (SQLException e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}

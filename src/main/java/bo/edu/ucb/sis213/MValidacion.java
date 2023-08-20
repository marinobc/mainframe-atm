
package bo.edu.ucb.sis213;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MValidacion {

    // Método para validar el usuario y pin proporcionados.
    // Devuelve -1 si la validación es exitosa y 0 en caso contrario.
    public int validar(String usuario, String pin) throws IOException, SQLException {
        // Obtenemos una instancia de la clase UsuarioActivo.
        UsuarioActivo almacenar = UsuarioActivo.getInstance();

        // Establecemos una conexión a la base de datos usando la clase MConexion.
        java.sql.Connection llamar = MConexion.getConnection();

        // Preparamos una consulta SQL para buscar un usuario con el usuario y pin especificados.
        String consulta = "SELECT * FROM usuarios WHERE usuario = ? AND pin = ?";

        try (PreparedStatement statement = llamar.prepareStatement(consulta)) {
            // Establecemos los parámetros para la consulta.
            statement.setString(1, usuario);
            statement.setString(2, pin);

            // Ejecutamos la consulta.
            ResultSet rs = statement.executeQuery();

            // Si se encuentra un resultado, significa que el usuario y el pin son válidos.
            if (rs.next()) {
                // Almacenamos la información del usuario en la instancia de UsuarioActivo.
                almacenar.setId(rs.getInt("id"));
                almacenar.setUsuario(rs.getString("usuario"));
                almacenar.setIntentos(3); // Restablecemos el contador de intentos a 3.
                return -1; // Indicamos que la validación fue exitosa.
            }
        }

        // La validación falló. Reducimos el contador de intentos en 1.
        almacenar.setIntentos(almacenar.getIntentos() - 1);
        return 0; // Indicamos que la validación no fue exitosa.
    }
}

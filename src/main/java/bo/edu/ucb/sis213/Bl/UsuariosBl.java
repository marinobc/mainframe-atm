
package bo.edu.ucb.sis213.Bl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bo.edu.ucb.sis213.Dao.MConexion;
import bo.edu.ucb.sis213.Util.UsuarioActivo;

public class UsuariosBl {

    // Variables estáticas para mantener el ID del usuario y el saldo
    private static int usuarioId;
    private static BigDecimal saldo = BigDecimal.ZERO;

    // Método para validar el usuario y pin proporcionados.
    // Devuelve -1 si la validación es exitosa y 0 en caso contrario.
    public static int validar(String usuario, String pin) throws IOException, SQLException {
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

    // Método para consultar el saldo de un usuario
    public static String consultarSaldo() throws SQLException {
        // Establecer conexión a la base de datos
        java.sql.Connection llamar = MConexion.getConnection();

        // Obtener la instancia de UsuarioActivo (patrón Singleton) para obtener el ID del usuario
        UsuarioActivo id = UsuarioActivo.getInstance();
        usuarioId = id.getId();

        // Consulta SQL para obtener el saldo
        String consulta = "SELECT saldo FROM usuarios WHERE id = ?";
        try {
            // Preparar la consulta
            PreparedStatement preparedStatement = llamar.prepareStatement(consulta);
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si la consulta tiene resultados, asigna el saldo y lo devuelve
            if (resultSet.next()) {
                saldo = resultSet.getBigDecimal("saldo");
                return saldo.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Si no se encontró el saldo, devuelve 0
        return saldo.toString();
    }

    // Método para realizar un depósito
    public static String realizarDeposito(String deposito) throws SQLException {
        if (deposito.isEmpty()) {
            return "Ingrese un valor mayor a 0";
        }
        try {
            // Convertir la cantidad de depósito a double
            BigDecimal cantidad = new BigDecimal(deposito);

            if (cantidad.compareTo(BigDecimal.ZERO) > 0) {
                java.sql.Connection llamar = MConexion.getConnection();
                UsuarioActivo id = UsuarioActivo.getInstance();
                usuarioId = id.getId();

                // Consulta SQL para actualizar el saldo del usuario
                String consultaActualizar = "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?";
                PreparedStatement preparedStatement = llamar.prepareStatement(consultaActualizar);
                preparedStatement.setBigDecimal(1, cantidad);
                preparedStatement.setInt(2, usuarioId);

                int rowsAffected = preparedStatement.executeUpdate();

                // Si la actualización es exitosa, registra la operación y devuelve el saldo
                if (rowsAffected > 0) {
                    HistoricoBl.registrarOperacionHistorico("Deposito", cantidad);
                    return "Depósito realizado con éxito. Su saldo es " + consultarSaldo();
                }
            }

            return "Ingrese un valor mayor a 0";
        } catch (NumberFormatException e) {
            return "Por favor ingrese un numero";
        }
    }

    // Método para realizar un retiro
    public static String realizarRetiro(String retiro) throws SQLException {
        if (retiro.isEmpty()) {
            return "Ingrese un valor mayor a 0";
        }
        try {
            // Convertir la cantidad de retiro a double
            BigDecimal cantidad = new BigDecimal(retiro);

            if (cantidad.compareTo(new BigDecimal(consultarSaldo())) > 0) {
                return "Saldo insuficiente";
            }
            if (cantidad.compareTo(BigDecimal.ZERO) > 0) {
                java.sql.Connection llamar = MConexion.getConnection();
                UsuarioActivo id = UsuarioActivo.getInstance();
                usuarioId = id.getId();

                // Consulta SQL para actualizar el saldo del usuario
                String consultaActualizar = "UPDATE usuarios SET saldo = saldo - ? WHERE id = ?";
                PreparedStatement preparedStatement = llamar.prepareStatement(consultaActualizar);
                preparedStatement.setBigDecimal(1, cantidad);
                preparedStatement.setInt(2, usuarioId);

                int rowsAffected = preparedStatement.executeUpdate();

                // Si la actualización es exitosa, registra la operación y devuelve el saldo
                if (rowsAffected > 0) {
                    HistoricoBl.registrarOperacionHistorico("Retiro", cantidad);
                    return "Retiro realizado con éxito. Su saldo es " + consultarSaldo();
                }
            }

            return "Ingrese un valor mayor a 0";
        } catch (NumberFormatException e) {
            return "Por favor ingrese un numero";
        }
    }

    // Método para cambiar el PIN del usuario
    public static String cambiarPIN(String nuevoPin, String pinAnterior) throws SQLException {
        // Comprobar si ambos PINs son numéricos
        if (nuevoPin.isEmpty() || pinAnterior.isEmpty()) {
            return "No puede dejar los campos vacios";
        }
        try {
            int revision;
            revision = Integer.parseInt(nuevoPin);
            revision = Integer.parseInt(pinAnterior);
        } catch (NumberFormatException e) {
            return "Ingrese solo numeros";
        }

        java.sql.Connection llamar = MConexion.getConnection();
        UsuarioActivo id = UsuarioActivo.getInstance();
        usuarioId = id.getId();

        // Consulta SQL para obtener el PIN actual del usuario
        String consultaPinActual = "SELECT pin FROM usuarios WHERE id = ?";
        try {
            PreparedStatement pinActualStatement = llamar.prepareStatement(consultaPinActual);
            pinActualStatement.setInt(1, usuarioId);
            ResultSet resultSet = pinActualStatement.executeQuery();

            if (resultSet.next()) {
                int pinActual = resultSet.getInt("pin");

                // Si el PIN anterior coincide con el PIN actual, actualiza el PIN
                if (pinActual == Integer.parseInt(pinAnterior)) {
                    String updateQuery = "UPDATE usuarios SET pin = ? WHERE id = ?";
                    try {
                        PreparedStatement preparedStatement = llamar.prepareStatement(updateQuery);
                        preparedStatement.setInt(1, Integer.parseInt(nuevoPin));
                        preparedStatement.setInt(2, usuarioId);
                        int rowsAffected = preparedStatement.executeUpdate();

                        if (rowsAffected > 0) {
                            return "PIN actualizado con éxito.";
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return "Error al cambiar el PIN.";
                    }
                } else {
                    return "Pin incorrecto";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error al consultar el PIN actual.";
        }
        return "Error al cambiar el PIN.";
    }
}
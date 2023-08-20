
package bo.edu.ucb.sis213;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class COperacionesATM {

    // Variables estáticas para mantener el ID del usuario y el saldo
    private static int usuarioId;
    private static double saldo;

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
                double saldo = resultSet.getDouble("saldo");
                return String.valueOf(saldo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Si no se encontró el saldo, devuelve 0
        return String.valueOf(saldo);
    }

    // Método para realizar un depósito
    public static String realizarDeposito(String deposito) throws SQLException {
        if (deposito.isEmpty()) {
            return "Ingrese un valor mayor a 0";
        }
        try {
            // Convertir la cantidad de depósito a double
            double cantidad = Double.parseDouble(deposito);

            if (cantidad > 0) {
                java.sql.Connection llamar = MConexion.getConnection();
                UsuarioActivo id = UsuarioActivo.getInstance();
                usuarioId = id.getId();

                // Consulta SQL para actualizar el saldo del usuario
                String consultaActualizar = "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?";
                PreparedStatement preparedStatement = llamar.prepareStatement(consultaActualizar);
                preparedStatement.setDouble(1, cantidad);
                preparedStatement.setInt(2, usuarioId);

                int rowsAffected = preparedStatement.executeUpdate();

                // Si la actualización es exitosa, registra la operación y devuelve el saldo
                if (rowsAffected > 0) {
                    registrarOperacionHistorico("Deposito", cantidad);
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
            double cantidad = Double.parseDouble(retiro);

            if (cantidad > Double.parseDouble(consultarSaldo())) {
                return "Saldo insuficiente";
            }
            if (cantidad > 0) {
                java.sql.Connection llamar = MConexion.getConnection();
                UsuarioActivo id = UsuarioActivo.getInstance();
                usuarioId = id.getId();

                // Consulta SQL para actualizar el saldo del usuario
                String consultaActualizar = "UPDATE usuarios SET saldo = saldo - ? WHERE id = ?";
                PreparedStatement preparedStatement = llamar.prepareStatement(consultaActualizar);
                preparedStatement.setDouble(1, cantidad);
                preparedStatement.setInt(2, usuarioId);

                int rowsAffected = preparedStatement.executeUpdate();

                // Si la actualización es exitosa, registra la operación y devuelve el saldo
                if (rowsAffected > 0) {
                    registrarOperacionHistorico("Retiro", cantidad);
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
                        } else {
                            return "No se pudo actualizar el PIN.";
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

    // Método para registrar la operación (retiro o depósito) en el historial
    public static void registrarOperacionHistorico(String tipoOperacion, double cantidad) throws SQLException {
        java.sql.Connection llamar = MConexion.getConnection();
        UsuarioActivo id = UsuarioActivo.getInstance();
        usuarioId = id.getId();

        // Consulta SQL para insertar en el histórico
        String insertQuery = "INSERT INTO historico (usuario_id, tipo_operacion, cantidad) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = llamar.prepareStatement(insertQuery);
            preparedStatement.setInt(1, usuarioId);
            preparedStatement.setString(2, tipoOperacion);
            preparedStatement.setDouble(3, cantidad);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Operación registrada en histórico.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para consultar el historial de un cliente
    public static String consultarHistoricoCliente(int idCliente) throws SQLException {
        java.sql.Connection llamar = MConexion.getConnection();

        // Consulta SQL para obtener historial del cliente
        String consultaHistorico = "SELECT tipo_operacion, cantidad FROM historico WHERE usuario_id = ?";
        try {
            PreparedStatement preparedStatement = llamar.prepareStatement(consultaHistorico);
            preparedStatement.setInt(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            StringBuilder historico = new StringBuilder();
            historico.append("Monto | Tipo Operacion\n");

            // Mientras haya resultados, añade la operación y la cantidad al historial
            while (resultSet.next()) {
                String tipoOperacion = resultSet.getString("tipo_operacion");
                double cantidad = resultSet.getDouble("cantidad");

                String signoMonto = tipoOperacion.equalsIgnoreCase("retiro") ? "-" : "+";
                historico.append(String.format("%s%.2f  %s\n", signoMonto, cantidad, tipoOperacion));
            }

            return historico.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Error al consultar el historial del cliente.";
    }
}
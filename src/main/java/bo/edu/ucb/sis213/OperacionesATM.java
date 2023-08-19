
public class OperacionesATM {
	public static void consultarSaldo(Connection connection) {
        String query = "SELECT saldo FROM usuarios WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, usuarioId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double saldo = resultSet.getDouble("saldo");
                System.out.println("Su saldo actual es: $" + saldo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public static void realizarDeposito(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a depositar: $");
        double cantidad = scanner.nextDouble();
    
        if (cantidad <= 0) {
            System.out.println("Cantidad no válida.");
        } else {
            String updateQuery = "UPDATE usuarios SET saldo = saldo + ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setDouble(1, cantidad);
                preparedStatement.setInt(2, usuarioId);
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Depósito realizado con éxito.");
                    // Registrar la operación de depósito en la tabla historico
                    registrarOperacionHistorico(connection, "deposito", cantidad);
    
                    consultarSaldo(connection);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void realizarRetiro(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad a retirar: $");
        double cantidad = scanner.nextDouble();
    
        if (cantidad <= 0) {
            System.out.println("Cantidad no válida.");
        } else if (cantidad > saldo) {
            System.out.println("Saldo insuficiente.");
        } else {
            String updateQuery = "UPDATE usuarios SET saldo = saldo - ? WHERE id = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setDouble(1, cantidad);
                preparedStatement.setInt(2, usuarioId);
                int rowsAffected = preparedStatement.executeUpdate();
    
                if (rowsAffected > 0) {
                    System.out.println("Retiro realizado con éxito.");    
                    // Registrar la operación de retiro en la tabla historico
                    registrarOperacionHistorico(connection, "retiro", cantidad);
    
                    consultarSaldo(connection);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
	
	public static void cambiarPIN(Connection connection) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese su nuevo PIN: ");
        int nuevoPin = scanner.nextInt();
        System.out.print("Confirme su nuevo PIN: ");
        int confirmacionPin = scanner.nextInt();

        if (nuevoPin == confirmacionPin) {
            String updateQuery = "UPDATE usuarios SET pin = ? WHERE id = ? AND pin = ?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);
                preparedStatement.setInt(1, nuevoPin);
                preparedStatement.setInt(2, usuarioId);
                preparedStatement.setInt(3, pinActual);
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    pinActual = nuevoPin;
                    System.out.println("PIN actualizado con éxito.");
                } else {
                    System.out.println("No se pudo actualizar el PIN.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Los PINs no coinciden.");
        }
    }
}

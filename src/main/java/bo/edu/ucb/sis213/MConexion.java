import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MConexion {
	
	private static final String USER = "root";
	private static final String PASSWORD = "123456";
	private static final String HOST = "127.0.0.1";
	private static final String DATABASE = "atm";
	private static final int PORT = 3306;
	
	public static Connection getConnection() throws SQLException {
        String jdbcUrl = String.format("jdbc:mysql://%s:%d/%s", HOST, PORT, DATABASE);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new SQLException("MySQL Driver not found.", e);
        }
        return DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
    }
	
	public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	
	
	
	
	
	public static boolean isConnectionOpen() {
	    Connection connection = null;
	    try {
	        connection = getConnection();
	        return !connection.isClosed();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
	    } finally {
	        closeConnection(connection);
	    }
	}
}

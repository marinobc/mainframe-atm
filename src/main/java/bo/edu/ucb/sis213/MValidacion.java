import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class MValidacion {
	
	public int validar(String usuario, String pin, int intentos) throws IOException, SQLException {
        java.sql.Connection llamar = MConexion.getConnection();
        String consulta = "SELECT * FROM usuarios WHERE usuario = ? AND pin = ?";
        try (PreparedStatement statement = llamar.prepareStatement(consulta)) {
            statement.setString(1, usuario);
            statement.setString(2, pin);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
            	UsuarioActivo almacenar = UsuarioActivo.getInstance();
            	almacenar.setId(rs.getInt("id"));
            	almacenar.setUsuario(rs.getString("usuario"));
                return -1; // Autenticación exitosa
            }
        }
        intentos--;
        if (intentos > 0) {
            JOptionPane.showMessageDialog(null, "Error de Acceso. Le quedan " + intentos + " intentos");
        }
        return intentos;
    }
	/*public int validar(String usuario, String pin, int intentos) throws IOException, SQLException{
		java.sql.Connection llamar = MConexion.getConnection();
		Statement s = (Statement)llamar.createStatement();
		ResultSet rs = (ResultSet) s.executeQuery("Select * from usuarios");
		while (rs.next()) {
			if (rs.getString(2).equals(usuario) && rs.getString(4).equals(pin)){
				return -1;
			}
		}
		intentos--;
		if(intentos>0) {
			JOptionPane.showMessageDialog(null, "Error de Acceso. Le quedan "+intentos+" intentos");
		}
		return intentos;
	}*/
}

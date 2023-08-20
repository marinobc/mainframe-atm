
package bo.edu.ucb.sis213;

/**
 * Clase UsuarioActivo que representa un usuario activo en el sistema.
 * Esta clase utiliza el patrón Singleton para asegurar que haya una única instancia 
 * del usuario activo en todo el sistema.
 */
public class UsuarioActivo {

    // Única instancia de la clase UsuarioActivo.
    private static UsuarioActivo instance;
    
    // ID del usuario.
    private int id;
    
    // Nombre del usuario.
    private String usuario;
    
    // Número de intentos permitidos para el inicio de sesión.
    private int intentos = 3;

    /**
     * Constructor privado para evitar la creación de instancias desde fuera 
     * de la clase y así asegurar el patrón Singleton.
     */
    private UsuarioActivo() { }

    /**
     * Método para obtener la única instancia de la clase UsuarioActivo.
     * Si la instancia no ha sido creada, la crea y luego la retorna.
     */
    public static UsuarioActivo getInstance() {
        if (instance == null) {
            instance = new UsuarioActivo();
        }
        return instance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }
    
    public void setIntentos(int intentos) {
        this.intentos = intentos;
    }

    public int getIntentos() {
        return intentos;
    }
}
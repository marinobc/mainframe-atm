
public class UsuarioActivo {
    private static UsuarioActivo instance;
    private int id;
    private String usuario;

    private UsuarioActivo() { }

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
}
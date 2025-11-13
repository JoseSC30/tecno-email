package datos;

public class Usuario {
    private int usu_id;
    private String usu_nombre;
    private String usu_apellido;
    private String usu_email;
    private String usu_password;
    private int rol_id;

    public Usuario() {}

    public Usuario(int id, String nombre, String apellido, String email, String password, int rol) {
        this.usu_id = id;
        this.usu_nombre = nombre;
        this.usu_apellido = apellido;
        this.usu_email = email;
        this.usu_password = password;
        this.rol_id = rol;
    }

    // Getters y Setters
    public int getId() { return usu_id; }
    public void setId(int id) { this.usu_id = id; }

    public String getNombre() { return usu_nombre; }
    public void setNombre(String nombre) { this.usu_nombre = nombre; }

    public String getApellido() { return usu_apellido; }
    public void setApellido(String apellido) { this.usu_apellido = apellido; }

    public String getEmail() { return usu_email; }
    public void setEmail(String email) { this.usu_email = email; }

    public String getPassword() { return usu_password; }
    public void setPassword(String password) { this.usu_password = password; }

    public int getRolId() { return rol_id; }
    public void setRolId(int rol_id) { this.rol_id = rol_id; }

    @Override
    public String toString() {
        return usu_id + " | " + usu_nombre + " | " + usu_apellido + " | " + usu_email + " | " + usu_password + " | " + rol_id;
    }
}
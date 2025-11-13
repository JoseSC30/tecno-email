package datos;

public class Rol {
    private int rol_id;
    private String rol_nombre;

    public Rol() {}

    public Rol(int id, String nombre) {
        this.rol_id = id;
        this.rol_nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return rol_id; }
    public void setId(int id) { this.rol_id = id; }

    public String getNombre() { return rol_nombre; }
    public void setNombre(String nombre) { this.rol_nombre = nombre; }

    @Override
    public String toString() {
        return rol_id + " | " + rol_nombre;
    }
}

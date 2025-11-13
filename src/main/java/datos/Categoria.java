package datos;

public class Categoria {
    private int cat_id;
    private String cat_nombre;

    public Categoria() {}

    public Categoria(int id, String nombre) {
        this.cat_id = id;
        this.cat_nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return cat_id; }
    public void setId(int id) { this.cat_id = id; }

    public String getNombre() { return cat_nombre; }
    public void setNombre(String nombre) { this.cat_nombre = nombre; }

    @Override
    public String toString() {
        return cat_id + " | " + cat_nombre;
    }
}
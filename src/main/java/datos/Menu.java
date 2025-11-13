package datos;

import java.sql.Date;

public class Menu {
    private int men_id;
    private String men_nombre;
    private String men_tipo;
    private Date men_fini;
    private Date men_ffin;

    public Menu() {}

    public Menu(int men_id, String men_nombre, String men_tipo, Date men_fini, Date men_ffin) {
        this.men_id = men_id;
        this.men_nombre = men_nombre;
        this.men_tipo = men_tipo;
        this.men_fini = men_fini;
        this.men_ffin = men_ffin;
    }

    // Getters y Setters
    public int getId() { return men_id; }
    public void setId(int id) { this.men_id = id; }

    public String getNombre() { return men_nombre; }
    public void setNombre(String nombre) { this.men_nombre = nombre; }

    public String getTipo() { return men_tipo; }
    public void setTipo(String tipo) { this.men_tipo = tipo; }

    public Date getFechaIni() { return men_fini; }
    public void setFechaIni(Date ini) { this.men_fini = ini; }

    public Date getFechaFin() { return men_ffin; }
    public void setFechaFin(Date fin) { this.men_ffin = fin; }

    @Override
    public String toString() {
        return men_id + " | " + men_nombre + " | " + men_tipo + " | " + men_fini + " | " + men_ffin;
    }
}
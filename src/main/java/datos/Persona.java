package datos;

import java.sql.Date;

public class Persona {
    private int id;
    private String nombre;
    private String apellido;
    private Date fechaNac;
    private String ci;
    private String telefono;

    public Persona() {}

    public Persona(int id, String nombre, String apellido, Date fechaNac, String ci, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNac = fechaNac;
        this.ci = ci;
        this.telefono = telefono;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public Date getFechaNac() { return fechaNac; }
    public void setFechaNac(Date fechaNac) { this.fechaNac = fechaNac; }

    public String getCi() { return ci; }
    public void setCi(String ci) { this.ci = ci; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return id + " | " + nombre + " | " + apellido + " | " + fechaNac + " | " + ci + " | " + telefono;
    }
}
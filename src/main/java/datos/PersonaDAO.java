package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    
    public List<Persona> listarPersonas() {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM pr_persona";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Persona p = new Persona(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getString("apellido"),
                    rs.getDate("fecha_nac"),
                    rs.getString("ci"),
                    rs.getString("telefono")
                );
                personas.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar personas: " + e.getMessage());
        }

        return personas;
    }
    
    public void insertarPersona(Persona persona) {
        String sql = "INSERT INTO pr_persona (nombre, apellido, fecha_nac, ci, telefono) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getApellido());
            pstmt.setDate(3, persona.getFechaNac());
            pstmt.setString(4, persona.getCi());
            pstmt.setString(5, persona.getTelefono());

            pstmt.executeUpdate();
            System.out.println("Persona insertada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar persona: " + e.getMessage());
        }
    }
    
    public void editarPersona(Persona persona) {
        String sql = "UPDATE pr_persona SET nombre=?, apellido=?, fecha_nac=?, ci=?, telefono=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getApellido());
            pstmt.setDate(3, persona.getFechaNac());
            pstmt.setString(4, persona.getCi());
            pstmt.setString(5, persona.getTelefono());
            pstmt.setInt(6, persona.getId());

            pstmt.executeUpdate();
            System.out.println("Persona actualizada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al editar persona: " + e.getMessage());
        }
    }
    
    public void eliminarPersona(int id) {
        String sql = "DELETE FROM pr_persona WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Persona eliminada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar persona: " + e.getMessage());
        }
    }
}
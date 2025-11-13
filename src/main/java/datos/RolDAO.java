package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RolDAO {
    
    public List<Rol> listarRoles() {
        List<Rol> roles = new ArrayList<>();
        String sql = "SELECT * FROM roles";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Rol p = new Rol(
                    rs.getInt("rol_id"),
                    rs.getString("rol_nombre")
                );
                roles.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar personas: " + e.getMessage());
        }

        return roles;
    }
    
    public void insertarRol(Rol rol) {
        String sql = "INSERT INTO roles (rol_nombre) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rol.getNombre());

            pstmt.executeUpdate();
            System.out.println("Rol insertad0 correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar rol: " + e.getMessage());
        }
    }
    
    public void editarRol(Rol rol) {
        String sql = "UPDATE roles SET rol_nombre=? WHERE rol_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, rol.getNombre());
            pstmt.setInt(2, rol.getId());

            pstmt.executeUpdate();
            System.out.println("Rol actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al editar rol: " + e.getMessage());
        }
    }
    
    public void eliminarRol(int id) {
        String sql = "DELETE FROM roles WHERE rol_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Rol eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar rol: " + e.getMessage());
        }
    }
}

package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {
    
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Usuario p = new Usuario(
                    rs.getInt("usu_id"),
                    rs.getString("usu_nombre"),
                    rs.getString("usu_apellido"),
                    rs.getString("usu_email"),
                    rs.getString("usu_password"),
                    rs.getInt("rol_id")
                );
                usuarios.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar personas: " + e.getMessage());
        }

        return usuarios;
    }
    
    public void insertarUsuario(Usuario usuario) {
        String sql = "INSERT INTO usuarios (usu_nombre, usu_apellido, usu_email, usu_password, rol_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setInt(5, usuario.getRolId());

            pstmt.executeUpdate();
            System.out.println("Usuario insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar usuario: " + e.getMessage());
        }
    }
    
    public void editarUsuario(Usuario usuario) {
        String sql = "UPDATE usuarios SET usu_nombre=?, usu_apellido=?, usu_email=?, usu_password=?, rol_id=? WHERE usu_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getApellido());
            pstmt.setString(3, usuario.getEmail());
            pstmt.setString(4, usuario.getPassword());
            pstmt.setInt(5, usuario.getRolId());
            pstmt.setInt(6, usuario.getId());

            pstmt.executeUpdate();
            System.out.println("Usuario actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al editar usuario: " + e.getMessage());
        }
    }
    
    public void eliminarUsuario(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Usuario eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar usuario: " + e.getMessage());
        }
    }
}

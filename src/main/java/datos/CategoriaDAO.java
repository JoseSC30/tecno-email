package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    
    public List<Categoria> listarCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria p = new Categoria(
                    rs.getInt("cat_id"),
                    rs.getString("cat_nombre")
                );
                categorias.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar categorias: " + e.getMessage());
        }

        return categorias;
    }
    
    public void insertarCategoria(Categoria categoria) {
        String sql = "INSERT INTO categorias (cat_nombre) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());

            pstmt.executeUpdate();
            System.out.println("Categoria insertado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar categoria: " + e.getMessage());
        }
    }
    
    public void editarCategoria(Categoria categoria) {
        String sql = "UPDATE categorias SET cat_nombre=? WHERE cat_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setInt(2, categoria.getId());

            pstmt.executeUpdate();
            System.out.println("Categoria actualizado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al editar categoria: " + e.getMessage());
        }
    }
    
    public void eliminarCategoria(int id) {
        String sql = "DELETE FROM categorias WHERE cat_id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Categoria eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar categorias: " + e.getMessage());
        }
    }
}

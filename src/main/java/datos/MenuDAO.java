package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuDAO {
    
    public List<Menu> listarMenus() {
        List<Menu> menus = new ArrayList<>();
        String sql = "SELECT * FROM menus";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Menu p = new Menu(
                    rs.getInt("men_id"),
                    rs.getString("men_nombre"),
                    rs.getString("men_tipo"),
                    rs.getDate("men_fini"),
                    rs.getDate("men_ffin")
                );
                menus.add(p);
            }

        } catch (SQLException e) {
            System.out.println("Error al consultar menus: " + e.getMessage());
        }

        return menus;
    }
    
    public void insertarMenu(Menu menu) {
        String sql = "INSERT INTO menus (men_nombre, men_tipo, men_fini, men_ffin) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, menu.getNombre());
            pstmt.setString(2, menu.getTipo());
            pstmt.setDate(3, menu.getFechaIni());
            pstmt.setDate(4, menu.getFechaFin());

            pstmt.executeUpdate();
            System.out.println("Menu insertada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al insertar menu: " + e.getMessage());
        }
    }
    
    public void editarMenu(Menu menu) {
        String sql = "UPDATE menus SET men_nombre=?, men_tipo=?, men_fini=?, men_ffin=? WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, menu.getNombre());
            pstmt.setString(2, menu.getTipo());
            pstmt.setDate(3, menu.getFechaIni());
            pstmt.setDate(4, menu.getFechaFin());
            pstmt.setInt(5, menu.getId());

            pstmt.executeUpdate();
            System.out.println("Menu actualizada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al editar menu: " + e.getMessage());
        }
    }
    
    public void eliminarMenu(int id) {
        String sql = "DELETE FROM menus WHERE id=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Menu eliminada correctamente");

        } catch (SQLException e) {
            System.out.println("Error al eliminar menu: " + e.getMessage());
        }
    }
}
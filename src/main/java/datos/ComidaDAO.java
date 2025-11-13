package datos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ComidaDAO {
    
    public List<String> obtenerTodasLasComidas() {
        List<String> comidas = new ArrayList<>();
        String sql = "SELECT * FROM pr_comida";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            // Agregar nombres de columnas
            StringBuilder header = new StringBuilder();
            for (int i = 1; i <= columnCount; i++) {
                header.append(metaData.getColumnName(i));
                if (i < columnCount) header.append(" | ");
            }
            comidas.add(header.toString());
            comidas.add("-".repeat(header.length()));
            
            // Agregar datos
            while (rs.next()) {
                StringBuilder row = new StringBuilder();
                for (int i = 1; i <= columnCount; i++) {
                    row.append(rs.getString(i) != null ? rs.getString(i) : "NULL");
                    if (i < columnCount) row.append(" | ");
                }
                comidas.add(row.toString());
            }
            
        } catch (SQLException e) {
            comidas.add("Error al consultar comidas: " + e.getMessage());
        }
        
        return comidas;
    }
}
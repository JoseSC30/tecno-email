package negocio;

import datos.MenuDAO;
import datos.Menu;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorMenu {
    
    private final MenuDAO menuDAO;
    
    public ControladorMenu() {
        this.menuDAO = new MenuDAO();
    }
    
    public String listarMenus() {
        System.out.println(" C : Ejecutando consulta LISMEN...");
        List<Menu> menus = menuDAO.listarMenus();
        return formatearResultado("PERSONAS", menus);
    }
    
    public String insertarMenu(String parametros) {
        System.out.println(" C : Ejecutando comando INSMEN...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en INSMEN.";

        // Esperamos: nombre, apellido, fechaNac(YYYY-MM-DD), ci, telefono
        if (parts.size() != 5) {
            return " C: INSMEN espera 4 parámetros: nombre, tipo, fecha inicio, fehca fin";
        }

        try {
            Menu p = new Menu();
            p.setNombre(parts.get(0));
            p.setTipo(parts.get(1));
            p.setFechaIni(Date.valueOf(parts.get(2))); // valida formato yyyy-[m]m-[d]d
            p.setFechaFin(Date.valueOf(parts.get(3))); // valida formato yyyy-[m]m-[d]d

            menuDAO.insertarMenu(p);
            return " C: Menu creada exitosamente.";

        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD. Detalle: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al insertar menu: " + e.getMessage();
        }
    }
    
    public String editarMenu(String parametros) {
        System.out.println(" C : Ejecutando comando EDIMEN...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en EDIMEN.";

        // Esperamos: id, nombre, apellido, fechaNac, ci, telefono
        if (parts.size() != 6) {
            return " C: EDIMEN espera 5 parámetros: id, nombre, tipo, fecha inicio, fecha fin";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            Menu p = new Menu();
            p.setId(id);
            p.setNombre(parts.get(1));
            p.setTipo(parts.get(2));
            p.setFechaIni(Date.valueOf(parts.get(3)));
            p.setFechaFin(Date.valueOf(parts.get(4)));

            menuDAO.editarMenu(p);
            return " C: Menu editada exitosamente.";

        } catch (NumberFormatException e) {
            return " C: ID inválido. Debe ser un número entero.";
        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD.";
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al editar menu: " + e.getMessage();
        }
    }
    
    public String eliminarMenu(String parametros) {
        System.out.println(" C : Ejecutando comando ELIMEN...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en ELIMEN.";

        // Esperamos: id
        if (parts.size() != 1) {
            return " C: ELIMEN espera 1 parámetro: id.";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            menuDAO.eliminarMenu(id);
            return " C: Persona eliminada exitosamente.";

        } catch (NumberFormatException e) {
            return " C: ID inválido. Debe ser un número entero.";
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al eliminar persona: " + e.getMessage();
        }
    }
    
    private List<String> extraerParametros(String texto) {
        if (texto == null) return null;
        int inicio = texto.indexOf('[');
        int fin = texto.lastIndexOf(']');
        if (inicio == -1 || fin == -1 || fin <= inicio) return null;

        String inside = texto.substring(inicio + 1, fin);

        // Buscar todas las ocurrencias entre comillas (\"...\")
        List<String> resultados = new ArrayList<>();
        Pattern p = Pattern.compile("\"([^\"]*)\"");
        Matcher m = p.matcher(inside);
        while (m.find()) {
            resultados.add(m.group(1));
        }

        // Si no se encontraron elementos entre comillas, intentar separar por comas simples
        if (resultados.isEmpty()) {
            // split simple (no recomendado si hay comillas internas)
            String[] parts = inside.split("\\s*,\\s*");
            for (String s : parts) {
                resultados.add(s.replaceAll("^\"|\"$", "").trim());
            }
        }

        return resultados;
    }
    
    private String formatearResultado(String titulo, List<Menu> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DE CONSULTA ").append(titulo).append(" ===\n");

        if (resultados == null || resultados.isEmpty()) {
            sb.append("No se encontraron resultados.\n");
        } else {
            // Cabecera manual (según campos de Persona)
            sb.append("id | nombre | tipo | fecha inicio | fecha fin\n");
            sb.append("---------------------------------------------\n");
            for (Menu p : resultados) {
                sb.append(p.toString()).append("\n");
            }
        }

        sb.append("=== FIN DE CONSULTA ===\n");
        return sb.toString();
    }
}
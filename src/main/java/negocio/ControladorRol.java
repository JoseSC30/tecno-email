package negocio;

import datos.RolDAO;
import datos.Rol;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorRol {
    
    private final RolDAO rolDAO;
    
    public ControladorRol() {
        this.rolDAO = new RolDAO();
    }
    
    public String listarRoles() {
        System.out.println(" C : Ejecutando consulta LISROL...");
        List<Rol> roles = rolDAO.listarRoles();
        return formatearResultado("ROLES", roles);
    }
    
    public String insertarRol(String parametros) {
        System.out.println(" C : Ejecutando comando INSROL...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en INSROL.";

        // Esperamos: nombre
        if (parts.size() != 1) {
            return " C: INSROL espera 1 parámetro: nombre";
        }

        try {
            Rol p = new Rol();
            p.setNombre(parts.get(0));

            rolDAO.insertarRol(p);
            return " C: Rol creado exitosamente.";

        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD. Detalle: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al insertar rol: " + e.getMessage();
        }
    }
    
    public String editarRol(String parametros) {
        System.out.println(" C : Ejecutando comando EDIROL...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en EDIROL.";

        // Esperamos: id, nombre
        if (parts.size() != 2) {
            return " C: EDIPER espera 2 parámetros: id, nombre";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            Rol p = new Rol();
            p.setId(id);
            p.setNombre(parts.get(1));

            rolDAO.editarRol(p);
            return " C: Rol editado exitosamente.";

        } catch (NumberFormatException e) {
            return " C: ID inválido. Debe ser un número entero.";
        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD.";
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al editar rol: " + e.getMessage();
        }
    }
    
    public String eliminarRol(String parametros) {
        System.out.println(" C : Ejecutando comando ELIROL...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en ELIROL.";

        // Esperamos: id
        if (parts.size() != 1) {
            return " C: ELIROL espera 1 parámetro: id.";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            rolDAO.eliminarRol(id);
            return " C: Rol eliminado exitosamente.";

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
    
    private String formatearResultado(String titulo, List<Rol> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DE CONSULTA ").append(titulo).append(" ===\n");

        if (resultados == null || resultados.isEmpty()) {
            sb.append("No se encontraron resultados.\n");
        } else {
            // Cabecera manual (según campos de Persona)
            sb.append("id | nombre\n");
            sb.append("-----------\n");
            for (Rol p : resultados) {
                sb.append(p.toString()).append("\n");
            }
        }

        sb.append("=== FIN DE CONSULTA ===\n");
        return sb.toString();
    }
}

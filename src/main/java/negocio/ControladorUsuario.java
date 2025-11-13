package negocio;

import datos.UsuarioDAO;
import datos.Usuario;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorUsuario {
    private final UsuarioDAO usuarioDAO;
    
    public ControladorUsuario() {
        this.usuarioDAO = new UsuarioDAO();
    }
    
    public String listarUsuarios() {
        System.out.println(" C : Ejecutando consulta LISUSU...");
        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        return formatearResultado("USUARIOS", usuarios);
    }
    
    public String insertarUsuario(String parametros) {
        System.out.println(" C : Ejecutando comando INSUSU...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en INSUSU.";

        // Esperamos: nombre, apellido, fechaNac(YYYY-MM-DD), ci, telefono
        if (parts.size() != 5) {
            return " C: INSPER espera 5 parámetros: nombre, apellido, email, password, rolId.";
        }

        try {
            Usuario p = new Usuario();
            p.setNombre(parts.get(0));
            p.setApellido(parts.get(1));
            p.setEmail(parts.get(2));
            p.setPassword(parts.get(3));
            int rolId = Integer.parseInt(parts.get(4));
            p.setRolId(rolId);

            usuarioDAO.insertarUsuario(p);
            return " C: Usuario creado exitosamente.";

        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD. Detalle: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al insertar Usuario: " + e.getMessage();
        }
    }
    
    public String editarUsuario(String parametros) {
        System.out.println(" C : Ejecutando comando EDIUSU...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en EDIUSU.";

        // Esperamos: id, nombre, apellido, fechaNac, ci, telefono
        if (parts.size() != 6) {
            return " C: EDIPER espera 6 parámetros: id, nombre, apellido, email, password, rolId.";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            Usuario p = new Usuario();
            p.setId(id);
            p.setNombre(parts.get(1));
            p.setApellido(parts.get(2));
            p.setEmail(parts.get(3));
            p.setPassword(parts.get(4));
            int rolId = Integer.parseInt(parts.get(5));
            p.setRolId(rolId);

            usuarioDAO.editarUsuario(p);
            return " C: Usuario editada exitosamente.";

        } catch (NumberFormatException e) {
            return " C: ID inválido. Debe ser un número entero.";
        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD.";
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al editar usuario: " + e.getMessage();
        }
    }
    
    public String eliminarUsuario(String parametros) {
        System.out.println(" C : Ejecutando comando ELIUSU...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en ELIUSU.";

        // Esperamos: id
        if (parts.size() != 1) {
            return " C: ELIUSU espera 1 parámetro: id.";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            usuarioDAO.eliminarUsuario(id);
            return " C: Usuario eliminada exitosamente.";

        } catch (NumberFormatException e) {
            return " C: ID inválido. Debe ser un número entero.";
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al eliminar usuario: " + e.getMessage();
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
    
    private String formatearResultado(String titulo, List<Usuario> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DE CONSULTA ").append(titulo).append(" ===\n");

        if (resultados == null || resultados.isEmpty()) {
            sb.append("No se encontraron resultados.\n");
        } else {
            // Cabecera manual (según campos de Persona)
            sb.append("id | nombre | apellido | email | password | rolId\n");
            sb.append("-------------------------------------------------\n");
            for (Usuario p : resultados) {
                sb.append(p.toString()).append("\n");
            }
        }

        sb.append("=== FIN DE CONSULTA ===\n");
        return sb.toString();
    }
}

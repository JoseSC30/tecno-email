package negocio;

import datos.PersonaDAO;
import datos.Persona;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControladorPersona {
    
    private final PersonaDAO personaDAO;
    
    public ControladorPersona() {
        this.personaDAO = new PersonaDAO();
    }
    
    public String listarPersonas() {
        System.out.println(" C : Ejecutando consulta LISPER...");
        List<Persona> personas = personaDAO.listarPersonas();
        return formatearResultado("PERSONAS", personas);
    }
    
    public String insertarPersona(String parametros) {
        System.out.println(" C : Ejecutando comando INSPER...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en INSPER.";

        // Esperamos: nombre, apellido, fechaNac(YYYY-MM-DD), ci, telefono
        if (parts.size() != 5) {
            return " C: INSPER espera 5 parámetros: nombre, apellido, fecha_nac, ci, telefono.";
        }

        try {
            Persona p = new Persona();
            p.setNombre(parts.get(0));
            p.setApellido(parts.get(1));
            p.setFechaNac(Date.valueOf(parts.get(2))); // valida formato yyyy-[m]m-[d]d
            p.setCi(parts.get(3));
            p.setTelefono(parts.get(4));

            personaDAO.insertarPersona(p);
            return " C: Persona creada exitosamente.";

        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD. Detalle: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al insertar persona: " + e.getMessage();
        }
    }
    
    public String editarPersona(String parametros) {
        System.out.println(" C : Ejecutando comando EDIPER...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en EDIPER.";

        // Esperamos: id, nombre, apellido, fechaNac, ci, telefono
        if (parts.size() != 6) {
            return " C: EDIPER espera 6 parámetros: id, nombre, apellido, fecha_nac, ci, telefono.";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            Persona p = new Persona();
            p.setId(id);
            p.setNombre(parts.get(1));
            p.setApellido(parts.get(2));
            p.setFechaNac(Date.valueOf(parts.get(3)));
            p.setCi(parts.get(4));
            p.setTelefono(parts.get(5));

            personaDAO.editarPersona(p);
            return " C: Persona editada exitosamente.";

        } catch (NumberFormatException e) {
            return " C: ID inválido. Debe ser un número entero.";
        } catch (IllegalArgumentException e) {
            return " C: Fecha inválida. Formato esperado: YYYY-MM-DD.";
        } catch (Exception e) {
            e.printStackTrace();
            return " C: Error al editar persona: " + e.getMessage();
        }
    }
    
    public String eliminarPersona(String parametros) {
        System.out.println(" C : Ejecutando comando ELIPER...");
        List<String> parts = extraerParametros(parametros);
        if (parts == null) return " C: Formato inválido en ELIPER.";

        // Esperamos: id
        if (parts.size() != 1) {
            return " C: ELIPER espera 1 parámetro: id.";
        }

        try {
            int id = Integer.parseInt(parts.get(0));
            personaDAO.eliminarPersona(id);
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
    
    private String formatearResultado(String titulo, List<Persona> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DE CONSULTA ").append(titulo).append(" ===\n");

        if (resultados == null || resultados.isEmpty()) {
            sb.append("No se encontraron resultados.\n");
        } else {
            // Cabecera manual (según campos de Persona)
            sb.append("id | nombre | apellido | fecha_nac | ci | telefono\n");
            sb.append("------------------------------------------------\n");
            for (Persona p : resultados) {
                sb.append(p.toString()).append("\n");
            }
        }

        sb.append("=== FIN DE CONSULTA ===\n");
        return sb.toString();
    }
}

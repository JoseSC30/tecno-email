package com.mycompany.socketsemail;

import datos.PersonaDAO;
import datos.ComidaDAO;
import java.util.List;

public class ProcesadorComandos {
    
    public String procesarComando(String subject) {
        System.out.println(" C : DEBUG - Procesando comando: '" + subject + "'");
        
        String comando = extraerComando(subject);
        System.out.println(" C : DEBUG - Comando extraído: '" + comando + "'");
        
        switch (comando.toUpperCase()) {
//            case "LISPER":
//                return ejecutarConsultaPersonas();
//            case "INSPER":
//                return ejecutarInsertarPersona(subject);
//            case "ELIPER":
//                return ejecutarEliminarPersona(subject);
//            case "EDIPER":
//                return ejecutarEditarPersona(subject);
//            case "LISCOM":
//                return ejecutarConsultaComidas();
            default:
                return "Correo con comando no válido: '" + comando + "'. Comandos válidos: LISPER, LISCOM, INSPER, ELIPER";
        }
    }
    
    private String extraerComando(String subjectEntero) {
        if (subjectEntero == null) return "";

        // 1) Eliminar espacios y tabs al inicio y al final
        String limpio = subjectEntero.trim();

        // 2) Tomar solo los primeros 6 caracteres, si existen
        String sub = limpio.length() >= 6 ? limpio.substring(0, 6) : limpio;

        // 3) Limpiar caracteres no alfanuméricos
        String comando = sub.replaceAll("[^a-zA-Z0-9]", "")
                        .toUpperCase();

        System.out.println(" C : DEBUG - Comando limpio: '" + comando + "'");
        return comando;
    }

    // ******** FUNCIONES - PERSONA ********
    
    private String ejecutarConsultaPersonas() {
        System.out.println(" C : Ejecutando consulta LISPER...");
        PersonaDAO personaDAO = new PersonaDAO();
//        List<String> resultados = personaDAO.obtenerTodasLasPersonas();
//        return formatearResultado("PERSONAS", resultados);
return "";
    }
    
    private String ejecutarInsertarPersona(String parametros) {
        System.out.println(" C : Ejecutando consulta INSPER...");
        PersonaDAO personaDAO = new PersonaDAO();
//        personaDAO.insertarPersona(parametros);
        return " C: Persona creada exitosamente.";
    }
    
    private String ejecutarEliminarPersona(String parametros) {
        System.out.println(" C : Ejecutando consulta ELIPER...");
        PersonaDAO personaDAO = new PersonaDAO();
//        personaDAO.eliminarPersona(parametros);
        return " C: Persona eliminada exitosamente.";
    }
    
    private String ejecutarEditarPersona(String parametros) {
        System.out.println(" C : Ejecutando consulta EDIPER...");
        PersonaDAO personaDAO = new PersonaDAO();
//        personaDAO.editarPersona(parametros);
        return " C: Persona editada exitosamente.";
    }
    
    // ******** FUNCIONES - COMIDA ********
    
    private String ejecutarConsultaComidas() {
        System.out.println(" C : Ejecutando consulta LISCOM...");
        ComidaDAO comidaDAO = new ComidaDAO();
        List<String> resultados = comidaDAO.obtenerTodasLasComidas();
        return formatearResultado("COMIDAS", resultados);
    }
    
    private String formatearResultado(String titulo, List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DE CONSULTA ").append(titulo).append(" ===\n");
        
        if (resultados.isEmpty()) {
            sb.append("No se encontraron resultados.\n");
        } else if (resultados.size() == 1 && resultados.get(0).contains("Error")) {
            sb.append(resultados.get(0)).append("\n");
        } else {
            for (String linea : resultados) {
                sb.append(linea).append("\n");
            }
        }
        
        sb.append("=== FIN DE CONSULTA ===\n");
        return sb.toString();
    }
}
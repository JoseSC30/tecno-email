package negocio;

import datos.ComidaDAO;
import java.util.List;

public class ControladorComida {

    private final ComidaDAO comidaDAO;

    public ControladorComida() {
        this.comidaDAO = new ComidaDAO();
    }

    public String listarComidas() {
        System.out.println("C : Ejecutando comando LISCOM...");

        List<String> resultados = comidaDAO.obtenerTodasLasComidas();
        return formatearResultado("COMIDAS", resultados);
    }

    // Si más adelante agregás comandos como INSCOM, ELICOM, EDICOM, se ponen aquí

    private String formatearResultado(String titulo, List<String> resultados) {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RESULTADO DE CONSULTA ").append(titulo).append(" ===\n");

        if (resultados == null || resultados.isEmpty()) {
            sb.append("No se encontraron resultados.\n");
        } else if (resultados.size() == 1 && resultados.get(0).toUpperCase().contains("ERROR")) {
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

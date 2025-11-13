package comunicacion;
//
import java.util.List;

public class HtmlTemplates {

    public static String resultadoToHtml(String resultadoTexto) {
        StringBuilder html = new StringBuilder();

        html.append("""
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; color: #333; margin: 20px; }
                        h2 { color: #1a73e8; }
                        table { border-collapse: collapse; width: 100%; margin-top: 10px; }
                        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
                        th { background-color: #f2f2f2; }
                        tr:nth-child(even) { background-color: #fafafa; }
                        .no-result { color: #888; font-style: italic; }
                        .footer { margin-top: 20px; font-size: 12px; color: #666; }
                    </style>
                </head>
                <body>
                """);

        // Detectar título (línea que contiene "RESULTADO DE CONSULTA")
        String[] lineas = resultadoTexto.split("\n");
        String titulo = "Consulta";
        for (String linea : lineas) {
            if (linea.contains("RESULTADO DE CONSULTA")) {
                titulo = linea.replace("=== RESULTADO DE CONSULTA", "")
                              .replace("===", "")
                              .trim();
                break;
            }
        }

        html.append("<h2>Resultado de ").append(titulo).append("</h2>");

        // Buscar si hay resultados
        if (resultadoTexto.contains("No se encontraron resultados")) {
            html.append("<p class='no-result'>No se encontraron resultados.</p>");
        } else {
            html.append("<table>");

            boolean enTabla = false;
            for (String linea : lineas) {
                linea = linea.trim();

                // Cabecera (línea con nombres de columnas)
                if (linea.startsWith("id |")) {
                    html.append("<tr>");
                    String[] columnas = linea.split("\\|");
                    for (String col : columnas) {
                        html.append("<th>").append(col.trim()).append("</th>");
                    }
                    html.append("</tr>");
                    enTabla = true;
                    continue;
                }

                // Separador de guiones (lo saltamos)
                if (linea.startsWith("---")) {
                    continue;
                }

                // Fin de tabla
                if (linea.startsWith("=== FIN")) {
                    break;
                }

                // Filas de datos
                if (enTabla && !linea.isEmpty()) {
                    html.append("<tr>");
                    String[] celdas = linea.split("\\|");
                    for (String celda : celdas) {
                        html.append("<td>").append(celda.trim()).append("</td>");
                    }
                    html.append("</tr>");
                }
            }

            html.append("</table>");
        }

        html.append("<div class='footer'>=== FIN DE CONSULTA ===</div>");
        html.append("</body></html>");

        return html.toString();
    }
    
    public static String mensajeCortoHtml(String mensaje) {
    // Elimina el prefijo " C : " o "C :" para dejar solo el texto limpio
    String textoLimpio = mensaje.replaceFirst("^\\s*C\\s*:\\s*", "").trim();

    return String.format("""
        <html>
        <head>
            <meta charset='UTF-8'>
            <style>
                body {
                    font-family: 'Segoe UI', Arial, sans-serif;
                    background-color: #f8fafc;
                    color: #333;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    height: 100vh;
                    margin: 0;
                }
                .card {
                    background: white;
                    padding: 30px 40px;
                    border-radius: 12px;
                    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                    text-align: center;
                    max-width: 500px;
                }
                h2 {
                    color: #1a73e8;
                    margin-bottom: 10px;
                }
                p {
                    font-size: 18px;
                    margin: 0;
                }
                .success {
                    color: #2e7d32;
                    font-weight: bold;
                }
            </style>
        </head>
        <body>
            <div class='card'>
                <h2>✅ Operación Exitosa</h2>
                <p class='success'>%s</p>
            </div>
        </body>
        </html>
        """, textoLimpio);
}
    
    public static String comandosAyuda() {
    return """
        <html>
        <head>
            <meta charset='UTF-8'>
            <style>
                body {
                    font-family: 'Segoe UI', Arial, sans-serif;
                    background-color: #f5f7fa;
                    color: #333;
                    padding: 30px;
                }
                h1 {
                    color: #1a73e8;
                    text-align: center;
                    margin-bottom: 40px;
                }
                h2 {
                    color: #222;
                    border-bottom: 2px solid #1a73e8;
                    padding-bottom: 5px;
                    margin-top: 40px;
                }
                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 15px;
                    background-color: #fff;
                    box-shadow: 0 2px 5px rgba(0,0,0,0.1);
                    border-radius: 10px;
                    overflow: hidden;
                }
                th, td {
                    padding: 12px 15px;
                    border-bottom: 1px solid #ddd;
                }
                th {
                    background-color: #1a73e8;
                    color: white;
                    text-align: left;
                    font-weight: 600;
                }
                tr:hover {
                    background-color: #f1f9ff;
                }
                code {
                    background-color: #eef3f8;
                    padding: 3px 6px;
                    border-radius: 4px;
                    font-size: 0.95em;
                }
                .footer {
                    text-align: center;
                    margin-top: 50px;
                    font-size: 13px;
                    color: #666;
                }
            </style>
        </head>
        <body>
            <h1>📘 Lista de Comandos Disponibles</h1>

            <h2>👤 Gestión de Usuarios</h2>
            <table>
                <tr><th>Acción</th><th>Comando</th></tr>
                <tr><td>Listar Usuarios</td><td><code>LISUSU</code></td></tr>
                <tr><td>Insertar Usuario</td><td><code>INSUSU["nombre","apellido","email","password","rol"]</code></td></tr>
                <tr><td>Editar Usuario</td><td><code>EDIUSU["id","nombre","apellido","email","password","rol"]</code></td></tr>
                <tr><td>Eliminar Usuario</td><td><code>ELIUSU["id"]</code></td></tr>
            </table>

            <h2>🧩 Gestión de Roles</h2>
            <table>
                <tr><th>Acción</th><th>Comando</th></tr>
                <tr><td>Listar Roles</td><td><code>LISROL</code></td></tr>
                <tr><td>Insertar Rol</td><td><code>INSROL["nombre"]</code></td></tr>
                <tr><td>Editar Rol</td><td><code>EDIROL["id","nombre"]</code></td></tr>
                <tr><td>Eliminar Rol</td><td><code>ELIROL["id"]</code></td></tr>
            </table>
           
           <h2>🧩 Gestión de Categorias</h2>
                       <table>
                           <tr><th>Acción</th><th>Comando</th></tr>
                           <tr><td>Listar Roles</td><td><code>LISCAT</code></td></tr>
                           <tr><td>Insertar Rol</td><td><code>INSCAT["nombre"]</code></td></tr>
                           <tr><td>Editar Rol</td><td><code>EDICAT["id","nombre"]</code></td></tr>
                           <tr><td>Eliminar Rol</td><td><code>ELICAT["id"]</code></td></tr>
                       </table>
           
           <h2>🧩 Gestión de Menus</h2>
                       <table>
                           <tr><th>Acción</th><th>Comando</th></tr>
                           <tr><td>Listar Roles</td><td><code>LISMEN</code></td></tr>
                           <tr><td>Insertar Rol</td><td><code>INSMEN["nombre","tipo","Fecha Inicio","Fecha Fin"]</code></td></tr>
                           <tr><td>Editar Rol</td><td><code>EDIMEN["id","nombre","tipo","Fecha Inicio","Fecha Fin"]</code></td></tr>
                           <tr><td>Eliminar Rol</td><td><code>ELIMEN["id"]</code></td></tr>
                       </table>

            <div class='footer'>
                © Sistema de Gestión Automatizada - Todos los derechos reservados
            </div>
        </body>
        </html>
    """;
}
    
    
}
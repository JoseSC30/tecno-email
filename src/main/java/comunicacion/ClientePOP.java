package comunicacion;
        
import negocio.ProcesadorComandos;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import jakarta.mail.internet.MimeUtility;
import java.io.FileInputStream;
import java.util.Properties;

public class ClientePOP {
    
    static final String HOST = "mail.tecnoweb.org.bo";
    static final int PUERTO = 110;
    static final int INTERVALO_VERIFICACION = 15000; // 15 segundos
    
    String user = "grupo22sc";
    String pass = "grup022grup022*";
    String comando;
    
    private Socket skCliente;
    private BufferedReader entrada;
    private DataOutputStream salida;
    private ProcesadorComandos procesador;
    
    public ClientePOP() {
        this.procesador = new ProcesadorComandos();
        
        try {
            conectar();
            if (!autenticar()) {
                System.out.println(" C : Error de autenticación. Cerrando programa.");
                return;
            }
            
            // Obtener cantidad inicial de correos
            int cantidadInicial = obtenerCantidadCorreos();
            System.out.println(" C : Cantidad inicial de correos: " + cantidadInicial);
            System.out.println(" C : Escuchando por nuevos correos cada 15 segundos...");
            System.out.println(" C : Comandos disponibles: LISPER, LISCOM.\n");
            
            int cantidadAnterior = cantidadInicial;
            
            // Bucle infinito para verificar nuevos correos
            while (true) {
                try {
                    Thread.sleep(INTERVALO_VERIFICACION);
                    
                    // Reconectar para evitar timeout del servidor
                    reconectar();
                    
                    int cantidadActual = obtenerCantidadCorreos();
                    
                    if (cantidadActual > cantidadAnterior) {
                        System.out.println(" C : ¡Correo nuevo encontrado!");
                        System.out.println(" C : Cantidad actual de correos: " + cantidadActual);
                        
                        // Recuperar y procesar el último correo
                        procesarUltimoCorreo(cantidadActual);
                        
                        cantidadAnterior = cantidadActual;
                    } else if (cantidadActual < cantidadAnterior) {
                        System.out.println(" C : Se eliminó algún correo. Cantidad actual: " + cantidadActual);
                        //cantidadAnterior = cantidadAnterior;
                        cantidadAnterior = cantidadActual;
                    } else {
                        System.out.println(" C : No hay correo nuevo. Cantidad actual: " + cantidadActual);
                    }
                    
                } catch (InterruptedException e) {
                    System.out.println(" C : Monitor interrumpido.");
                    break;
                } catch (Exception e) {
                    System.out.println(" C : Error durante la verificación: " + e.getMessage());
                    reconectar();
                }
            }
            
        } catch (Exception e) {
            System.out.println(" C : Error inicial: " + e.getMessage());
        } finally {
            cerrarConexion();
        }
    }
    
    private void conectar() throws IOException {
        skCliente = new Socket(HOST, PUERTO);
        entrada = new BufferedReader(new InputStreamReader(skCliente.getInputStream()));
        salida = new DataOutputStream(skCliente.getOutputStream());
        
        System.out.println(" C : Conectado a <" + HOST + ">");
        System.out.println(" S : " + entrada.readLine());
    }
    
    private void reconectar() {
        try {
            cerrarConexion();
            conectar();
            autenticar();
        } catch (Exception e) {
            System.out.println(" C : Error al reconectar: " + e.getMessage());
        }
    }
    
    private boolean autenticar() throws IOException {
        // USER
        comando = "USER " + user + "\r\n";
        salida.writeBytes(comando);
        String respuestaUser = entrada.readLine();
        System.out.println(" S : " + respuestaUser);
        
        if (!respuestaUser.startsWith("+OK")) {
            return false;
        }
        
        // PASS
        comando = "PASS " + pass + "\r\n";
        salida.writeBytes(comando);
        String respuestaPass = entrada.readLine();
        System.out.println(" S : " + respuestaPass);
        
        return respuestaPass.startsWith("+OK");
    }
    
    private int obtenerCantidadCorreos() {
        try {
            comando = "STAT\r\n";
            salida.writeBytes(comando);
            String respuesta = entrada.readLine();
            
            if (respuesta.startsWith("+OK")) {
                String[] partes = respuesta.split(" ");
                if (partes.length >= 2) {
                    return Integer.parseInt(partes[1]);
                }
            }
        } catch (Exception e) {
            System.out.println(" C : Error al obtener cantidad de correos: " + e.getMessage());
        }
        return -1;
    }
    
    private void procesarUltimoCorreo(int cantidadCorreos) {
        try {
            comando = "RETR " + cantidadCorreos + "\r\n";
            salida.writeBytes(comando);
            entrada.readLine(); // Descarta respuesta +OK
        
            String contenidoCorreo = extraerInformacionRelevante(entrada);
        
            String subject = extraerSubject(contenidoCorreo);
            String from = extraerFrom(contenidoCorreo);
        
            System.out.println(" C : Subject extraído: '" + subject + "'");
        
            // Procesar comando
            String resultado = procesador.procesarComando(subject);
            
            // **** Acá preparar envio de respuesta por email. ***************//
            String propertiesFile = "gmail.properties";
            Properties props = new Properties();
            props.load(new FileInputStream(propertiesFile));
            EmailService emailService = new EmailService(props);
            
            String html;
            if ("comandos".equals(resultado)) {
                html = HtmlTemplates.comandosAyuda();
            } else if (resultado.trim().startsWith("C :") || resultado.trim().startsWith("C:")) {
                html = HtmlTemplates.mensajeCortoHtml(resultado);
            } else {
                html = HtmlTemplates.resultadoToHtml(resultado);
            }
            
            emailService.sendHtmlEmail(
                    props.getProperty("mail.user"),
                    from,
                    "RESULTADO DE CONSULTA",
                    html
            );
            
            //****************************************************************//
            System.out.println(resultado);
        
        } catch (Exception e) {
            System.out.println(" C : Error al procesar el último correo: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private String extraerSubject(String contenidoCorreo) {
    // 1) Normalizar saltos de línea a '\n' (por si recibes '\r\n')
        String normalized = contenidoCorreo.replace("\r\n", "\n");

    // 2) Buscar la línea "Subject:" y capturar desde ahí hasta que termine el header (unfolding)
        String[] lines = normalized.split("\n");
        boolean leyendo = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            String linea = lines[i];

            if (!leyendo) {
            // Detecta inicio de Subject (case-insensitive)
                if (linea.toLowerCase().startsWith("subject:")) {
                    leyendo = true;
                // toma lo que viene después de "Subject:"
                    String after = linea.substring(8); // mantiene posibles espacios
                    sb.append(after);
                // no hacemos trim() global aquí; sólo eliminamos el CRLF lógico después
                }
            } else {
            // Si la siguiente línea empieza con espacio o tab => continuación del header
                if (linea.startsWith(" ") || linea.startsWith("\t")) {
                // RFC: reemplazar CRLF + WSP por un solo espacio => agregamos un espacio y la línea sin el prefijo WSP
                    sb.append(" ");
                    sb.append(linea.trim()); // quitamos el WSP inicial, pero mantenemos el contenido
                } else {
                // terminó el header Subject (la siguiente línea no es continuación)
                    break;
                }
            }
        }

        String rawSubject = sb.toString().trim(); // subject sin CRLFs, con unfold correcto

        if (rawSubject.isEmpty()) {
            return "";
        }

    // 3) Ahora decodificamos RFC2047 (varios encoded-words incluidos)
        try {
            String decoded = MimeUtility.decodeText(rawSubject);
        // Imprimir sólo el subject decodificado
            System.out.println(" C : Subject extraído: '" + decoded + "'");
            return decoded;
        } catch (Exception e) {
        // en caso de fallo, imprimir el rawSubject para debug y devolverlo
            System.out.println(" C : Subject (no decodificado) : '" + rawSubject + "'");
            e.printStackTrace();
            return rawSubject;
        }
    }//
    
    private String extraerFrom(String contenidoCorreo) {
    // Normalizamos saltos de línea
        String normalized = contenidoCorreo.replace("\r\n", "\n");

        String[] lines = normalized.split("\n");
        boolean leyendo = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            String linea = lines[i];

            if (!leyendo) {
                if (linea.toLowerCase().startsWith("from:")) {
                    leyendo = true;
                    String after = linea.substring(5); // luego de "From:"
                    sb.append(after);
                }
            } else {
            // Verificar continuación (espacio o tab)
                if (linea.startsWith(" ") || linea.startsWith("\t")) {
                    sb.append(" ");
                    sb.append(linea.trim());
                } else {
                    break;
                }
            }
        }

        String rawFrom = sb.toString().trim();

        if (rawFrom.isEmpty()) {
            return "";
        }

        try {
        // Decodificar RFC2047 (si el nombre está codificado)
            String decoded = MimeUtility.decodeText(rawFrom);

            // Extraer el correo usando regex
            java.util.regex.Matcher matcher = java.util.regex.Pattern
                .compile("<([^>]+)>")
                .matcher(decoded);

            String email;
            if (matcher.find()) {
                email = matcher.group(1).trim();
            } else {
            // Si no hay <>, tomamos el texto directamente (puede ser solo el correo)
                email = decoded.trim();
            }

            System.out.println(" C : From extraído: '" + email + "'");
            return email;
        } catch (Exception e) {
            System.out.println(" C : From (no decodificado): '" + rawFrom + "'");
            e.printStackTrace();
            return rawFrom;
        }
    }


    
    static protected String extraerInformacionRelevante(BufferedReader in) throws IOException {
        StringBuilder informacion = new StringBuilder();
        boolean enHeaders = true;
    
        while (true) {
            String line = in.readLine();
            if (line == null) {
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")) {
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')) {
                line = line.substring(1);
            }
        
            // Solo leer headers (hasta la línea vacía)
            if (enHeaders) {
                informacion.append(line).append("\n");
                if (line.trim().isEmpty()) {
                    enHeaders = false;
                    // Pero seguimos leyendo hasta el final para consumir todo el correo
                }
            }
        }
    
        return informacion.toString();
    }
    
    private void cerrarConexion() {
        try {
            if (salida != null) {
                comando = "QUIT\r\n";
                salida.writeBytes(comando);
                if (entrada != null) {
                    System.out.println(" S : " + entrada.readLine());
                }
            }
            if (skCliente != null && !skCliente.isClosed()) {
                skCliente.close();
                System.out.println(" C : Desconectado de <" + HOST + ">\n");
            }
        } catch (Exception e) {
            System.out.println(" C : Error al cerrar conexión: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        System.out.println(" C : Iniciando monitor de correo POP3 con procesador de comandos...");
        ClientePOP c = new ClientePOP();
    }
}
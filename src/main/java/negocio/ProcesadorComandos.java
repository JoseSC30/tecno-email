package negocio;

public class ProcesadorComandos {
    
    private final ControladorPersona controladorPersona;
    private final ControladorComida controladorComida;
    private final ControladorRol controladorRol;
    private final ControladorUsuario controladorUsuario;
    
    public ProcesadorComandos() {
        this.controladorPersona = new ControladorPersona();
        this.controladorComida = new ControladorComida();
        this.controladorRol = new ControladorRol();
        this.controladorUsuario = new ControladorUsuario();
    }
    
    public String procesarComando(String subject) {
        System.out.println(" C : DEBUG - Procesando comando: '" + subject + "'");
        
        String comando = extraerComando(subject);
        System.out.println(" C : DEBUG - Comando extraído: '" + comando + "'");
        
        switch (comando.toUpperCase()) {
            case "AYUDAS":
                return "comandos";
            // ROL
            case "LISROL":
                return controladorRol.listarRoles();
            case "INSROL":
                return controladorRol.insertarRol(subject);
            case "EDIROL":
                return controladorRol.editarRol(subject);
            case "ELIROL":
                return controladorRol.eliminarRol(subject);
            //
            // USUARIO
            case "LISUSU":
                return controladorUsuario.listarUsuarios();
            case "INSUSU":
                return controladorUsuario.insertarUsuario(subject);
            case "EDIUSU":
                return controladorUsuario.editarUsuario(subject);
            case "ELIUSU":
                return controladorUsuario.eliminarUsuario(subject);
            
            //
            case "LISPER":
                return controladorPersona.listarPersonas();
            case "INSPER":
                return controladorPersona.insertarPersona(subject);
            case "ELIPER":
                return controladorPersona.eliminarPersona(subject);
            case "EDIPER":
                return controladorPersona.editarPersona(subject);
            case "LISCOM":
                return controladorComida.listarComidas();
            default:
                return "Correo con comando no válido: '" + comando + "'.";
        }
    }
    
    //Extrae SOLAMENTE el comando.
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
}
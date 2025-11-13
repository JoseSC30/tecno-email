package com.mycompany.socketsemail;
import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class ClientePOPCopiaOriginal {
    
    static final String HOST = "mail.tecnoweb.org.bo";
    static final int PUERTO = 110;
    String user = "grupo22sc";
    String pass = "grup022grup022*";
    String comando;
    public ClientePOPCopiaOriginal() {
        try {
            Socket skCliente = new Socket(HOST, PUERTO);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(skCliente.getInputStream()));
            DataOutputStream salida = new DataOutputStream(skCliente.getOutputStream());
            
            System.out.println(" C : Conectado a <" + HOST + ">");
            System.out.println(" S : " + entrada.readLine());
            
            comando = "USER " + user + "\r\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
            
            comando = "PASS " + pass + "\r\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
            
            comando = "STAT\r\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
            
            comando = "LIST\r\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
            System.out.println(Multilinea(entrada));
            
            comando = "RETR 5\r\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
            System.out.println(Multilinea(entrada));
//            
//            comando = "DELE 1\r\n";
//            System.out.println(" C : " + comando);
//            salida.writeBytes(comando);
//            System.out.println(" S : " + entrada.readLine());
            
            comando = "QUIT\r\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
            
            skCliente.close();
            System.out.println(" C : Desconectado del <" + HOST + ">");
        } catch (Exception e){
            System.out.println(" C : " + e.getMessage());
        }
    }
    
    static protected String Multilinea(BufferedReader in) throws IOException {
        String lines = "";
        while (true){
            String line = in.readLine();
            if (line == null){
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.equals(".")){
                break;
            }
            if ((line.length() > 0) && (line.charAt(0) == '.')){
                line = line.substring(1);
            }
            lines=lines+"\n    "+line;
        }
        return lines+"\n";
    }
    
    public static void main(String[] args) {
        ClientePOPCopiaOriginal c = new ClientePOPCopiaOriginal();
    }
}
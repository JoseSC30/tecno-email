package com.mycompany.socketsemail;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;

public class ClienteSMTP {
	
    static final String HOST = "mail.tecnoweb.org.bo";
    static final int PUERTO = 25;
    String user_emisor = "<joseluis.universidad2020@gmail.com>";
    String user_receptor = "<grupo22sc@tecnoweb.org.bo>";
    String comando;
	
    public ClienteSMTP() {
        try {
            Socket skCliente = new Socket(HOST, PUERTO);
            BufferedReader entrada = new BufferedReader(
                    new InputStreamReader(skCliente.getInputStream()));
            DataOutputStream salida = new DataOutputStream(skCliente.getOutputStream());
            System.out.println(" C : Conectado a <" + HOST + ">");
            System.out.println(" S : " + entrada.readLine());
			
            comando = "HELO " + HOST + "\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
                        
            comando = "MAIL FROM:" +user_emisor+ "\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
                        
            comando = "RCPT TO:" +user_receptor+ "\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
                        
            comando = "DATA\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
                        
            comando = "SUBJECT: ric\r\n";
            comando+="Estoy enviando el patron en el Subject - PRUEba 01.\r\n";
            comando+=".\r\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
                       
            comando = "QUIT\n";
            System.out.println(" C : " + comando);
            salida.writeBytes(comando);
            System.out.println(" S : " + entrada.readLine());
			
            skCliente.close();
            System.out.println(" C : Desconectado del <" + HOST + ">");
	} catch (Exception e) {
            System.out.println(" C : " + e.getMessage());
	}
    }
	
    public static void main(String[] args) {
	ClienteSMTP c = new ClienteSMTP();
    }
}

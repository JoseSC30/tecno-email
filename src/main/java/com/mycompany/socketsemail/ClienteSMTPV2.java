package com.mycompany.socketsemail;
import java.net.Socket;
import java.io.*;
import java.net.*;

public class ClienteSMTPV2 {
    static final String HOST="mail.tecnoweb.org.bo";
    static final int PORT=25;
    String Command="";
    String emisor="evansbv@gmail.com";
    String receptor="grupo30sc@tecnoweb.org.bo";
    
    public ClienteSMTPV2() {
        try{
            Socket skCliente = new Socket( HOST , PORT );
            BufferedReader entrada = new BufferedReader(
                               new InputStreamReader(skCliente.getInputStream()));
            DataOutputStream salida = new DataOutputStream (skCliente.getOutputStream());
            System.out.println( " C : Conectado a <"+HOST+">" );
            System.out.println( " S : "+entrada.readLine() );
            
            Command="HELO "+HOST+"\r\n";
            System.out.print( " C : "+Command );
            salida.writeBytes( Command);
            System.out.println( " S : "+entrada.readLine() );
            
            Command="MAIL FROM: "+emisor+"\r\n";
            System.out.print( " C : "+Command );
            salida.writeBytes( Command);
            System.out.println( " S : "+entrada.readLine() );
            
            Command="RCPT TO: "+receptor+"\r\n";
            System.out.print( " C : "+Command );
            salida.writeBytes( Command);
            System.out.println( " S : "+entrada.readLine() );
            
            Command="DATA \r\n";
            System.out.print( " C : "+Command );
            salida.writeBytes( Command);
            System.out.println( " S : "+entrada.readLine() );
            Command="SUBJECT: DEMO EMAIL VIA JAVA \r\n";
            Command+="Hola como estas. \r\n";
            Command+=".\r\n";
            System.out.print( " C : "+Command );
            salida.writeBytes( Command);
            System.out.println( " S : "+entrada.readLine() );
            
            Command="QUIT\r\n";
            System.out.print( " C : "+Command );
            salida.writeBytes( Command);
            System.out.println( " S : "+entrada.readLine() );
            
            skCliente.close();
            System.out.println( " C : Desconectado del <"+HOST+">" );
        } catch( Exception e ){
            System.out.println( " C : "+e.getMessage() );
       }
    }
    
    public static void main(String[] args) {
    // TODO Auto-generated method stub
        ClienteSMTPV2 c = new ClienteSMTPV2();
    }
}
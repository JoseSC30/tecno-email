package com.mycompany.socketsemail;
import java.net.ServerSocket;  // Para crear el socket del servidor
import java.net.Socket;        // Para manejar conexiones de clientes
import java.io.PrintWriter;    // Para enviar datos al cliente
import java.io.IOException;    // Para manejar excepciones de E/S

public class Servidor {
	static final int PUERTO = 5000;
	
	public Servidor() {
		try {
			ServerSocket skServidor = new ServerSocket(PUERTO);
			System.out.println(" SERVIDOR : Servidor iniciado");
			
			while(true) {
				Socket skCliente = skServidor.accept();
				System.out.println(" SERVIDOR : Cliente conectado");
				
				PrintWriter salida = new PrintWriter(skCliente.getOutputStream(), true);
				salida.println("Hola cliente, soy el servidor");
				
				skCliente.close();
				System.out.println( " SERVIDOR : Cliente desconectado");
			}
		} catch (IOException e) {
			System.out.println(" SERVIDOR : " + e.getMessage());
		}
	}

	public static void main(String[] args) {
		new Servidor();
	}

}
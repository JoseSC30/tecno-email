package com.mycompany.socketsemail;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cliente {

	static final String HOST = "192.168.0.8";
	static final int PUERTO = 5000;
	
	public Cliente() {
		try {
			Socket skCliente = new Socket(HOST, PUERTO);
			BufferedReader entrada = new BufferedReader(
					new InputStreamReader(skCliente.getInputStream()));
			System.out.println(" CLIENTE : Conectado a <" + HOST + ">");
			System.out.println(" S : " + entrada.readLine());
			skCliente.close();
			System.out.println(" CLIENTE : Desconectado del <" + HOST + ">");
		} catch (Exception e) {
			System.out.println(" CLIENTE : " + e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		Cliente c = new Cliente();
	}
}

package main;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.io.*;
import Mensaje.*;
import oyente.OyenteServer;

public class Client{
	
	static ObjectOutputStream out ;
	static ObjectInputStream in;
	static Socket socket = null;
	static BufferedReader capt = null;
	static String name = "a";
	static Mensaje m = null;
	static Semaphore sem;
	
	public static void main(String[] args)throws IOException{
		
		
		socket = new Socket("localhost", 888);
		
		out = new ObjectOutputStream(socket.getOutputStream());
		in = new ObjectInputStream(socket.getInputStream());
		
		sem = new Semaphore(1);
		
		capt = new BufferedReader(new InputStreamReader(System.in));
		
		//Nombre de usuario:
		while(name.length()< 2) {
		System.out.println("Escribe tu nombre de usuario (Porfavor inserta un nombre mayor que un caracter):");
		name = capt.readLine();
		}
		//iniciar oyente Servidor
		OyenteServer os = new OyenteServer(socket, in, out, sem);
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m = new MensajeConexion(1, name, name);
		
		out.writeObject(m);
		out.flush();
		
		os.start();
		
		menu();

	}

	public static void menu() throws NumberFormatException, IOException {
		//Opcion
		int option = 0;
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("Escribe 1 si quieres consultar información sobre usuarios,"
				+ " 2 si quieres descargar informacion o 3 si quieres salir");
		option = Integer.parseInt(capt.readLine());
		
		while(option != 3) {
			
		if(option == 1) { //Recibe del server toda la informacion de los usuarios
			
			m = new MensajeListaUsuarios(2, name, name);
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else if(option == 2){
			System.out.println("Que fichero quieres pedir?");
			String fichero = capt.readLine();
			m = new MensajePedirFichero(4, name, name, fichero);
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}
		
		else
			System.out.println("Valor invalido, intenalo de nuevo");
		
		try {
			sem.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Escribe 1 si quieres consultar información sobre usuarios,"
				+ " 2 si quieres descargar informacion o 3 si quieres salir");
		try {
			option = Integer.parseInt(capt.readLine());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
		if(option == 3){
			
			m = new MensajeCerrarConexion(3, name, name);

			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		
	}	
}


package main;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import Mensaje.*;
import oyente.OyenteServer;

public class Client implements Runnable{
	
	static ObjectOutputStream out = null;
	static ObjectInputStream in = null;
	static Socket socket = null;
	
	public static void main(String[] args)throws IOException{

		socket = new Socket("localhost", 888);
		
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
		Scanner capt = new Scanner(System.in);

		//Nombre de usuario:
		System.out.println("Escribe tu nombre de usuario:");
		String name = capt.nextLine();
		
		
		List<String> recursos = new ArrayList<String>();
		String rec = capt.nextLine();
		while(!rec.equals("0")) {
			recursos.add(rec);
			rec = capt.nextLine();
		}
		
		//iniciar oyente Servidor
		Thread oyenteS = new Thread();
		oyenteS.start();
		
		//User usuario = new User(name, recursos); Innecesario??
		
		Mensaje m = new MensajeConexion(1, name, name, out);
		
		out.writeObject(m);
		out.flush();
		
		
		
		//Opcion
		int option = 0;
		
		while(option != 3) {
			
		System.out.println("Escribe 1 si quieres consultar información sobre usuarios,"
					+ " 2 si quieres descargar informacion o 3 si quieres salir");
		option = capt.nextInt();
		
		if(option == 1) { //Recibe del server toda la informacion de los usuarios
			
			m = new MensajeListaUsuarios(2, name, name);
			out.writeObject(m);
			out.flush();
		
		}
		else if(option == 2){
			System.out.println("Que fichero quieres pedir?");
			String fichero = capt.nextLine();
			m = new MensajePedirFichero(4, name, name, fichero);
			out.writeObject(m);
			out.flush();
			
		}
		
		else if(option == 3){
			m = new MensajeCerrarConexion(3, name, name);
			out.writeObject(m);
			out.flush();
		}
		
		else
			System.out.println("Valor invalido, intenalo de nuevo");
		}
		
		capt.close();
		socket.close();
	}

	@Override
	public void run() {
		OyenteServer os = new OyenteServer(socket, in, out);
		os.run();
	}	
	
	
}


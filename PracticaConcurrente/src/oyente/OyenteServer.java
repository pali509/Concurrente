package oyente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import Mensaje.*;


public class OyenteServer implements Runnable{
	Socket socket = null;
	ObjectInputStream in = null;
	ObjectOutputStream out = null;
	
	public OyenteServer(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
		this.socket = socket;
		this.in = in;
		this.out = out;
	}
	@Override
	public void run(){
		Scanner inputFile = null;
		
		while(true) {
		
		//Inicializar mensaje
		Mensaje m = null;
		try {
			m = (Mensaje) in.readObject();
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		//Leer mensaje(Que toca hacer)
		switch(m.getTipo()) {
		
		case 1: //MensajeConexion
			System.out.println("Conectado! :)");
			//FALTA:Mostrar info?
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			break;
			
		case 2: //ListaUsuarios
			System.out.println("Client " + m.getOrigen() +  " pide informacion");
			//FALTA:llamar a metodo en server que devuelva la info
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			break;
		
		case 3: //CerrarConexion
			System.out.println("Client " + m.getOrigen() +  " sale");
			//FALTA:"Eliminar usuario"??
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			break;
		
		case 4: //PedirFichero
			System.out.println("Client " + m.getOrigen() +  " pide un fichero");
			//FALTA:Mucho xd
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			break;
		case 5: //ServidorCliente Preparado
			System.out.println("Client " + m.getOrigen() +  " listo para intercambio");
			//FALTA:Mucho xd
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}

			break;
			
			}
		}	
	}
}
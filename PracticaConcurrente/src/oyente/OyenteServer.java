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
		
		//Inicializar mensaje
		Mensaje m = null;
		
		while(true) {
		
		try {
			m = (Mensaje) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Leer mensaje(Que toca hacer)
		switch(m.getTipo()) {
		
		case 1: //MensajeConexion HECHO??????
			
			System.out.println("Conectado! :)");
			
			break;
			
		case 2: //ListaUsuarios HECHO??????
			
			System.out.println("Usuarios conectados y sus recursos:");
			//Hay que hacer bucle para printear el mapa?????
			System.out.println(m.getMap());
			break;
		
		case 3: //CerrarConexion HECHO?????
			
			System.out.println("Hasta la proxima!");
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			break;
		
		case 4: //EmitirFichero
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

			break;
			
			}
		}	
	}
}
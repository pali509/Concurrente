package oyente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import main.Server;
import Mensaje.*;

public class OyenteClient  implements Runnable {
	Socket socket = null;

	public OyenteClient(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run(){
		Scanner inputFile = null;
		
		while(true) {
			
		//Reader
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		//Writer
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream( socket.getOutputStream());
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
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
		
		case 1: //MensajeConexion HECHO??????? 
			System.out.println("Client " + m.getOrigen() +  " connected");
			
			Server.nuevoUser(m.getOrigen(), m.getRec());
			
			m = new MensajeConfConexion(1, m.getOrigen(), m.getOrigen());
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
			
		case 2: //ListaUsuarios 
			System.out.println("Client " + m.getOrigen() +  " pide informacion");
			Server.getUsersInfo(); //Que hago yo con esto xd
			m = new MensajeConfListaUsuarios(2, m.getOrigen(), m.getOrigen());
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			break;
		
		case 3: //CerrarConexion HECHO????
			System.out.println("Client " + m.getOrigen() +  " sale");
			
			Server.eliminarUser(m.getOrigen());
			m = new MensajeConfCerrarSesion(3, m.getOrigen(), m.getOrigen());
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
			m = new MensajePedirFichero(4, m.getOrigen(), m.getOrigen());
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
		case 5: //PreparadoClienteServidor
			System.out.println("Client " + m.getOrigen() +  " listo para intercambio");
			//FALTA:Mucho xd
			m = new MensajePrepClienteServidor(5, m.getOrigen(), m.getOrigen());
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
			
		}
		
		}
		//Cuando coño uso threads y pa que 
		//??????????????????????????????????????????
		//Thread th = new Thread(new Server()); 
		
	}

}

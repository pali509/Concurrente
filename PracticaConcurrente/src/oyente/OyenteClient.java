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
			
			Server.nuevoUser(m.getOrigen(), m.getOutputS());
			
			m = new MensajeConfConexion(1, m.getOrigen(), m.getOrigen());
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			break;
			
		case 2: //ListaUsuarios HECHO????????
			System.out.println("Client " + m.getOrigen() +  " pide informacion");
			m = new MensajeConfListaUsuarios(2, m.getOrigen(), m.getOrigen(), Server.getUsersInfo());
			
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
		
		case 4: //PedirFichero HECHO?????????????????????????????????????????????????
			System.out.println("Client " + m.getOrigen() +  " pide un fichero");
			String client = Server.findCliente(m.getFichero(), 1);
			String fichero = m.getFichero();
			if(client != null) {
				
			ObjectOutputStream outPeer = Server.getOutStream(client);
			
			m = new MensajeEmitirFichero(4, m.getOrigen(), client, fichero);
			try {
				outPeer.writeObject(m);
				outPeer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			}
			
			else
				m = new MensajeError(6, m.getOrigen(), m.getOrigen());
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			break;
			
		case 5: //PreparadoClienteServidor
			System.out.println("Cliente listo para intercambio");

			String cliente = Server.findCliente(m.getDestino(), 2);
			if(cliente != null) {
			ObjectOutputStream outPeer2 = Server.getOutStream(cliente);
			
			m = new MensajePrepServidorCliente(5, m.getDestino(), m.getOrigen(), m.getPuerto());
			
			try {
				outPeer2.writeObject(m);
				outPeer2.flush();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			}
			else
				m = new MensajeError(6, m.getOrigen(), m.getOrigen());
			try {
				out.writeObject(m);
				out.flush();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			break;
			
		}
		
		}
		
	}

}

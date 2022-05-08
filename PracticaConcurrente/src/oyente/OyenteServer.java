package oyente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import Mensaje.*;
import main.Server;


public class OyenteServer extends Thread{
	private Socket socket = null;
	private ObjectInputStream in = null;
	private ObjectOutputStream out = null;
	private Semaphore sem;
	public OyenteServer(Socket socket, ObjectInputStream in, ObjectOutputStream out, Semaphore sem) {
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.sem = sem;
	}

	@Override
	public void run(){
		Scanner inputFile = null;
		
		//Inicializar mensaje
		Mensaje m = null;
		boolean salir = false;
		
		while(!salir) { //Loop principal
		
		try {
			m = (Mensaje) in.readObject();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//Leer mensaje(Que toca hacer)
		switch(m.getTipo()) {
		
		case 1: //MensajeConexion: Printea conectado y suelta el semaforo
			
			System.out.println("Conectado! :)");
			sem.release();
			break;
			
		case 2: //ListaUsuarios : printea el mensaje y suelta el semaforo
			
			System.out.println("Usuarios conectados y sus recursos:");
			
			System.out.println(m.getMap());
			
			sem.release();
			break;
		
		case 3: //CerrarConexion 
			
			System.out.println("Hasta la proxima!");
			salir = true; //Para salir del loop
			try {
				socket.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			break;
		
		case 4: //EmitirFichero: Crea el emisor y manda de vuelta prepClienteServidor
			System.out.println("Client " + m.getOrigen() +  " pide el fichero " + m.getFichero());
			Emisor emisor = new Emisor(m.getFichero(), m.getPuerto());
			
			m = new MensajePrepClienteServidor(5, m.getDestino(), m.getOrigen(), emisor.getPuerto(), m.getFichero());
			
			try {
				
				out.writeObject(m);
				out.flush();
				emisor.start();
				
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			break;
		case 5: //ServidorClientePreparado: Crea el receptor
			System.out.println("Client " + m.getDestino() +  " listo para intercambio");
			Receptor receptor = new Receptor(m.getPuerto());
			
			receptor.start();
			
			try {
				receptor.join();
				sem.release(); //Libera el semaforo
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			break;
		case 6: //Error al encontrar file
			System.out.println("Error al realizar el traspaso de archivos");
			sem.release();
			break;
			}
		}
		
	}
}
package main;
import java.net.*;
import java.util.Scanner;
import java.io.*;
import Mensaje.*;
import oyente.OyenteServer;

public class Client {
	public static void main(String[] args)throws IOException{
		Socket socket = new Socket("localhost", 888);
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
		
		OyenteServer os = new OyenteServer(socket, in, out);

		Scanner capt = new Scanner(System.in);

		//Nombre de usuario:
		System.out.println("Escribe tu nombre de usuario:");
		String name = capt.nextLine();
		
		Mensaje m = new MensajeConexion(1, name, name);
		
		out.writeObject(m);
		out.flush();
		
		os.run();
		
		//Opcion
		int option = 0;
		System.out.println("Escribe 1 si quieres consultar información sobre usuarios,"
				+ " 2 si quieres descargar informacion o 3 si quieres salir");
		
		while(option != 3) {

		option = capt.nextInt();
		
		if(option == 1) { //Recibe del server toda la informacion de los usuarios
			
			m = new MensajeListaUsuarios(2, name, name);
			out.writeObject(m);
			out.flush();
		
		}
		else if(option == 2){
			System.out.println("A quien quieres pedirle el fichero?");
			String dest = capt.nextLine();
			m = new MensajePedirFichero(4, name, dest);
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
		

	
		

		

		
		/*while(!FileName.equals("0")) {

		out.flush();
		
		strFile = bf.readLine();
		
		while(!strFile.equals("//0")) {
			System.out.println("Server: " + strFile);
			strFile = bf.readLine();
		}
		
		out.println(capt.nextLine());
		FileName = out.toString();
	
		}
		*/
		capt.close();
		socket.close();
	}	
	
}


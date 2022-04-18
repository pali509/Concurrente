package main;
import java.net.*;
import java.lang.*;
import java.io.*;
public class Server {
	
	/*
	 Extends threads??
	 Al recibir el mensaje de client:
	 -Crea thread
	 -Thread abre el archivo
	 -Manda de vuelta el contenido a client
	 */
	public static void main(String[] args)throws IOException{
		String aux;
		ServerSocket ss = new ServerSocket(4999);
		Socket s = ss.accept();
		
		System.out.println("Client connected");
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		
		String str = bf.readLine();
		while(str != "0") {//Mientras que el cliente siga queriendo pedir cosas
		
		System.out.println("Client: " + str);
		
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		aux = "fichero.txt";
		pr.println(aux); //Aqui tendria que leer el archivo y printear su contenido 
		pr.flush();
		str = bf.readLine();
		}
	}
	
}

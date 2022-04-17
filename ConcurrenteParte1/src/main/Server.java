package main;
import java.net.*;
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
		ServerSocket ss = new ServerSocket(4999);
		Socket s = ss.accept();
		
		System.out.println("Client connected");
		
		InputStreamReader in = new InputStreamReader(s.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		
		String str = bf.readLine();
		System.out.println("Client: " + str);
		
		PrintWriter pr = new PrintWriter(s.getOutputStream());
		pr.println("fichero.txt");
		pr.flush();
		}
	
}

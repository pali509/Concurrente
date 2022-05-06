package oyente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Emisor extends Thread{

	private static Socket socket = null;
	static String fichero = null;
	
	public Emisor(String fichero) {
		Emisor.fichero = fichero;
	}


	@Override
	public void run() {
		ServerSocket ss = null;
		try {
			ss = new ServerSocket(801);
			socket = ss.accept();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		System.out.println("Cliente Receptor connectado");
		String line;
		PrintWriter pr = null;
		Scanner inputFile = null;
		try {
			pr = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String nombreFile = fichero + ".txt";
		File file = new File(nombreFile);
		
	    try {
	    	inputFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    
	    pr.println("File content: ");
	    pr.flush();
	    
	    while (inputFile.hasNext()){
	        line = inputFile.nextLine();
	         pr.println(line);
	 		 pr.flush();
	    }
	    pr.println("//0");
	    pr.flush();
	      // Close the file.
	      inputFile.close();
	      
	   try {
		   	socket.close();
			ss.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getPuerto() {
		return 801;
	}
}

package oyente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Emisor implements Runnable{

	private static Socket socket = null;
	static String fichero = null;
	
	public Emisor(String fichero) {
		Emisor.fichero = fichero;
	}

	public void main(String[] args)throws IOException {

		
		ServerSocket ss = new ServerSocket(999);
		socket = ss.accept();
		
		System.out.println("Client connected");
		
		Thread th = new Thread(new Emisor(fichero));
		th.start();
		
		
		ss.close();
	}

	@Override
	public void run() {
		String line;
		PrintWriter pr = null;
		Scanner inputFile = null;
		try {
			pr = new PrintWriter(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File file = new File(fichero);
		
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
	}

	public int getPuerto() {
		
		return 0;
	}
}

package main;
import java.net.*;
import java.util.HashMap;
import java.util.List;

import oyente.OyenteClient;

import java.io.*;
public class Server{
	static HashMap<String, List<String>> clientsInfo = new HashMap<>();
	private static Socket socket = null;
	static String str = null;

	public static void main(String[] args)throws IOException {

		while(true) {
			ServerSocket ss = new ServerSocket(888);
			socket = ss.accept();
			
			OyenteClient oc = new OyenteClient(socket);
			oc.run();
			
		}

	}
	public static void nuevoUser(String name, List<String> clientInfo) {
		clientsInfo.put(name, clientInfo);
	}
	public static HashMap<String, List<String>> getUsersInfo() {
		return clientsInfo;
	}
	public static void eliminarUser(String name) {
		clientsInfo.remove(name);
		
	}
	
	
	
	
	
}
	
	/*
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
		
		File file = new File(str);
		
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
	*/


package main;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import oyente.OyenteClient;

import java.io.*;
public class Server{
	static HashMap<String, List<String>> clientsInfo = new HashMap<>();
	static HashMap<String, ObjectOutputStream> clientsOutS = new HashMap<>();
	private static Socket socket = null;
	static String str = null;

	public static void main(String[] args)throws IOException {
		ServerSocket ss = new ServerSocket(888);
		
		while(true) {
			socket = ss.accept();
			OyenteClient oc = new OyenteClient(socket);
			oc.run();
			
		}

	}
	public static void nuevoUser(String name, ObjectOutputStream out) {
		File file = new File("BaseDatos.txt");
		Scanner inputFile = null;
	    try {
	    	inputFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	    
		//clientsInfo.put(name, clientInfo);
		clientsOutS.put(name, out);
	}
	public static HashMap<String, List<String>> getUsersInfo() {
		return clientsInfo;
	}
	public static void eliminarUser(String name) {
		clientsInfo.remove(name);
		
	}
	public static String findCliente(String fichero, int caso) {
		

		if(caso == 1) {
		Iterator<Entry<String, List<String>>> iterator1 = clientsInfo.entrySet().iterator();
		while(iterator1.hasNext()) {
			Entry<String, List<String>> entry = iterator1.next();
			if(entry.getValue().contains(fichero))
				return entry.getKey();
			}
		}
		
		else if(caso == 2) {
			Iterator<Entry<String, ObjectOutputStream>> iterator2 = clientsOutS.entrySet().iterator();
			while(iterator2.hasNext()) {
				Entry<String, ObjectOutputStream> entry = iterator2.next();
				if(entry.getKey().equals(fichero))
					return entry.getKey();
				}
		}
		return null;
	}
	public static ObjectOutputStream getOutStream(String findCliente) {
		return clientsOutS.get(findCliente);
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


package main;
import java.net.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import oyente.LockP;
import oyente.OyenteClient;
import oyente.Puerto;

import java.io.*;
public class Server{
	static HashMap<String, List<String>> clientsInfo = new HashMap<>();
	static HashMap<String, ObjectOutputStream> clientsOutS = new HashMap<>();
	private static Socket socket = null;
	static String str = null;
	private static int CON_MAX = 100;

	public static void main(String[] args)throws IOException {
		ServerSocket ss = new ServerSocket(888);
		int cont = 0;
		LockP lock = new LockP(CON_MAX);
		Puerto puerto = new Puerto();
		
		while(true) {
			socket = ss.accept();
			OyenteClient oc = new OyenteClient(socket, cont, lock, puerto);
			oc.start();
			cont ++;
		}

	}
	public static void nuevoUser(String name, ObjectOutputStream out) throws IOException {
		
	File file = new File("BaseDatos.txt");
	Scanner inputFile = null;
	String line = null;
	int aux = 0;
	List<String> clientInfo = new ArrayList<String>();
	
    try {
    	inputFile = new Scanner(file);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
    line = inputFile.next();
    while (!line.equals(null) && aux == 0){
        
        if(line.equals(name)) {
        	aux = 1;
        	if(inputFile.hasNext()) {
        	line = inputFile.next();
        	while(line.length() == 1) {
        	clientInfo.add(line);
        	if(inputFile.hasNext())
        	line = inputFile.next();
        	else
        	line = "end";
        		}
        	}
        }
        else
        line = inputFile.next();
    }
    
    inputFile.close();
    
	if(aux == 0) {
		PrintWriter writer = new PrintWriter(new FileWriter("BaseDatos.txt", true));
		writer.write("\n");
		writer.write(name);
		writer.close();
	}
	
	    clientsInfo.put(name, clientInfo);
		clientsOutS.put(name, out);
		
		
	}
	public static HashMap<String, List<String>> getUsersInfo() {
		return clientsInfo;
	}
	public static void eliminarUser(String name) {
		clientsInfo.remove(name);
	}
	public static String findCliente(String fichero, int caso, String name) {
		
		if(caso == 1) {
		Iterator<Entry<String, List<String>>> iterator1 = clientsInfo.entrySet().iterator();
		while(iterator1.hasNext()) {
			Entry<String, List<String>> entry = iterator1.next();
			if(entry.getValue().contains(fichero) && !name.equals(entry.getKey()))
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
	public static void addInfo(String origen, String fichero) {
		
		
		/*System.out.println(origen);
		System.out.println(clientsInfo.get(origen));
		*/
		List<String> clientInfo = new ArrayList<String>();
		
		for(int i = 0; i < clientsInfo.get(origen).size(); i++)
			clientInfo.add(clientsInfo.get(origen).get(i));
		
		clientInfo.add(fichero);
		clientsInfo.put(origen, clientInfo);
		
		File file = new File("BaseDatos.txt");
		Scanner inputFile = null;
		
	    try {
			inputFile = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter("BaseDatos.txt", true));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    String line = inputFile.next();
	    int aux = 0;
		
	    while (!line.equals(null) && aux == 0){
	        
	        if(line.equals(origen)) {
	        	aux = 1;
	    		writer.write("\n");
	    		writer.write(fichero);
	        	}
	        else
		        line = inputFile.next();
	        }
	    
		 inputFile.close();
		 writer.close();
	   
	}	
}


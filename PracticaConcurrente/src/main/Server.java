package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;

import oyente.LockP;
import oyente.MonitorClients;
import oyente.MonitorOuts;
import oyente.OyenteClient;
import oyente.Puerto;
public class Server{
	
	//Tablas con sus monitores
	static HashMap<String, List<String>> clientsInfo = new HashMap<>();
	static HashMap<String, ObjectOutputStream> clientsOutS = new HashMap<>();
	private static int counterClientes = 1;
	private static int counterOuts = 1;
	static MonitorClients mClientes = new MonitorClients(counterClientes);
	static MonitorOuts mOuts = new MonitorOuts(counterOuts);
	
	private static Socket socket = null;
	static String str = null;
	private static int CON_MAX = 100;

	
	public static void main(String[] args)throws IOException {
		ServerSocket ss = new ServerSocket(888);
		int cont = 0;
		LockP lock = new LockP(CON_MAX);
		Puerto puerto = new Puerto();
		//loop principal del servidor, al aceptar un cliente crea su oyente y sube el contador
		while(true) {
			socket = ss.accept();
			OyenteClient oc = new OyenteClient(socket, cont, lock, puerto);
			oc.start();
			cont ++;
		}

	}
	public static void nuevoUser(String name, ObjectOutputStream out) throws IOException, InterruptedException {
	//Busca su nombre en el archivo, si lo encuentra carga sus ficheros y si no lo encuentra añade el nombre al archivo
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
    
    if(inputFile.hasNext()) {
    line = inputFile.next();
    while (!line.equals("end") && aux == 0){
        
        if(line.equals(name)) {
        	aux = 1;
        	if(inputFile.hasNext()) {
        	line = inputFile.next();
        	while(line.length() == 1) { //Todos mis archivos tienen un solo caracter en la base de datos y todos los nombres minimo 2
        	clientInfo.add(line);
        	if(inputFile.hasNext())
        	line = inputFile.next();
        	else
        	line = "end";
        		}
        	}
        }
        else if(inputFile.hasNext()) {
        line = inputFile.next();
        }
        else
        line = "end";
    }
    }
    inputFile.close();
    
	if(aux == 0) {
		PrintWriter writer = new PrintWriter(new FileWriter("BaseDatos.txt", true));
		writer.write("\n");
		writer.write(name);
		writer.close();
	}
		
		mClientes.esperar();
	    clientsInfo.put(name, clientInfo);
	    mClientes.retomar();
	    
	    mOuts.esperar();
		clientsOutS.put(name, out);
		mOuts.retomar();
		
	}
	public static HashMap<String, List<String>> getUsersInfo() { //devuelve la lista de clientes con sus archivos
		return clientsInfo;
	}
	public static void eliminarUser(String name) throws InterruptedException {
		//le borra de ambas tablas
		mClientes.esperar();
		clientsInfo.remove(name);
		mClientes.retomar();
		 
		mOuts.esperar(); 
		clientsOutS.remove(name);
		mOuts.retomar();
		
	}
	public static String findCliente(String fichero, int caso, String name) {
		//Dos casos, el primero para el cliente que mandara un archivo y el segundo para el cliente que recibira el archivo pedido
		//En el segundo caso el string fichero es en verdad el nombre del cliente
		
		if(caso == 1) {
			if(clientsInfo.get(name).contains(fichero))
				return null;
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
		//Devuelve el outputStream de el cliente
		ObjectOutputStream outs;
		mOuts.esperar();
		outs = clientsOutS.get(findCliente);
		mOuts.retomar();
		return outs;
	}
	public static void addInfo(String origen, String fichero) throws IOException, InterruptedException{

		HashMap<String, List<String>> clientsInfoNew = new HashMap<>();
		List<String> ficheros = new ArrayList<String>();	
		
		//Añadir a el HashMap el fichero nuevo
		mClientes.esperar();
		ficheros = clientsInfo.get(origen);
		mClientes.retomar();
		
		ficheros.add(fichero);
		clientsInfoNew.put(origen, ficheros);
		
		mClientes.esperar();
		clientsInfo.replace(origen, clientsInfo.get(origen), clientsInfoNew.get(origen));
		mClientes.retomar();
		
		//Añadirlo en el file: 
		//1.Copia de el contenido del file
		
		Scanner copiaFile = new Scanner(new File("BaseDatos.txt"));
		HashMap<String, List<String>> copia = new HashMap<>();
		List<String> copiaFicheros = new ArrayList<String>();
		
		String line = copiaFile.next();
		String aux1;
		String aux2 = null;
		
	    while (!line.equals("end")) {
	    	
	    	copia.put(line, null);
	    	aux1 = line;
	    	copiaFicheros = new ArrayList<String>();
	    	
	    	while(!line.equals("endLoop") && copiaFile.hasNext()){
	    		line = copiaFile.next();
	    		aux2 = line;
	    		if(line.length() == 1)
	    		copiaFicheros.add(line);
	    		else
	    			line = "endLoop";
	    	}

	    	copia.replace(aux1, null, copiaFicheros);

	    	if(!copiaFile.hasNext() && aux2.length() > 1) {
	    		copiaFicheros = new ArrayList<String>();
	    		copia.put(aux2, copiaFicheros);
	    		line = "end";
	    	}
	    	else if(!copiaFile.hasNext() && aux2.length() == 1) {
	    		line = "end";
	    	}
	    	else
	    		line = aux2;
	    }
	    copiaFile.close();
	    
		//2.Añadir el fichero nuevo
	    mClientes.esperar();
	    copia.replace(origen, copia.get(origen), clientsInfo.get(origen));
	    mClientes.retomar();
		
	    //3.Reescribir file
		PrintWriter writer = new PrintWriter(new FileWriter("BaseDatos.txt"));
		
		Iterator<Entry<String, List<String>>> iterator = copia.entrySet().iterator();
		
		while(iterator.hasNext()) {
			Entry<String, List<String>> entry = iterator.next();
			writer.write("\n");
			writer.write(entry.getKey());
			if(entry.getValue().isEmpty())
				writer.write("\n");
			else {
			for(int i = 0; i < entry.getValue().size(); i++) {
				writer.write("\n");
				writer.write(entry.getValue().get(i));
			}
			}
		}
	    
		writer.close();
	   
	}	
}


package main;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class Server implements Runnable{
	
	private static Socket socket = null;
	static String str = null;
	public static void main(String[] args)throws IOException {

		
		ServerSocket ss = new ServerSocket(888);
		socket = ss.accept();
		
		System.out.println("Client connected");
		
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		
		str = bf.readLine();
		while(!str.equals("0")) {//Mientras que el cliente siga queriendo pedir cosas
		
		System.out.println("Client: " + str);
		
		Thread th = new Thread(new Server());
		th.start();
		
		str = bf.readLine();
		
		}
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
	
}

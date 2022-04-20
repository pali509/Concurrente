package main;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	public static void main(String[] args)throws IOException{
		Socket socket = new Socket("localhost", 888);
		
		PrintWriter pr = new PrintWriter(socket.getOutputStream());
		Scanner capt = new Scanner(System.in);
		String strFile = null;
		
		pr.println(capt.nextLine());
		
		String strName = pr.toString();
		
		while(!strName.equals("0")) {

		pr.flush();
		
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		strFile = bf.readLine();
		
		while(!strFile.equals("//0")) {
			System.out.println("Server: " + strFile);
			strFile = bf.readLine();
		}
		
		pr.println(capt.nextLine());
		strName = pr.toString();
	
		}
		
		capt.close();
		socket.close();
		
	}
}


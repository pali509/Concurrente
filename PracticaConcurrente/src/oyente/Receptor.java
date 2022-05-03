package oyente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Receptor{
	static int puerto;
	public Receptor(int puerto) {
		this.puerto = puerto;
	}

	public void main(String[] args)throws IOException{
		Socket socket = new Socket("localhost", puerto);
		Scanner capt = new Scanner(System.in);
		String strFile = null;
		
		InputStreamReader in = new InputStreamReader(socket.getInputStream());
		BufferedReader bf = new BufferedReader(in);
		strFile = bf.readLine();
		
		while(!strFile.equals("//0")) {
			System.out.println(strFile);
			strFile = bf.readLine();
		}
		
		capt.close();
		socket.close();
		
	}
}

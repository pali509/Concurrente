package oyente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Receptor extends Thread{
	int puerto;
	public Receptor(int puerto) {
		this.puerto = puerto;
	}

	public void run(){
		Socket socket = null;
		try {
			socket = new Socket("localhost", puerto);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Scanner capt = new Scanner(System.in);
		String strFile = null;
		
		InputStreamReader in = null;
		try {
			in = new InputStreamReader(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		BufferedReader bf = new BufferedReader(in);
		try {
			strFile = bf.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while(!strFile.equals("//0")) {
			System.out.println(strFile);
			try {
				strFile = bf.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		capt.close();
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

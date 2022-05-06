package Mensaje;

import java.io.Serializable;

public class MensajePrepClienteServidor extends Mensaje implements Serializable{
	int puerto;
	String fichero;
	public MensajePrepClienteServidor(int tipo, String or, String des, int puerto, String fichero) {
		super(tipo, or, des);
		this.puerto = puerto;
		this.fichero = fichero;
	}
	public int getPuerto() {
		return this.puerto;
	}
	public String getFichero() {
		return this.fichero;
	}
}

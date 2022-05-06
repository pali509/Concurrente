package Mensaje;

import java.io.Serializable;

public class MensajePedirFichero extends Mensaje implements Serializable{
	String fichero;
	public MensajePedirFichero(int tipo, String or, String des, String fichero) {
		super(tipo, or, des);
		this.fichero = fichero;
	}
	public String getFichero() {
		return fichero;
	}
}

package Mensaje;

import java.io.Serializable;

public class MensajeEmitirFichero extends Mensaje implements Serializable{
	String fichero = null;
	public MensajeEmitirFichero(int tipo, String or, String des, String fichero) {
		super(tipo, or, des);
		this.fichero= fichero;
	}
	public String getFichero() {
		return this.fichero;
	}
}

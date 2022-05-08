package Mensaje;

import java.io.Serializable;

import oyente.Puerto;

public class MensajeEmitirFichero extends Mensaje implements Serializable{
	private String fichero = null;
	private Puerto puerto;

	public MensajeEmitirFichero(int tipo, String or, String des, String fichero, Puerto puerto) {
		super(tipo, or, des);
		this.fichero= fichero;
		this.puerto = puerto;

	}
	public String getFichero() {
		return this.fichero;
	}
	public int getPuerto() {
		return this.puerto.getPuerto();
	}
}

package Mensaje;

import java.io.Serializable;

public class MensajeConfCerrarSesion extends Mensaje implements Serializable{

	public MensajeConfCerrarSesion(int tipo, String or, String des) {
		super(tipo, or, des);
	}

}

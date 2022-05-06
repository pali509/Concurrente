package Mensaje;

import java.io.Serializable;

public class MensajeError extends Mensaje implements Serializable{

	public MensajeError(int tipo, String or, String des) {
		super(tipo, or, des);
	}

}

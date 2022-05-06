package Mensaje;

import java.io.Serializable;

public class MensajeListaUsuarios extends Mensaje implements Serializable{

	public MensajeListaUsuarios(int tipo, String or, String des) {
		super(tipo, or, des);
	}

}

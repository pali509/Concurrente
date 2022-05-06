package Mensaje;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

public class MensajeConexion extends Mensaje implements Serializable{

	private static final long serialVersionUID = 1L;

	public MensajeConexion(int tipo, String or, String des) {
		super(tipo, or, des);
	}
	
}

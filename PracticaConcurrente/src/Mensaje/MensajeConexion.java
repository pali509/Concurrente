package Mensaje;

import java.io.ObjectOutputStream;
import java.util.List;

public class MensajeConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	ObjectOutputStream out;
	public MensajeConexion(int tipo, String or, String des, ObjectOutputStream out) {
		super(tipo, or, des);
		this.out = out;
	}
	public ObjectOutputStream getOutputS() {
		return this.out;
	}
	
}

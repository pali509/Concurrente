package Mensaje;

import java.util.List;

public class MensajeConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	List<String> recursos;
	public MensajeConexion(int tipo, String or, String des, List<String> recursos) {
		super(tipo, or, des);
		this.recursos = recursos;
	}
	public List<String> getRec() {
		return this.recursos;
	}
	
}

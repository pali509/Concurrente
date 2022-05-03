package Mensaje;

public class MensajePrepServidorCliente extends Mensaje {
	int puerto;
	public MensajePrepServidorCliente(int tipo, String or, String des, int puerto) {
		super(tipo, or, des);
		this.puerto = puerto;
	}
	public int getPuerto() {
		return this.puerto;
	}
}

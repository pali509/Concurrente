package Mensaje;

public class MensajePrepClienteServidor extends Mensaje {
	int puerto;
	public MensajePrepClienteServidor(int tipo, String or, String des, int puerto) {
		super(tipo, or, des);
		this.puerto = puerto;
	}
	public int getPuerto() {
		return this.puerto;
	}

}

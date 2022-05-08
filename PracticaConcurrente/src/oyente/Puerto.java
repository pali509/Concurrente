package oyente;

import java.io.Serializable;

public class Puerto implements Serializable{
	private int puerto;
	public Puerto() {
		puerto = 889;
	}
	public void incrementar() {
		puerto++;
	}
	public int getPuerto() {
		return puerto;
	}
}

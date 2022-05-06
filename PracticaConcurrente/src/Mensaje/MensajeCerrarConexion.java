package Mensaje;

import java.io.Serializable;

public class MensajeCerrarConexion extends Mensaje implements Serializable{

	public MensajeCerrarConexion(int tipo, String or, String des) {
		super(tipo, or, des);
	}

}

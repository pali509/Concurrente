package Mensaje;

import java.util.HashMap;
import java.util.List;

public class MensajeConfListaUsuarios extends Mensaje {
	HashMap<String, List<String>> hashMap;
	public MensajeConfListaUsuarios(int tipo, String or, String des, HashMap<String, List<String>> hashMap) {
		super(tipo, or, des);
		this.hashMap = hashMap;
	}

	public HashMap<String, List<String>> getMap() {
		return this.hashMap;
	}
}

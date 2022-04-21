package Mensaje;

import java.io.Serializable;
import java.util.List;

public abstract class Mensaje implements Serializable{ //raíz de la jerarquía de mensajes a diseñar

	private static final long serialVersionUID = 1L;//?????
	
	private int tipo;
	private String origen;
	private String destino;
	
	public Mensaje (int tipo, String or, String des) {
		this.tipo = tipo;
		this.origen = or;
		this.destino = des;
	}
	
	public int getTipo() {
		return this.tipo;
	}
	
	public String getOrigen() {
		return this.origen;
	}
	
	public String getDestino() {
		return this.destino;
	}
	public List<String> getRec() {
		return null;
	}
}

package oyente;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class LockP implements Serializable{ //Lock para asegurar que los puertos creados en peer to peer son unicos
	private volatile int[] turnos;
	private volatile int sig;
	private AtomicInteger num;
	public LockP(int n) {
		turnos = new int[n+1];
		for(int i = 0; i < n+1; i++)
			turnos[i] = 0;
		num = new AtomicInteger(1);
		sig = 1;
		turnos = turnos;
	}
	public void take(int id) {
		turnos[id] = num.getAndAdd(1);
		turnos = turnos;
		while(turnos[id] != sig);
	}
	public void release(int id) {
		sig++;
	}
}

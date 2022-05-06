package oyente;

import java.util.concurrent.atomic.AtomicInteger;

public class LockP {
	private int n;
	private volatile int[] turnos;
	private volatile int sig;
	private AtomicInteger num;
	public LockP(int n) {
		this.n = n;
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

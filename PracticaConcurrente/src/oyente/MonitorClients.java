package oyente;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorClients {

	private int counter;
	private final Lock lock;
	private final Condition cond;

	
	public MonitorClients(int counter) {
		this.counter = counter;
		lock = new ReentrantLock();
		cond = lock.newCondition();
	}
	
	public void esperar() throws InterruptedException {
		lock.lock();
		while(counter < 1)
			cond.await();
		counter--;
		lock.unlock();
	}
	public void retomar() {
		lock.lock();
		try {
			counter++;
			if(counter == 1)
				cond.signal(); 
		} finally {
		    lock.unlock();
		}
		
	}
}

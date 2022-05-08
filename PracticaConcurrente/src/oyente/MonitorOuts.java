package oyente;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorOuts {
	int counter;
	private final Lock lock;
	private final Condition cond;
	public MonitorOuts(int counter) {
		this.counter = counter;
		lock = new ReentrantLock();
		cond = lock.newCondition();
	}
	public synchronized void esperar() {
		lock.lock();
		while(counter < 1)
			try {
				cond.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		counter--;
		lock.unlock();
	}
	public synchronized void retomar() {
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

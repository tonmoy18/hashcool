package hashcool;

import java.util.concurrent.locks.*;

public class CoarseHashMap<Tk, Tv> extends BaseHashMap<Tk, Tv> {
	final Lock lock;

	CoarseHashMap(int capacity) {
		super(capacity);
		lock = new ReentrantLock();
	}

	public final void acquire(Tk k) {
		lock.lock();
	}

	public void release(Tk k) {
		lock.unlock();
	}

}

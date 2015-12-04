package hashcool;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.locks.ReentrantLock;

public class RefineableHashSet<T> extends BaseHashSet<T> {

	AtomicMarkableReference<Thread> owner;
	volatile ReentrantLock[] locks;

	public RefineableHashSet(int capacity) {
		super(capacity);
		locks = new ReentrantLock[capacity];
		for (int i = 0; i < capacity; i++) {
			locks[i] = new ReentrantLock();
		}
		owner = new AtomicMarkableReference<Thread>(null, false);
	}

	public void acquire(T x) {
		boolean[] mark = { true };
		Thread me = Thread.currentThread();
		Thread who;
		while (true) {
			do {
				who = owner.get(mark);
			} while (mark[0] && who != me);
			ReentrantLock[] oldLocks = locks;
			ReentrantLock oldLock = oldLocks[Math.abs(x.hashCode() % oldLocks.length)];
			oldLock.lock();
			who = owner.get(mark);
			if ((!mark[0] || who == me) && locks == oldLocks) {
				return;
			} else {
				oldLock.unlock();
			}
		}
	}

	public void release(T x) {
		locks[Math.abs(x.hashCode() % locks.length)].unlock();
	}
}

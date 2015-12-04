package hashcool;

import java.util.concurrent.locks.*;
import java.util.EmptyStackException;

public class LockBasedStack<T> {

	public class Node {
		public T value;
		public Node next;

		public Node(T value) {
			this.value = value;
			next = null;
		}
	}

	ReentrantLock lock = new ReentrantLock();
	Node top = new Node(null);

	public void push(T value) {
		Node node = new Node(value);
		lock.lock();
		try {
			node.next = top;
			top = node;
			lock.unlock();
		} finally {
			lock.unlock();
		}
	}

	public T pop() throws EmptyStackException {
		lock.lock();
		try {
			if (top == null) {
				throw new EmptyStackException();
			}
			T value = top.value;
			top = top.next;
			lock.unlock();
			return value;
		} finally {
			lock.unlock();
		}
	}

}

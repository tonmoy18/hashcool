package hashcool;

import java.util.*;

public abstract class BaseHashSet<T> implements HashSet<T> {
	protected ArrayList<T>[] table;
	protected int setSize;
	protected int totalNodeSize = 0;

	@SuppressWarnings("unchecked")
	public BaseHashSet(int capacity) {
		table = (ArrayList<T>[]) new ArrayList[capacity];
		setSize = 0;
		for (int i = 0; i < capacity; i++) {
			table[i] = new ArrayList<T>();
		}
	}

	public void acquire(T x) {

	}

	public void release(T x) {

	}

	public boolean contains(T x) {
		// Acquiring the Lock for node X
		acquire(x);
		try {
			int myBucket = Math.abs(x.hashCode() % table.length);
			return table[myBucket].contains(x);
		} finally {
			release(x);
		}
	}

	public boolean add(T x) {
		boolean result = false;
		acquire(x);
		try {
			int myBucket = Math.abs(x.hashCode() % table.length);
			if (table[myBucket].contains(x) != true) {
				table[myBucket].add(x);
				result = true;
				totalNodeSize++;
			}
		} finally {
			release(x);
		}
		return result;

	}

	public boolean remove(T x) {
		boolean result = false;
		acquire(x);
		try {
			int myBucket = Math.abs(x.hashCode() % table.length);
			if (table[myBucket].contains(x) == true) {
				table[myBucket].remove(x);
				result = true;
				totalNodeSize--;
			}
		} finally {
			release(x);
		}
		return result;
	}

} // Class

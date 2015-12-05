package hashcool;

public abstract class BaseHashMap<Tk, Tv> implements HashMap<Tk, Tv> {
	protected LinkedList<Tk, Tv>[] table;
	protected int setSize;
	protected int totalNodeSize = 0;

	@SuppressWarnings("unchecked")
	public BaseHashMap(int capacity) {
		table = (LinkedList<Tk, Tv>[]) new LinkedList[capacity];
		setSize = 0;
		for (int i = 0; i < capacity; i++) {
			table[i] = new LinkedList<Tk, Tv>();
		}
	}

	public void acquire(Tk k) {

	}

	public void release(Tk k) {

	}

	public boolean contains(Tk k) {
		// Acquiring the Lock for node X
		acquire(k);
		try {
			int myBucket = Math.abs(k.hashCode() % table.length);
			return table[myBucket].contains(k);
		} finally {
			release(k);
		}
	}

	public boolean add(Tk k, Tv v) {
		boolean result = false;
		acquire(k);
		try {
			int myBucket = Math.abs(k.hashCode() % table.length);
			if (table[myBucket].contains(k) != true) {
				table[myBucket].add(k, v);
				result = true;
				totalNodeSize++;
			}
		} finally {
			release(k);
		}
		return result;

	}

	public boolean remove(Tk k) {
		boolean result = false;
		acquire(k);
		try {
			int myBucket = Math.abs(k.hashCode() % table.length);
			if (table[myBucket].contains(k) == true) {
				table[myBucket].remove(k);
				result = true;
				totalNodeSize--;
			}
		} finally {
			release(k);
		}
		return result;
	}

	public Tv getVal(Tk k) {
		acquire(k);
		try {
			int myBucket = Math.abs(k.hashCode() % table.length);
			return table[myBucket].getVal(k);
		} finally {
			release(k);
		}
	}

} // Class

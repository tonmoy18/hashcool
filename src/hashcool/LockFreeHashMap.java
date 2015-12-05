package hashcool;

public class LockFreeHashMap<Tk, Tv> implements HashMap<Tk, Tv> {
	protected LockFreeList<Tk, Tv>[] table;
	protected int setSize;
	protected int totalNodeSize = 0;

	@SuppressWarnings("unchecked")
	public LockFreeHashMap(int capacity) {
		table = (LockFreeList<Tk, Tv>[]) new LockFreeList[capacity];
		setSize = 0;
		for (int i = 0; i < capacity; i++) {
			table[i] = new LockFreeList<Tk, Tv>();
		}
	}

	public boolean contains(Tk x) {
		int myBucket = Math.abs(x.hashCode() % table.length);
		return table[myBucket].contains(x);
	}

	public boolean add(Tk x, Tv v) {
		int myBucket = Math.abs(x.hashCode() % table.length);
		if (table[myBucket].contains(x) != true) {
			table[myBucket].add(x, v);
			totalNodeSize++;
			return true;
		}
		return false;
	}

	public boolean remove(Tk x) {
		int myBucket = Math.abs(x.hashCode() % table.length);
		if (table[myBucket].contains(x) == true) {
			table[myBucket].remove(x);
			totalNodeSize--;
			return true;
		}
		return false;
	}

	@Override
	public Tv getVal(Tk x) {
		int myBucket = Math.abs(x.hashCode() % table.length);
		return table[myBucket].getVal(x);	
	}

}

package hashcool;

public class LockFreeHashMap<T> {
	protected LockFreeList<T>[] table;
	protected int setSize;
	protected int totalNodeSize = 0;

	@SuppressWarnings("unchecked")
	public LockFreeHashMap(int capacity) {
		table = (LockFreeList<T>[]) new LockFreeList[capacity];
		setSize = 0;
		for (int i = 0; i < capacity; i++) {
			table[i] = new LockFreeList<T>();
		}
	}

	public boolean contains(T x) {
		int myBucket = Math.abs(x.hashCode() % table.length);
		return table[myBucket].contains(x);
	}

	public boolean add(T x) {
		int myBucket = Math.abs(x.hashCode() % table.length);
		if (table[myBucket].contains(x) != true) {
			table[myBucket].add(x);
			totalNodeSize++;
			return true;
		}
		return false;
	}

	public boolean remove(T x) {
		int myBucket = Math.abs(x.hashCode() % table.length);
		if (table[myBucket].contains(x) == true) {
			table[myBucket].remove(x);
			totalNodeSize--;
			return true;
		}
		return false;
	}

}

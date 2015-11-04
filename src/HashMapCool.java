
class Entry {
	int val;
	public Entry(int v) {
		val = v;
	}
}

public class HashMapCool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LockFreeList<Integer> l = new LockFreeList<Integer>();
	 	System.out.println(l.add(1));
	 	System.out.println(l.contains(1));
	 	System.out.println(l.remove(1));
	 	System.out.println(l.contains(1));
	}
}

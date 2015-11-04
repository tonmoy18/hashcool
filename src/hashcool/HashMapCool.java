package hashcool;

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
//		LockFreeList<Integer> l = new LockFreeList<Integer>();
//	 	System.out.println(l.add(1));
//	 	System.out.println(l.contains(1));
//	 	System.out.println(l.remove(1));
//	 	System.out.println(l.contains(1));
		
		LockFreeHashMap<Integer> h = new LockFreeHashMap<Integer>(32);
		
		h.add(1);
		h.add(2);
		h.add(3);
		h.add(4);
		
		h.contains(2);
		h.contains(5);
		
		
	}
}

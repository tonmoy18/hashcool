package hashcool;

public class HashMapCool {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		int capacity = 10;
		
		CoarseHashMap<String, Integer> coarseHashMap = new CoarseHashMap<String, Integer>(capacity);
		
		coarseHashMap.add("A", 1);
		
		// you need to remove old value before adding new value!!!
		coarseHashMap.remove("A");
		coarseHashMap.add("A", 2);
		
		System.out.println(coarseHashMap.getVal("A"));
		

	}
}

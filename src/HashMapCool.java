
public class HashMapCool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CoarseHashSet<Integer> hashTable = new CoarseHashSet<Integer>(25);
		hashTable.add(5);
		
		System.out.print(hashTable.contains(5));
	}
	

}

package hashcool;

public class HashMapCool {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {

		System.out.println("This is the main program!");
		
		CoarseHashSet<Integer> coarseHashSet = new CoarseHashSet<Integer>(100000);
		RefineableHashSet<Integer> refineableHashSet = new RefineableHashSet<Integer>(100000);
		LockFreeHashSet<Integer> lockFreeHashSet = new LockFreeHashSet<Integer>(100000);

		
		RandomBenchmark randomBenchmark = new RandomBenchmark();
		randomBenchmark.setNThreds(5);
		
		System.out.println(randomBenchmark.runBenchmark(coarseHashSet) + " nano seconds");
		System.out.println(randomBenchmark.runBenchmark(refineableHashSet) + " nano seconds");
		System.out.println(randomBenchmark.runBenchmark(lockFreeHashSet) + " nano seconds");
		

	}
}

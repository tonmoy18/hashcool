package hashcool;

public class HashMapCool {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("This is the main program!");
		
		int[] caps = {666666};
		int[] tn = {7};
		
		
		for (int cap_i = 0; cap_i < caps.length; cap_i++) {
			for (int tn_i = 0; tn_i < tn.length; tn_i++) {
				CoarseHashMap<Integer, Integer> coarseHashSet = new CoarseHashMap<Integer, Integer>(caps[cap_i]);
				RefineableHashMap<Integer, Integer> refineableHashSet = new RefineableHashMap<Integer, Integer>(caps[cap_i]);
				LockFreeHashMap<Integer, Integer> lockFreeHashSet = new LockFreeHashMap<Integer, Integer>(caps[cap_i]);
//				STMHashMap<Integer, Integer> stmhashmap = new STMHashMap<Integer, Integer>();

				
				RandomBenchmark randomBenchmark = new RandomBenchmark();
				randomBenchmark.setNThreds(tn[tn_i]);
				
//				System.out.println("Lock Free: " + randomBenchmark.runBenchmark(lockFreeHashSet)/1000 + " milli seconds");
//				System.out.println("capacity: " + caps[cap_i] + " \n" + "number of threads: " + tn[tn_i]);
//				System.out.println("Coarse: " + randomBenchmark.runBenchmark(coarseHashSet)/1000 + " milli seconds");
				System.out.println("Fine: " + randomBenchmark.runBenchmark(refineableHashSet)/1000 + " milli seconds");
//				System.out.println("STM: " + randomBenchmark.runBenchmark(stmhashmap)/1000 + " milli seconds");
				
				System.out.println();
			}
		}
		
		
		
		

	}
}

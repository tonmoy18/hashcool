package hashcool;

public class HashMapCool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("This is the main program!");
		
		Benchmark benchmark = new Benchmark();
		
		benchmark.runBenchmarks();
		
		System.out.println("coarseHashSequentialAddTime= " + benchmark.coarseHashSequentialAddTime);
		System.out.println("lockFreeHashSequentialAddTime= " + benchmark.lockFreeHashSequentialAddTime);
		System.out.println("coarseHashRandomAddTime= " + benchmark.coarseHashRandomAddTime);
		System.out.println("lockFreeHashRandomAddTime= " + benchmark.lockFreeHashRandomAddTime);
		System.out.println("coarseHashSequentialRemoveTime= " + benchmark.coarseHashSequentialRemoveTime);
		System.out.println("lockFreeHashSequentialRemoveTime= " + benchmark.lockFreeHashSequentialRemoveTime);
		
		
	}
}

package hashcool;

import java.util.Random;
import java.util.concurrent.*;

public class RandomBenchmark {
	int runsForAvg = 1000;
	
	int maxData = 1000000;
	int nThreads =  5;
	
	int containsPercent = 90;
	int addPercent = 8;
	int removePercent = 2;
	
	Random rand = new Random(System.nanoTime());
	
	void setNThreds(int n) {
		nThreads = n;
	}
	
	long runBenchmark(final HashMap<Integer, Integer> hashMap) throws InterruptedException {
		// initialize hashSet
		for (int i = 0; i < maxData; i++) {
			hashMap.add(i, 0);
		}
		
		
		long totalTime = 0;
		
		
		// http://stackoverflow.com/questions/10092251/creating-dynamic-number-of-threads-concurrently
		
		
		// run 10 times (then avg the results)
		for (int i = 0; i < runsForAvg; i++) {
			
			ExecutorService threadPool = Executors.newFixedThreadPool(nThreads);
			
			long startTime = System.nanoTime();
			for (int t = 0; t < nThreads; t++) {
			
				threadPool.submit(new Runnable() {

					@Override
					public void run() {
						// choose contains, add or remove randomly based on percentage 
						int p = rand.nextInt(100);
						if (p < containsPercent) {
							// see if random number from 0 to maxData is in hash
							hashMap.contains(rand.nextInt(maxData));
						} else if (p < containsPercent + addPercent) {
							// add random integer from maxData to MAX_INT in hash
							hashMap.add(rand.nextInt(Integer.MAX_VALUE - maxData) + maxData, 0);
						} else {
							// remove random number from 0 to maxData from hash
							hashMap.remove(rand.nextInt(maxData));
						}
					}
				});
			}
			threadPool.shutdown();
			threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
			long timeTaken = System.nanoTime() - startTime;
			totalTime += timeTaken;
		}
		
		return totalTime / runsForAvg;
		
	}
}

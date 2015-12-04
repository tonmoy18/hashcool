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
	
	Random rand = new Random();
	
	void setNThreds(int n) {
		nThreads = n;
	}
	
	long runBenchmark(final HashSet<Integer> hashSet) throws InterruptedException {
		// initialize hashSet
		for (int i = 0; i < maxData; i++) {
			hashSet.add(i);
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
							hashSet.contains(rand.nextInt(maxData));
						} else if (p < containsPercent + addPercent) {
							// add random integer from maxData to MAX_INT in hash
							hashSet.contains(rand.nextInt(Integer.MAX_VALUE - maxData) + maxData);
						} else {
							// remove random number from 0 to maxData from hash
							hashSet.remove(rand.nextInt(maxData));
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

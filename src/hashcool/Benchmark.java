package hashcool;

import java.util.Random;

public class Benchmark {
	int numKeys = 1000;
	int numBuckets = 50;
	
	Random rand = new Random();
		
	long coarseHashSequentialAddTime;
	long lockFreeHashSequentialAddTime;
	long coarseHashRandomAddTime;
	long lockFreeHashRandomAddTime;
	long coarseHashSequentialRemoveTime;
	long lockFreeHashSequentialRemoveTime;
	
	public void runBenchmarks() {
		long time;
		
		time = System.currentTimeMillis();
		coarseHashSequentialAdd(new CoarseHashSet<Integer>(numBuckets));
		coarseHashSequentialAddTime = System.currentTimeMillis() - time;
		
		time = System.currentTimeMillis();
		lockFreeHashSequentialAdd(new LockFreeHashMap<Integer>(numBuckets));
		lockFreeHashSequentialAddTime = System.currentTimeMillis() - time;

		time = System.currentTimeMillis();
		coarseHashRandomAdd(new CoarseHashSet<Integer>(numBuckets));
		coarseHashRandomAddTime = System.currentTimeMillis() - time;

		time = System.currentTimeMillis();
		lockFreeHashRandomAdd(new LockFreeHashMap<Integer>(numBuckets));
		lockFreeHashRandomAddTime = System.currentTimeMillis() - time;
		
		CoarseHashSet<Integer> coarseHashSet = new CoarseHashSet<Integer>(numBuckets);
		coarseHashSequentialAdd(coarseHashSet);
		time = System.currentTimeMillis();
		coarseHashSequentialRemove(coarseHashSet);
		coarseHashSequentialRemoveTime = System.currentTimeMillis() - time;
		
		LockFreeHashMap<Integer> lockFreeHashMap = new LockFreeHashMap<Integer>(numBuckets);
		lockFreeHashSequentialAdd(lockFreeHashMap);
		time = System.currentTimeMillis();
		lockFreeHashSequentialRemove(lockFreeHashMap);
		lockFreeHashSequentialRemoveTime = System.currentTimeMillis() - time;
		

	}


	void coarseHashSequentialAdd(CoarseHashSet<Integer> coarseHashSet) {
		for (int i = 0; i < numKeys; i++) {
			coarseHashSet.add(i);
		}
	}
	
	void lockFreeHashSequentialAdd(LockFreeHashMap<Integer> lockFreeHashMap) {
		for (int i = 0; i < numKeys; i++) {
			lockFreeHashMap.add(i);
		}
	}
	
	void coarseHashRandomAdd(CoarseHashSet<Integer> coarseHashSet) {
		for (int i = 0; i < numKeys; i++) {
			coarseHashSet.add(rand.nextInt());
		}
	}
	
	void lockFreeHashRandomAdd(LockFreeHashMap<Integer> lockFreeHashMap) {
		for (int i = 0; i < numKeys; i++) {
			lockFreeHashMap.add(rand.nextInt());
		}
	}
	
	private void coarseHashSequentialRemove(CoarseHashSet<Integer> coarseHashSet) {
		for (int i = 0; i < numKeys; i++) {
			coarseHashSet.remove(i);
		}	
	}

	private void lockFreeHashSequentialRemove(LockFreeHashMap<Integer> lockFreeHashMap) {
		for (int i = 0; i < numKeys; i++) {
			lockFreeHashMap.remove(i);
		}
		
	}
	
}

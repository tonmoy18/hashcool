package hashcool;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;


public class HashMapCool {

	/**
	 * @param args
	 * @throws InterruptedException
	 */

	public static void randomIntTest() throws InterruptedException {
		int cap = 500000;
		int tn = 7;

		CoarseHashMap<Integer, Integer> coarseHashSet = new CoarseHashMap<Integer, Integer>(cap);
		RefineableHashMap<Integer, Integer> refineableHashSet = new RefineableHashMap<Integer, Integer>(cap);
		LockFreeHashMap<Integer, Integer> lockFreeHashSet = new LockFreeHashMap<Integer, Integer>(cap);
		STMHashMap<Integer, Integer> stmhashmap = new STMHashMap<Integer, Integer>();

		RandomBenchmark randomBenchmark = new RandomBenchmark();
		randomBenchmark.setNThreds(tn);

		System.out.println("Random Int Lock Free: " + randomBenchmark.runBenchmark(lockFreeHashSet) / 1000 + " milli seconds");
//		System.out.println("Random Int Coarse: " + randomBenchmark.runBenchmark(coarseHashSet) / 1000 + " milli seconds");
//		System.out.println("Random Int Fine: " + randomBenchmark.runBenchmark(refineableHashSet) / 1000 + " milli seconds");
//		System.out.println("Random IntSTM: " + randomBenchmark.runBenchmark(stmhashmap) / 1000 + " milli seconds");

		System.out.println();
	}

	public static void wordCountTedst() throws InterruptedException {

		int capacity = 10;
		int maxDataSize = 1000000;
		Random rand = new Random(System.nanoTime());

		CoarseHashMap<String, Integer> hashMap = new CoarseHashMap<String, Integer>(capacity);

		hashMap.add("A", 1);
		hashMap.add("A", 2);

		LockFreeHashMap<String, Integer> lockFreeHashMap = new LockFreeHashMap<String, Integer>(capacity);
		lockFreeHashMap.add("What's", 1);
		lockFreeHashMap.add("going", 7);
		lockFreeHashMap.add("on", 2);
		if (lockFreeHashMap.contains("on") == true) {
			lockFreeHashMap.remove("on");
			lockFreeHashMap.add("on", 5);
		}

		// lockFreeHashMap.add("on", 5);

		System.out.println(hashMap.getVal("A"));
		System.out.println(lockFreeHashMap.getVal("on"));
		System.out.println("Int Max Value - maxData = " + rand.nextInt(Integer.MAX_VALUE - maxDataSize));

		System.out.println("Rand = " + (rand.nextInt(10 - 0 + 1) + 0));

		// The name of the file to open.
		String fileName = "dictionary.txt";
		// String fileName = "dictionary1.txt";

		// This will reference one line at a time
		String line = null;

		try {
			// FileReader reads text files in the default encoding.
			FileReader myFile = new FileReader(fileName);

			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader = new BufferedReader(myFile);

			// Vector<Character> vec = new Vector<Character>();
			int wordCount = 0;
			String delims = "[ .,?!]";
			// Initializing the HashSet
			while ((line = bufferedReader.readLine()) != null) {

				// System.out.println(line);
				// String lineWithoutPunc = line.replaceAll("\\p{Punct}+","");
				String[] words = line.split(delims);

				for (int i = 0; i <= words.length - 1; i++) {

					// Check if this word is already in the vector list
					if (hashMap.contains(words[i]) == true) {
						// If so, gets its old value count & re-add the word to
						// update count
						int oldValue = hashMap.getVal(words[i]);
						int newValue = oldValue + 1;
						hashMap.add(words[i], newValue);
						System.out.println("Word = " + words[i] + " & ValueCnt = " + newValue);

					} else {
						// Increment the wordCount and check if we have reached
						// the max data count
						wordCount++;
						if (wordCount <= maxDataSize) {
							// Its a new word so add it with the initial count
							// value of 1
							hashMap.add(words[i], 1);
							System.out.println("Word = " + words[i] + " & ValueCnt = 1");
						}
					}

					// sleep 2 seconds
					Thread.sleep(2000);
					// TimeUnit.SECONDS.sleep(2);
					// hashMap.add(rand.nextInt(Integer.MAX_VALUE -
					// maxDataSize), words[i]);
				}
			}

			System.out.println("End");
			// Always close files.
			bufferedReader.close();
		} catch (IOException ex) {
			System.out.println("Unable to open file '" + fileName + "'");
		}
	}

	public static void main(String[] args) throws InterruptedException {

		randomIntTest();
		wordCountTedst();
		
	}
}

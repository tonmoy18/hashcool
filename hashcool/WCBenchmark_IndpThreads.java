package hashcool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class WCBenchmark_IndpThreads {
	int maxDataSize;		//= 1000000;
	int nThreads 			 =  5;
	
	int containsPercent = 100;
	//int addPercent = 8;
	int removePercent = 50;
	Random rand = new Random(System.nanoTime());
	private AtomicInteger wordCount = new AtomicInteger(0);
	
	
	void setNThreds(int n) {
		nThreads = n;
	}
	
	void setMaxDataSize(int size) {
		maxDataSize = size;
	}
	
	Vector<String> runBenchmark() throws InterruptedException {

		long totalTime = 0;
			
		// http://stackoverflow.com/questions/10092251/creating-dynamic-number-of-threads-concurrently
		//http://stackoverflow.com/questions/12191029/running-two-independent-tasks-simultaneously-using-threads
		
	
		

			
			String dir = Paths.get(".").toAbsolutePath().normalize().toString();
			
			boolean finishFlag = false;
			 // The name of the file to open.
			//"dicExtra.txt";
			String fileName = "dictionary_200k.txt";
	        //String fileName = "dictionary1.txt";

	        // This will reference one line at a time
	        String line = null;
	        Vector<String> vec = new Vector<String>();
	        
	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader myFile = new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = new BufferedReader(myFile);

	            String delims = "[ .,?!]";
	            
	            // Initializing the HashSet
	            while((line = bufferedReader.readLine()) != null) {
	            	
	                //System.out.println(line);
	            	//String lineWithoutPunc = line.replaceAll("\\p{Punct}+","");
	                String[] words = line.split(delims);

	                for(int i=0; i <= words.length -1; i++){	      
	                	vec.add(words[i]);
	                }
	            }
	            // Always close files.
	            bufferedReader.close();       
	        }	            
	        catch(IOException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                fileName + "'");                
	        }
	        
		   
        	//hashMap.add(rand.nextInt(Integer.MAX_VALUE - maxDataSize), words[i]);		                	
		
			// choose contains or remove randomly based on percentage 
/*						int p = rand.nextInt(100);
						if (p < containsPercent) {
							// see if random number from 0 to maxDataSize is in hash
							hashMap.contains(rand.nextInt(maxDataSize));
						} else if (p < containsPercent + addPercent) {
							// add random integer from maxDataSize to MAX_INT in hash
							hashMap.add(rand.nextInt(Integer.MAX_VALUE - maxDataSize) + maxDataSize, 0);
						} else {
							// remove random number from 0 to maxDataSize from hash
							hashMap.remove(rand.nextInt(maxDataSize));
						}
*/						       


		//System.out.println("Total Time for adding = " + totalTime/runsForAvg);
		//return totalTime / runsForAvg;
		return vec;
	}
	
	public int runBenchMarkPart2(Vector<String> vec, final HashMap<String, Integer> hashMap, boolean isLockFree) throws InterruptedException
	{
		//Splitting up the words evenly among 5 threads
        int totalWords = vec.size();
	    int wordsPerThread = totalWords/nThreads;
	    int remaindingWords = totalWords%nThreads;
	    //System.out.println("Total Words = " +totalWords+ "....per thread = " + wordsPerThread );
	    
	    //Thread 1
	    int startIdx1 =0;
	    int endIdx1 = wordsPerThread-1;
	    
	    //Thread2
	    int stIdx2 = endIdx1 +1;
	    int endIdx2 = (wordsPerThread*2) -1;
	    
	    //Thread3
	    int stIdx3 = endIdx2 +1;
	    int endIdx3 = (wordsPerThread*3) -1;
	    
	    //Thread4
	    int stIdx4 = endIdx3 +1;
	    int endIdx4 = (wordsPerThread*4) -1;
	    
	    //Thread5 gives the remainder workload too
	    int stIdx5 = endIdx4 +1;
	    int endIdx5 = (wordsPerThread*5) -1 + remaindingWords; //Should equal totalWords

		 //if(finishFlag == true){		    
		    
			new Thread(new Runnable() {
			    public void run() {
			        //System.out.println("Thread 1");
			        for(int i =startIdx1; i <= endIdx1; i++)
			        {
			        	//Check if this word is already in the vector list
			        	String currWord = vec.elementAt(i);
	            		if(hashMap.contains(currWord) == true)
	            		{           			
	            			//If so, gets its old value count & re-add the word to update count
	            			int oldValue = hashMap.getVal(currWord);
	            			int newValue = oldValue +1;
	            			if(isLockFree == false)
	            			{
	            				hashMap.add(currWord, newValue);
	            			}else
	            			{
	            				hashMap.remove(currWord);
	            				hashMap.add(currWord, newValue);
	            			}
	            				
	            			//System.out.println("T1 Word = " + currWord + " & ValueCnt = " + newValue + " .....i ="+i);
	            			
	            		}
	            		else{
	            			//Increment the wordCount and check if we have reached the max data count 			                	
	            			wordCount.getAndIncrement();			    
	            			if(wordCount.get() <= maxDataSize){
	                			//Its a new word so add it with the initial count value of 1
	                			hashMap.add(vec.elementAt(i), 1);
	                		//	System.out.println("T1 Word = " + currWord + " & ValueCnt = 1" + " .....i ="+i);
	            			}
	            		} //else	     
			        	
			        }//for  
			    }//run
			    
			}).start();

		    
			new Thread(new Runnable() {
			    public void run() {
			        //System.out.println("Thread 2");
			        for(int i =stIdx2; i <=endIdx2; i++)
			        {
			        	//Check if this word is already in the vector list
	            		if(hashMap.contains(vec.elementAt(i)) == true)
	            		{           			
	            			//If so, gets its old value count & re-add the word to update count
	            			int oldValue = hashMap.getVal(vec.elementAt(i));
	            			int newValue = oldValue +1;
	            			if(isLockFree == false)
	            			{
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}else
	            			{
	            				hashMap.remove(vec.elementAt(i));
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}
	            			//System.out.println("T2 Word = " + vec.elementAt(i) + " & ValueCnt = " + newValue + " .....i ="+i);
	            			
	            		}
	            		else{
	            			//Increment the wordCount and check if we have reached the max data count 			                	
	            			wordCount.getAndIncrement();			    
	            			if(wordCount.get() <= maxDataSize){
	                			//Its a new word so add it with the initial count value of 1
	                			hashMap.add(vec.elementAt(i), 1);
	                		//	System.out.println("T2 Word = " + vec.elementAt(i)+ " & ValueCnt = 1" + " .....i ="+i);
	            			}
	            		} //else	     
			        	
			        }//for  
			    }//run
			    
			}).start();
			
			//startIdx = endIdx+1;
		    //endIdx = endIdx+wordsPerThread;
			new Thread(new Runnable() {
			    public void run() {
			        //System.out.println("Thread 3");
			        for(int i =stIdx3; i <= endIdx3; i++)
			        {
			        	//Check if this word is already in the vector list
	            		if(hashMap.contains(vec.elementAt(i)) == true)
	            		{           			
	            			//If so, gets its old value count & re-add the word to update count
	            			int oldValue = hashMap.getVal(vec.elementAt(i));
	            			int newValue = oldValue +1;
	            			if(isLockFree == false)
	            			{
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}else
	            			{
	            				hashMap.remove(vec.elementAt(i));
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}
	            			//System.out.println("T3 Word = " + vec.elementAt(i) + " & ValueCnt = " + newValue+ " .....i ="+i);
	            			
	            		}
	            		else{
	            			//Increment the wordCount and check if we have reached the max data count 			                	
	            			wordCount.getAndIncrement();			    
	            			if(wordCount.get() <= maxDataSize){
	                			//Its a new word so add it with the initial count value of 1
	                			hashMap.add(vec.elementAt(i), 1);
	                		//	System.out.println("T3 Word = " + vec.elementAt(i)+ " & ValueCnt = 1" + " .....i ="+i);
	            			}
	            		} //else	     
			        	
			        }//for  
			    }//run
			    
			}).start();
	            		
			
			new Thread(new Runnable() {
			    public void run() {
			        //System.out.println("Thread 4");
			        for(int i =stIdx4; i <= endIdx4; i++)
			        {
			        	//Check if this word is already in the vector list
	            		if(hashMap.contains(vec.elementAt(i)) == true)
	            		{           			
	            			//If so, gets its old value count & re-add the word to update count
	            			int oldValue = hashMap.getVal(vec.elementAt(i));
	            			int newValue = oldValue +1;
	            			if(isLockFree == false)
	            			{
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}else
	            			{
	            				hashMap.remove(vec.elementAt(i));
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}
	            		//	System.out.println("T4 Word = " + vec.elementAt(i) + " & ValueCnt = " + newValue+ " .....i ="+i);
	            			
	            		}
	            		else{
	            			//Increment the wordCount and check if we have reached the max data count 			                	
	            			wordCount.getAndIncrement();			    
	            			if(wordCount.get() <= maxDataSize){
	                			//Its a new word so add it with the initial count value of 1
	                			hashMap.add(vec.elementAt(i), 1);
	                	//		System.out.println("T4 Word = " + vec.elementAt(i)+ " & ValueCnt = 1" + " .....i ="+i);
	            			}
	            		} //else	     
			        	
			        }//for  
			    }//run
			    
			}).start();
			
			new Thread(new Runnable() {
			    public void run() {
			        //System.out.println("Thread 5");
			        for(int i =stIdx5; i <= endIdx5; i++)
			        {
			        	//Check if this word is already in the vector list
	            		if(hashMap.contains(vec.elementAt(i)) == true)
	            		{           			
	            			//If so, gets its old value count & re-add the word to update count
	            			int oldValue = hashMap.getVal(vec.elementAt(i));
	            			int newValue = oldValue +1;
	            			if(isLockFree == false)
	            			{
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}else
	            			{
	            				hashMap.remove(vec.elementAt(i));
	            				hashMap.add(vec.elementAt(i), newValue);
	            			}
	            		//	System.out.println("T5 Word = " + vec.elementAt(i) + " & ValueCnt = " + newValue+ " .....i ="+i);
	            			
	            		}
	            		else{
	            			//Increment the wordCount and check if we have reached the max data count 			                	
	            			wordCount.getAndIncrement();			    
	            			if(wordCount.get() <= maxDataSize){
	                			//Its a new word so add it with the initial count value of 1
	                			hashMap.add(vec.elementAt(i), 1);
	                		//	System.out.println("T5 Word = " + vec.elementAt(i)+ " & ValueCnt = 1" + " .....i ="+i);
	            			}
	            		} //else	     
			        	
			        }//for  
			    }//run
			    
			}).start();
				//sleep 2 seconds
				//Thread.sleep(2000);
		    //}
		return wordCount.get();
	}
	
	public static void main(String[] args) {
		int runsForAvg = 10;
		//Capacity sizes: 100, 1000, 5000, 10000
		int maxSize = 10000; 
		
		WCBenchmark_IndpThreads bmObj = new WCBenchmark_IndpThreads();
		CoarseHashMap<String, Integer> coarseHashMap = new CoarseHashMap<String, Integer>(maxSize);
		RefineableHashMap<String, Integer> fineHashMap = new RefineableHashMap<String, Integer>(maxSize);
		LockFreeHashMap<String, Integer> lockFreeHashMap = new LockFreeHashMap<String, Integer>(maxSize);
		
		//bmObj.setNThreds(5);
		bmObj.setMaxDataSize(maxSize);
		long execTime =0;
		long totalTime =0;
		int uniqueWordCnt1 =0;
		try {
			// run 10 times (then avg the results)
			for (int iRuns = 0; iRuns < runsForAvg; iRuns++) {
				long startTimer = System.nanoTime();
				//System.out.println("Busy ... loop iteration #" + iRuns + " out of " + runsForAvg);
				
				Vector<String> vec = bmObj.runBenchmark();

				//uniqueWordCnt1 = bmObj.runBenchMarkPart2(vec, coarseHashMap, true);
				//uniqueWordCnt1 = bmObj.runBenchMarkPart2(vec, fineHashMap, true);
				//uniqueWordCnt1 = bmObj.runBenchMarkPart2(vec, lockFreeHashMap, true);
				long timeTaken = System.nanoTime() - startTimer;
				totalTime += timeTaken;
			}
			
			execTime = totalTime / runsForAvg;
			System.out.println("Total Time for Adding Words = " + execTime +" ns.");
			execTime = TimeUnit.MILLISECONDS.convert(execTime, TimeUnit.NANOSECONDS);
			System.out.println("Total Time for Adding Words = " + execTime +" ms.");
			System.out.println("Unique Word Count = " + uniqueWordCnt1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
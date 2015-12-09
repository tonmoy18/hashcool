package hashcool;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.*;

public class WordCountBenchmark {
	int runsForAvg = 1000;
	int maxDataSize;		//= 1000000;
	int nThreads;			// =  5;
	
	int containsPercent = 100;
	//int addPercent = 8;
	int removePercent = 50;
	Random rand = new Random(System.nanoTime());
	
	
	void setNThreds(int n) {
		nThreads = n;
	}
	
	void setMaxDataSize(int size) {
		maxDataSize = size;
	}
	
	long runBenchmark(final HashMap<String, Integer> hashMap) throws InterruptedException {

		long totalTime = 0;
			
		// http://stackoverflow.com/questions/10092251/creating-dynamic-number-of-threads-concurrently
		
		// run 10 times (then avg the results)
		for (int iRuns = 0; iRuns < runsForAvg; iRuns++) {
			
			System.out.println("Busy ... loop iteration #" + iRuns + " out of " + runsForAvg);
			long startTimer = System.nanoTime();
			
			String dir = Paths.get(".").toAbsolutePath().normalize().toString();
			
			 // The name of the file to open.
			String fileName = "dictionary.txt";
	        //String fileName = "dictionary1.txt";

	        // This will reference one line at a time
	        String line = null;
	        
	        try {
	            // FileReader reads text files in the default encoding.
	            FileReader myFile = new FileReader(fileName);

	            // Always wrap FileReader in BufferedReader.
	            BufferedReader bufferedReader = new BufferedReader(myFile);

	            //Vector<Character> vec = new Vector<Character>();
	            int wordCount = 0;
	            String delims = "[ .,?!]";
	            
	            // Initializing the HashSet
	            while((line = bufferedReader.readLine()) != null) {
	            	
	                //System.out.println(line);
	            	//String lineWithoutPunc = line.replaceAll("\\p{Punct}+","");
	                String[] words = line.split(delims);

	                for(int i=0; i <= words.length -1; i++){	      
	                	
	            		//Check if this word is already in the vector list
	            		if(hashMap.contains(words[i]) == true)
	            		{           			
	            			//If so, gets its old value count & re-add the word to update count
	            			int oldValue = hashMap.getVal(words[i]);
	            			int newValue = oldValue +1;
	            			hashMap.add(words[i], newValue);
	            			//System.out.println("Word = " + words[i] + " & ValueCnt = " + newValue);
	            			
	            		}
	            		else{
	            			//Increment the wordCount and check if we have reached the max data count 			                	
	            			wordCount++;			    
	            			if(wordCount <= maxDataSize){
	                			//Its a new word so add it with the initial count value of 1
	                			hashMap.add(words[i], 1);
	                			//System.out.println("Word = " + words[i] + " & ValueCnt = 1");
	            			}
	            		}	            		
	        			//sleep 2 seconds
	        			//Thread.sleep(2000);
	                	//hashMap.add(rand.nextInt(Integer.MAX_VALUE - maxDataSize), words[i]);		                	
	                }
	            }   

	            // Always close files.
	            bufferedReader.close();         
	        }
	        catch(IOException ex) {
	            System.out.println(
	                "Unable to open file '" + 
	                fileName + "'");                
	        } /*catch (InterruptedException e) {
				e.printStackTrace();
			}*/

					
			
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

			long timeTaken = System.nanoTime() - startTimer;
			totalTime += timeTaken;
		} //for runsForAvg
		//System.out.println("Total Time for adding = " + totalTime/runsForAvg);
		return totalTime / runsForAvg;	
	}
	
	
	
	public static void main(String[] args) {
		
		int maxSize = 1000000;
		WordCountBenchmark bmObj = new WordCountBenchmark();
		CoarseHashMap<String, Integer> coarseHashMap = new CoarseHashMap<String, Integer>(maxSize);
		bmObj.setNThreds(5);
		bmObj.setMaxDataSize(maxSize);
		long execTime =0;
		try {
			execTime = bmObj.runBenchmark(coarseHashMap);
			System.out.println("Total Time for Adding Words = " + execTime +" ns.");
			execTime = TimeUnit.MILLISECONDS.convert(execTime, TimeUnit.NANOSECONDS);
			System.out.println("Total Time for Adding Words = " + execTime +" ms.");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}
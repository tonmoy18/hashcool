package hashcool;
import java.util.concurrent.locks.*;

public class CoarseHashSet<T> extends BaseHashSet<T> {
  final Lock lock;
  CoarseHashSet(int capacity) {
    super(capacity);
    lock = new ReentrantLock();
  }
  public final void acquire(T x) {
    lock.lock();
  }
  public void release(T x) {
    lock.unlock();
  }
  
  public boolean contains(T x){
		 //Acquiring the Lock for node X
		 acquire(x);
		 try{
			 int myBucket = x.hashCode() % table.length;
			 return table[myBucket].contains(x);
		 }
		 finally{
			 release(x);
		 }
	 }

	 public boolean add(T x){
		 boolean result = false;
		 acquire(x);
		 try{
			 int myBucket = Math.abs(x.hashCode() % table.length);
			 if(table[myBucket].contains(x) != true)
			 {
				 table[myBucket].add(x);
				 result = true;
				 totalNodeSize++;
			 }
		 }
		 finally
		 {
			 release(x);
		 }
		 return result;

	 }
	 
	 
	 public boolean remove(T x) {
		 boolean result = false;
		 acquire(x);
		 try{
			 int myBucket = Math.abs(x.hashCode() % table.length);
			 if(table[myBucket].contains(x) == true)
			 {
				 table[myBucket].remove(x);
				 result = true;
				 totalNodeSize--;
			 }
		 }
		 finally
		 {
			 release(x);
		 }
		 return result;
	 }
	 

}

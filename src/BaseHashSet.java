import java.util.*;

public class BaseHashSet<T> {
  protected ArrayList<T>[] table;
  protected int setSize;

  @SuppressWarnings("unchecked")
public BaseHashSet(int capacity) {
    table = (ArrayList<T>[])new ArrayList[capacity];
    setSize = 0;
    for (int i = 0; i < capacity; i++) {
      table[i] = new ArrayList<T>();
    }
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
	 boolean result false;
	 acquire(x);
	 try{
		 int myBucket = Math.abs(x.hashCode() % table.length);
		 if(table[muBucket].contains(x) != true)
		 {
			 table[myBucket].add(x);
			 result = true;
			 size++;
		 }
	 }
	 finally
	 {
		 release(x);
	 }
	 return result;
	 
 }

 
 
} //Class

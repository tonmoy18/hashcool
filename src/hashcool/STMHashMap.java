package hashcool;

import akka.stm.*;

public class STMHashMap<Tk, Tv> implements HashMap<Tk, Tv>{
	final TransactionalMap<Tk, Tv> hashmap = new TransactionalMap<Tk, Tv>();
	
	public boolean add(final Tk k, final Tv v) {
		new Atomic() {
		    public Object atomically() {
		    	hashmap.put(k, v);
		        return null;
		    }
		}.execute();
		return true;
	}
	
	public boolean remove(final Tk k) {
		new Atomic() {
		    public Object atomically() {
		    	hashmap.remove(k);
		        return null;
		    }
		}.execute();
		return true;
	}
	
	public boolean contains(final Tk k) {
		return new Atomic<Boolean>() {
		    public Boolean atomically() {
		    	return hashmap.contains(k);
		    }
		}.execute();
	}
	
	public Tv getVal(final Tk k) {
		return new Atomic<Tv>() {
		    public Tv atomically() {
		    	return hashmap.apply(k);
		    }
		}.execute();
	}
	
}



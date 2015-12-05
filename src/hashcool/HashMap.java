package hashcool;

public interface HashMap<Tk, Tv> {
	boolean add(Tk k, Tv v);
	boolean remove(Tk k);
	boolean contains(Tk k);
	Tv getVal(Tk k);
}

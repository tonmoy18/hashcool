import java.util.*;

public abstract class BaseHashSet<T> {
  protected ArrayList<T>[] table;
  protected int setSize;
  protected int totalNodeSize =0;

  @SuppressWarnings("unchecked")
public BaseHashSet(int capacity) {
    table = (ArrayList<T>[])new ArrayList[capacity];
    setSize = 0;
    for (int i = 0; i < capacity; i++) {
      table[i] = new ArrayList<T>();
    }
 }



} //Class

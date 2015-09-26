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
}

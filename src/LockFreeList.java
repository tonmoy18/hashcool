import java.util.concurrent.atomic.*;

public class LockFreeList<T> {

  public class Node {
    public int key;
    public AtomicMarkableReference<Node> next;
    T item;
    Node(T item) {
      this.item = item;
    }
  }

  class Window {
    public Node pred, curr;
    Window(Node p, Node c) {
      pred = p;
      curr = c;
    }
    public Window find(Node head, int key) {
      Node pred = null, curr = null, succ = null;
      boolean[] marked = {false};
      boolean snip;
      retry: while(true) {
        pred = head;
        curr = pred.next.getReference();
        while(true) {
          succ = curr.next.getReference();
          while (marked[0]) {
            snip = pred.next.compareAndSet(curr, succ, false, false);
            if (!snip) continue retry;
            curr = succ;
            succ = curr.next.get(marked);
          }
          if (curr.key >= key)
            return new Window(pred, curr);
          pred = curr;
          curr = succ;
        }
      }
    }

    public boolean add(T item) {
      int key = item.hashCode();
      while(true) {
        Window window = find(head, key);
        Node pred = window.pred, curr = window.curr;
        if (curr.key == key) {
          return false;
        }
        else {
          Node node = new Node(item);
          node.next = new AtomicMarkableReference(curr, false);
          if (pred.next.compareAndSet(curr, node, false, false)) {
            return true;
          }
        }
      }
    }

    public boolean remove(T item) {
      int key = item.hashCode();
      boolean snip;
      while (true) {
        Window window = find(head, key);
        Node pred = window.pred, curr = window.curr;
        if (curr.key != key) {
          return false;
        }
        else {
          Node succ = curr.next.getReference();
          snip = curr.next.compareAndSet(succ, succ, false, false);
          if (!snip)
            continue;
          pred.next.compareAndSet(curr, succ, false, false);
          return true;
        }
      }
    }

    public boolean contains(T item) {
      boolean[] marked = {false};
      int key = item.hashCode();
      Node curr = head;
      while (curr.key < key) {
        curr = curr.next.getReference();
        Node succ = curr.next.get(marked);
      }
      return (curr.key == key && !marked[0])
    }
  }

}

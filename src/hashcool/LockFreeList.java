package hashcool;
import java.util.concurrent.atomic.*;

public class LockFreeList<T> {

	public class Node {
		public int key;
		public AtomicMarkableReference<Node> next;
		T item;

		Node(T item) {
			this.item = item;
			if (item != null) key = item.hashCode();
		}
	}

	Node head;

	public LockFreeList() {
		head = new Node(null);
		Node next = new Node(null);
		Node nextNext = new Node(null);
		head.key = Integer.MIN_VALUE;
		next.key = Integer.MAX_VALUE;
		nextNext.key = Integer.MAX_VALUE;
		
		next.next = new AtomicMarkableReference<Node>(nextNext, false);
		head.next = new AtomicMarkableReference<Node>(next, false);
	}

	class Window {
		public Node pred, curr;

		Window(Node p, Node c) {
			pred = p;
			curr = c;
		}
	}

	public Window find(Node head, int key) {
		Node pred = null, curr = null, succ = null;
		boolean[] marked = { false };
		boolean snip;
		retry: while (true) {
			pred = head;
			curr = pred.next.getReference();
			while (true) {
				succ = curr.next.getReference();
				while (marked[0]) {
					snip = pred.next.compareAndSet(curr, succ, false, false);
					if (!snip)
						continue retry;
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
		while (true) {
			Window window = find(head, key);
			Node pred = window.pred, curr = window.curr;
			if (curr.key == key) {
				return false;
			} else {
				Node node = new Node(item);
				node.next = new AtomicMarkableReference<Node>(curr, false);
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
			} else {
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
		boolean[] marked = { false };
		int key = item.hashCode();
		Node curr = this.head;
		while (curr.key < key) {
			curr = curr.next.get(marked);
		}
		return (curr.key == key && !marked[0]);
	}


}

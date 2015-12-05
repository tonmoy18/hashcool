package hashcool;


public class LinkedList<Tk, Tv> {
	class Node {
		int key;
		Tv val;
		
		Node next;
		
		Node(int k, Tv v) {
			key = k;
			val = v;
		}
	}
	
	Node head;
	
	LinkedList() {
		head = new Node(Integer.MIN_VALUE, null);
		head.next = new Node(Integer.MAX_VALUE, null);
	}
	
	public boolean add(Tk k, Tv v) {
		Node pred, curr;
		int key = k.hashCode();
		pred = head;
		curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		if (key == curr.key) {
			return false;
		}
		else {
			Node node = new Node(key, v);
			node.next = curr;
			pred.next = node;
			return true;
		}
		
	}

	public boolean contains(Tk k) {
		Node curr = head;
		int key = k.hashCode();
		while (curr.key <= key) {
			if (curr.key == key) {
				return true;
			}
			curr=curr.next;
		}
		return false;
		
	}
	
	public boolean remove(Tk k) {
		Node pred, curr;
		int key = k.hashCode();
		pred = head;
		curr = pred.next;
		pred = head;
		curr = pred.next;
		while (curr.key < key) {
			pred = curr;
			curr = curr.next;
		}
		if (key == curr.key) {
			pred.next = curr.next;
			return true;
		}
		else {
			return false;
		}
	}
	
	public Tv getVal(Tk k) {
		Node curr = head;
		int key = k.hashCode();
		while (curr.key <= key) {
			if (curr.key == key) {
				return curr.val;
			}
			curr=curr.next;
		}
		return null;
	}
	
	
	
}

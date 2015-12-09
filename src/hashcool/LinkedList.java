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
		if (key == curr.key && v == curr.val) {
			//System.out.println("Current Key = " + curr.key + "& Value = " + curr.val);
			return false;
		}
		else if(key == curr.key && v != curr.val) {
			//Update the value
			//System.out.println("Current Key = " + curr.key + "& Value = " + curr.val);
			curr.val = v;
			return true;
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
	
	public int containsWithSameValue(Tk k, Tv v) {
		Node curr = head;
		int key = k.hashCode();
		while (curr.key <= key) {
			if (curr.key == key && curr.val == v) {
				return 1;
			}
			if (curr.key == key && curr.val != v) {
				return -1;
			}
			curr=curr.next;
		}
		return 0;
		
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
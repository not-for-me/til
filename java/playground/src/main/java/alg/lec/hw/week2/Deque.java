package alg.lec.hw.week2;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Reference: Coursera
 *
 * Corner cases. Throw a java.lang.NullPointerException if the client attempts
 * to add a null item; throw a java.util.NoSuchElementException if the client
 * attempts to remove an item from an empty deque; throw a
 * java.lang.UnsupportedOperationException if the client calls the remove()
 * method in the iterator; throw a java.util.NoSuchElementException if the
 * client calls the next() method in the iterator and there are no more items to
 * return.
 *
 * Performance requirements. Your deque implementation must support each deque
 * operation in constant worst-case time and use space proportional to the
 * number of items currently in the deque. Additionally, your iterator
 * implementation must support each operation (including construction) in
 * constant worst-case time.
 *
 * @author NFM
 *
 * @param <Item>
 */

public class Deque<Item> implements Iterable<Item> {
	private Node<Item> first;
	private Node<Item> last;

	private class Node<Item> {
		private Item item;
		private Node<Item> before;
		private Node<Item> next;
	}

	// construct an empty deque
	public Deque() {
		first = null;
		last = null;
	}

	// is the deque empty?
	public boolean isEmpty() {
		return first == null;
	}

	// return the number of items on the deque
	public int size() {
		int len = 0;
		Node<Item> traverseNode = first;
		while (traverseNode != null) {
			traverseNode = traverseNode.next;
			len++;
		}
		return len;
	}

	// add the item to the front
	public void addFirst(Item item) {
		validateInputItem(item);

		Node<Item> newNode = new Node<Item>();
		newNode.item = item;
		newNode.before = null;
		newNode.next = first;

		if (first != null) {
			first.before = newNode;
			first = newNode;
		} else {
			first = newNode;
			last = newNode;
		}
	}

	// add the item to the end
	public void addLast(Item item) {
		validateInputItem(item);

		Node<Item> newNode = new Node<Item>();
		newNode.item = item;
		newNode.before = last;
		newNode.next = null;

		if (last != null) {
			last.next = newNode;
			last = newNode;
		} else {
			first = newNode;
			last = newNode;
		}
	}

	private void validateInputItem(Item item) {
		if (item == null)
			throw new NullPointerException();
	}

	// remove and return the item from the front
	public Item removeFirst() {
		validateEmpty();

		Node<Item> removedNode = first;
		Item removedItem = removedNode.item;
		first = removedNode.next;
		removedNode = null;
		if (first == null) {
			last = null;
		} else {
			first.before = null;
		}
		return removedItem;
	}

	// remove and return the item from the end
	public Item removeLast() {
		validateEmpty();

		Node<Item> removedNode = last;
		Item removedItem = removedNode.item;
		last = removedNode.before;
		removedNode = null;
		if (last == null) {
			first = null;
		} else {
			last.next = null;
		}
		return removedItem;
	}

	private void validateEmpty() {
		if (isEmpty())
			throw new NoSuchElementException();
	}

	// return an iterator over items in order from front to end
	public Iterator<Item> iterator() {
		return new ItemIterator<Item>();
	}

	private class ItemIterator<Item> implements Iterator<Item> {
		private Node current = first;

		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public Item next() {
			if (this.hasNext()) {
				Item item = (Item) current.item;
				current = current.next;
				return item;
			} else {
				throw new NoSuchElementException();
			}
		}

		public void remove() {
			throw new UnsupportedOperationException();
		}
	};

	// unit testing
	public static void main(String[] args) {
		int queueNum = 6;
		Deque<Integer> deck = new Deque<Integer>();
		for (int i = 0; i < queueNum; i++) {
			int randNum = new Random().nextInt(101);
			deck.addLast(randNum);
			System.out.print(randNum + " ");
		}

		System.out.println("\n\n### Test Queue ###");
		System.out.println("The Size: " + deck.size());
		System.out.println("dequeue Last:" + deck.removeLast());
		System.out.println("dequeue Last:" + deck.removeLast());
		System.out.println("dequeue Last:" + deck.removeLast());
		System.out.println("IsEmtpy?" + deck.isEmpty());
		System.out.println("dequeue First:" + deck.removeFirst());
		System.out.println("dequeue First:" + deck.removeFirst());
		System.out.println("dequeue First:" + deck.removeFirst());
		System.out.println("IsEmtpy?" + deck.isEmpty());

	}

}


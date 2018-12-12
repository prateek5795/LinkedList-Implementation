/*
 * 	Prateek Sarna - pxs180012
 * 	Sneha Hulivan Girisha - sxh173730
 */

package pxs180012;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {

	// Class Entry holds a single node of the list
	static class Entry<E> extends SinglyLinkedList.Entry<E> {
		Entry<E> prev;

		Entry(E x, Entry<E> nxt, Entry<E> prev) {
			super(x, nxt);
			this.prev = prev;
		}
	}

	public DoublyLinkedList() {
		head = new Entry<T>(null, null, null);
		tail = head;
	}

	private class DLLIterator extends SLLIterator {

		public DLLIterator() {
			super();
		}

		public boolean hasPrev() {
			return ((Entry<T>) cursor).prev != null;
		}

		public T prev() { // Move to the previous element and return it
			cursor = ((Entry<T>) cursor).prev;
			ready = true;
			return cursor.element;
		}

		// Removes the current element (retrieved by the most recent next() or prev())
		// Remove can be called only if next or prev has been called and the element has not been removed
		public void remove() {
			if (!ready) {
				throw new NoSuchElementException();
			}

			// Handle case when tail of a list is deleted
			if (cursor == tail) {
				tail = ((Entry<T>) cursor).prev;
				tail.next = cursor.next;
			}
			else {
				Entry<T> nextEntry = (Entry<T>) cursor.next;
				((Entry<T>) cursor).prev.next = nextEntry;
				nextEntry.prev = ((Entry<T>) cursor).prev;
			}

			cursor = ((Entry<T>) cursor).prev;
			ready = false; // Calling remove again without calling next or prev will result in exception thrown
			size--;
		}

		public void add(T x) { // Adds the specified element before the next element
			Entry<T> newEntry = new Entry<T>(x, null, null);

			if (cursor.next == null) { // When cursor is at the last element, append new element to the list and update tail
				tail.next = newEntry;
				newEntry.prev = (Entry<T>) tail;
				tail = tail.next;
				ready = false;
				size++;
			} else {
				Entry<T> nextEntry = (Entry<T>) cursor.next;
				newEntry.next = nextEntry;
				nextEntry.prev = newEntry;
				cursor.next = newEntry;
				newEntry.prev = (Entry<T>) cursor;
				cursor = ((Entry<T>) cursor).next;
				ready = false;
				size++;
			}
		}

	}

	public Iterator<T> iterator() {
		return new DLLIterator();
	}

	public void add(T x) {
		Entry<T> newEntry = new Entry<T>(x, null, (Entry<T>) tail);
		super.add(newEntry);
	}

	public static void main(String[] args) throws NoSuchElementException {
		int n = 10;
		if (args.length > 0) {
			n = Integer.parseInt(args[0]);
		}

		DoublyLinkedList<Integer> dlst = new DoublyLinkedList<>();
		for (int i = 1; i <= n; ++i) {
			dlst.add(Integer.valueOf(i));
		}
		dlst.printList();

		// Casting the iterator to DoublyLinkedList type
		DoublyLinkedList<Integer>.DLLIterator dit = (DoublyLinkedList<Integer>.DLLIterator) dlst.iterator(); 
		Scanner in = new Scanner(System.in);
		whileloop: while (in.hasNext()) {
			int com = in.nextInt();
			switch (com) {
			case 1: // Move to next element and print it
				if (dit.hasNext()) {
					System.out.println(dit.next());
				} else {
					break whileloop;
				}
				break;
			case 2: // Remove element
				dit.remove();
				dlst.printList();
				break;
			case 3: // Move to previous element and print it
				if (dit.hasPrev()) {
					System.out.println(dit.prev());
				} else {
					break whileloop;
				}
				break;
			case 4: // Add element
				System.out.println("Enter element to be added: ");
				dit.add(in.nextInt());
				dlst.printList();
				break;
			default: // Exit loop
				break whileloop;
			}
		}
		dlst.printList();
	}

}
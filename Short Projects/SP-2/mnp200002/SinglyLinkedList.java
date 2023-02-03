/* @author rbk
 *  Singly linked list: for instructional purposes only
 *  Ver 1.0: 2018/08/21
 *  Ver 2.0: 2018/08/28: modified to be able to extend to DoublyLinkedList
 *  Entry class has generic type associated with it, to allow inheritance.
 *  We can now have a doubly linked list class DLL that has

public class DoublyLinkedList<T> extends SinglyLinkedList<T> {
    static class Entry<E> extends SinglyLinkedList.Entry<E> {
	Entry<E> prev;
	Entry(E x, Entry<E> next, Entry<E> prev) {
	    super(x, nxt);
	    this.prev = prev;
	}
    }

 */

/*
 * Team Number - SP 37
 * Manav Prajapati - mnp200002
 * Rahul Bosamia - rnb200003
 */

package mnp200002; 	// change to your netid
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<T> implements Iterable<T> {

    /** Class Entry holds a single node of the list */
    public static class Entry<E> {
        public E element;
        public Entry<E> next;

        protected Entry(E x, Entry<E> nxt) {
            element = x;
            next = nxt;
        }
    }

    // Dummy header is used.  tail stores reference of tail element of list
    protected Entry<T> head, tail;
    public int size;

    public SinglyLinkedList() {
        head = new Entry<>(null, null);
        tail = head;
        size = 0;
    }

    public Iterator<T> iterator() { return new SLLIterator(); }

    protected class SLLIterator implements Iterator<T> {
		Entry<T> cursor, prev;
		protected boolean ready;  // is item ready to be removed?

		protected SLLIterator() {
			cursor = head;
			prev = null;
			ready = false;
		}

		public boolean hasNext() {
			return cursor.next != null;
		}
		
		public T next() {
			prev = cursor;
			cursor = cursor.next;
			ready = true;
			return cursor.element;
		}

		// Removes the current element (retrieved by the most recent next())
		// Remove can be called only if next has been called and the element has not been removed
		public void remove() {
			if(!ready) {
				throw new NoSuchElementException();
			}
			prev.next = cursor.next;
			// Handle case when tail of a list is deleted
			if(cursor == tail) {
			tail = prev;
			}
			cursor = prev;
			ready = false;  // Calling remove again without calling next will result in exception thrown
			size--;
		}
    }  // end of class SLLIterator

    // Add new elements to the end of the list
    public void add(T x) {
		add(new Entry<>(x, null));
    }

    public void add(Entry<T> ent) {
		tail.next = ent;
		tail = tail.next;
		size++;
    }

    public void printList() {
		System.out.print(this.size + ": ");
		for(T item: this) {
			System.out.print(item + " ");
		}

		System.out.println();
    }

    // Rearrange the elements of the list by linking the elements at even index
    // followed by the elements at odd index. Implement by rearranging pointers
    // of existing elements without allocating any new elements.
    public void unzip() {
		// Unzip only if size is greater than or equal to 3
		if(size >= 3) {
			Entry<T> odd = head.next;
			Entry<T> even = head.next.next;
			Entry<T> oddFirst = odd;
			Entry<T> evenFirst = even;
			
			while(true) {
				odd.next = even.next;
				odd = even.next;

				if(odd.next == null) {
					even.next = oddFirst;
					break;
				}

				even.next = odd.next;
				even = odd.next;

				if(even.next == null) {
					even.next = oddFirst;
					odd.next = null;
					break;
				}
			}

			head.next = evenFirst;
			tail = odd;
			return;
		}
    }

	public void addFirst(T value) {
		Entry<T> ent = new Entry<>(value, null);
		ent.next = head.next;
		head.next = ent;
		size++;
		return;
	}

	public T removeFirst() {
		T value = head.next.element;
		head.next = head.next.next;
		size--;
		return value;
	}

	public T remove(T value) {
		Entry<T> node = head.next;

		// First element
		if(node.element == value) {
			head.next = node.next;
			node = null;
			size--;
			return value;
		}
		Entry<T> prev = node;
		node = node.next;
		
		// In between element
		while(node != null) {
			if(node.element == value) {
				prev.next = node.next;
				node.next = null;
				size--;
				return value;
			}

			prev = node;
			node = node.next;
		}

		throw new NoSuchElementException();
	}

    public static void main(String[] args) throws NoSuchElementException {
        int n = 10;
        if(args.length > 0) {
            n = Integer.parseInt(args[0]);
        }

        SinglyLinkedList<Integer> lst = new SinglyLinkedList<>();
        for(int i=1; i<=n; i++) {
            lst.add(Integer.valueOf(i));
        }
        lst.printList();

		// Iterator<Integer> it = lst.iterator();
		// Scanner in = new Scanner(System.in);
		// whileloop:
		// while(in.hasNext()) {
		// 	int com = in.nextInt();
		// 	switch(com) {
		// 	case 1:  // Move to next element and print it
		// 		if (it.hasNext()) {
		// 			System.out.println(it.next());
		// 		} else {
		// 			break whileloop;
		// 		}
		// 		break;
		// 	case 2:  // Remove element
		// 		it.remove();
		// 		lst.printList();
		// 		break;
		// 	default:  // Exit loop
		// 		break whileloop;
		// 	}
		// }
		// lst.printList();
		// lst.unzip();
		// lst.printList();
		// lst.removeFirst();
		// lst.remove(9);
		// lst.addFirst(2);
		// lst.printList();
    }
}

/* Sample input:
   1 2 1 2 1 1 1 2 1 1 2 0
   Sample output:
10: 1 2 3 4 5 6 7 8 9 10 
1
9: 2 3 4 5 6 7 8 9 10 
2
8: 3 4 5 6 7 8 9 10 
3
4
5
7: 3 4 6 7 8 9 10 
6
7
6: 3 4 6 8 9 10 
6: 3 4 6 8 9 10 
6: 3 6 9 4 8 10
5: 2 6 4 8 10
*/

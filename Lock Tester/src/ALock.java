/** ALock.java
 * 
 * Code reproduced from textbook:
 * 
 * "The Art of Multiprocessor Programming"
 * by: Maurice Herlihy and Nir Shavit
 * 
 */
import java.util.concurrent.atomic.AtomicInteger;

public class ALock implements Lockable {
	ThreadLocal<Integer> mySlotIndex = new ThreadLocal<Integer>() {
		protected Integer initialValue() {
			return 0;
		}
	};

	AtomicInteger tail;
	volatile boolean[] flag;
	int size;

	/** ALock(int capacity)
	 * 
	 * This is the constructor for the ALock class.
	 * This constructor initializes the size of the
	 * Array Lock with the capacity that is passed in
	 * through the parameter. This also sets the tail
	 * with a new Atomic Integer of 0, sets the flag 
	 * boolean array with the capacity that is passed
	 * in throught the parameter, and sets the first flag
	 * to true.
	 * 
	 */

	public ALock(int capacity) {
		size = capacity;
		tail = new AtomicInteger(0);
		flag = new boolean[capacity];
		flag[0] = true;
	}

	/** lock()
	 * 
	 * This method simulates the locking mechanism
	 * for the ALock.
	 * 
	 */

	public void lock() {
		int slot = tail.getAndIncrement() % size;
		mySlotIndex.set(slot);
		while( ! flag[slot]) {};
	}

	/** unlock()
	 * 
	 * This method simulates the unlocking mechanism
	 * for the ALock.
	 * 
	 */

	public void unlock() {
		int slot = mySlotIndex.get();
		flag[slot] = false;
		flag[(slot + 1) % size] = true;
	}
}
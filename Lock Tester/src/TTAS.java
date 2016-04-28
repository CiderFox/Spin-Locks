/** TTAS.java
 * 
 * Code reproduced from textbook:
 * 
 * "The Art of Multiprocessor Programming"
 * by: Maurice Herlihy and Nir Shavit
 * 
 */
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/** TTAS.java
 * 
 * This class creates the TTAS Lock.
 *
 */

public class TTAS implements Lockable {
	AtomicBoolean lock = new AtomicBoolean(false);


	/** lock()
	 * 
	 * This method simulates the unlocking mechanism 
	 * for the TTAS Lock.
	 * 
	 */

	@Override
	public void lock() {
		while(true){
			while(lock.get()){};
			if(!lock.getAndSet(true)){
				return;
			}
		}
	}

	/** unlock()
	 * 
	 * This method simulates the unlocking mechanism 
	 * for the TTAS Lock.
	 * 
	 */

	@Override
	public void unlock() {
		lock.set(false);
	}
}
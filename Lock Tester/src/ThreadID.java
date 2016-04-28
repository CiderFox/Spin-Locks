/** ThreadID.java
 * 
 * This class allows the creation of a threads ID
 * and allows accessing the value, setting the value
 * and resetting the value.
 *
 */

public class ThreadID {

	private static volatile int nextID = 0;
	private static ThreadLocalID threadID = new ThreadLocalID();

	/** get()
	 * 
	 * This method retrieves the value of threads ID.
	 * 
	 */

	public static int get() {  
		return threadID.get();
	}

	/** reset()
	 * 
	 * This method resets the value of threads
	 * ID to the initial value of 0.
	 */

	public static void reset() {
		nextID = 0;
	}

	/** set(int value)
	 * 
	 * This method sets the value of a Threads id to
	 * the value that was passed into the parameter.
	 * 
	 */

	public static void set(int value) {
		threadID.set(value);
	}

	/** ThreadLocalID extends ThreadLocal<Integer>
	 * 
	 * This is a small class that is written that returns the 
	 * value of the next ID for a thread.
	 *
	 */

	private static class ThreadLocalID extends ThreadLocal<Integer> {
		protected synchronized Integer initialValue() {
			return nextID++;
		}
	}
}
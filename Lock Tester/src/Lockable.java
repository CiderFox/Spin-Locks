/** Lockable.java
 * 
 * This is my lock interface class, so that the only
 * methods needed for implementing my lock types are 
 * the methods: Lock() and Unlock()
 *
 */
public interface Lockable {
	
	public void lock();
	
	public void unlock();

}

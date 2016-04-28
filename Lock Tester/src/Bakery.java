/** Bakery.java
 * 
 * Code reproduced from textbook:
 * 
 * "The Art of Multiprocessor Programming"
 * by: Maurice Herlihy and Nir Shavit
 * 
 * Additional Resource:
 * http://furkankamaci.com/implementing-filter-and-bakery-locks-in-java/
 * (Used for help implementing the spin
 * 
 */
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;


public class Bakery implements Lockable {
	
    private AtomicBoolean[] flag;
    private AtomicInteger[] label;
    private int n;
    
    /** Bakery(int n)
     * 
     * This is the constructor for the Backery Lock.
     * This constructor sets the flag and label arrays
     * to be able to hold the capacity that is passed in
     * through the parameter. 
     * 
     */
    
    public Bakery(int n) {
        this.n = n;
        flag = new AtomicBoolean[n];
        label = new AtomicInteger[n];
        for (int i = 0; i < n; i++) {
            flag[i] = new AtomicBoolean();
            label[i] = new AtomicInteger();
        }
    }
    
    /** lock()
	 * 
	 * This method simulates the locking mechanism
	 * for the Bakery Lock.
	 * 
	 */
    
    @Override
    public void lock() {
        int i = ThreadID.get();
        flag[i].set(true);
        label[i].set(findMaximumElement(label) + 1);
        for (int k = 0; k < n; k++) {
            while ((k != i) && flag[k].get() && ((label[k].get() < label[i].get()) || ((label[k].get() == label[i].get()) && k < i))) {
            }
        }
    }

    /** unlock()
     * 
     * This method simulates the unlocking mechanism 
     * for the Bakery Lock.
     * 
     */
    
    @Override
    public void unlock() {
        flag[ThreadID.get()].set(false);
    }

    /** findMaximumElement(AtomicInteger0- elementArray) 
     * 
     * This method finds the current maximum element within
     * the array. this is used when locking a thread to check 
     * if this thread can be locked or set to spin/wait.
     * 
     */
    
    private int findMaximumElement(AtomicInteger[] elementArray) {
        int maxValue = Integer.MIN_VALUE;
        for (AtomicInteger element : elementArray) {
            if (element.get() > maxValue) {
                maxValue = element.get();
            }
        }
        return maxValue;
    }

}
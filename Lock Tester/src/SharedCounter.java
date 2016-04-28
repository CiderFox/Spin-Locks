/** AccessCounter.java
 * 
 * This class simulated the locking and unlocking of a thread in
 * order for the thread to access the critical section to increment 
 * the shared counter.
 *  
 *
 */

public class SharedCounter extends Thread {

	private final int attemptCount;
	private final Lockable lock;
	public static int sharedCounter = 0;

	/** AccessCounter(Lockable lock, int attemptCount)
	 * 
	 * This is the constructor that starts the AccessCounter Class
	 * with setting the lock to what ever is passed in through the 
	 * parameter, sets the number of times a thread will access the 
	 * shared counter with the int attemotCount that is passed in
	 * the parameters. 
	 * 
	 */

	public SharedCounter(Lockable lock,int attemptCount){
		this.lock = lock;
		this.attemptCount = attemptCount;
	}

	/** run()
	 * 
	 * This is the run methid that runs the thread to access
	 * the make shift critical section.
	 * 
	 */

	public void run() {		 
		for(int i = 0; i < attemptCount; i++){
			lock.lock();	
			try{
				criticalSection();
			}finally{
				lock.unlock();
			}
		}
	}

	public static int getCounter() {
		return sharedCounter;
	}
	
	public static void  resetCounter() {
		sharedCounter = 0;
	}


	/** critical Section()
	 * 
	 * This method simulates the critical section that each
	 * thread is trying to access. once the thread accesses
	 * this critical section method, it increments the previous 
	 * counter number and also increments the current counter
	 * value.
	 * 
	 */

	private void criticalSection() {	
		sharedCounter++;
		//System.out.println(sharedCounter);
	}
}
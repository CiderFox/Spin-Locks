/** MainDriver.java
 * 
 * This is the main class of the program, accessing all other
 * classes needed to run and preform simulations for different
 * amounts of threads, and lock types accessing the "critical
 * section" to increment the shared counter.
 * 
 */

import javax.swing.JFrame;

public class MainDriver {
	private static int threadCount;
	private static int attempts;
	static Journal journal;
	private static int runNumber = 1;

	/** MainDriver()
	 * 
	 * This constructor is empty because all of
	 * the variables of this class are static and 
	 * do not need to be set.
	 * 
	 */

	public MainDriver() {	}

	/** setThreadCount(int count)
	 * 
	 * This method sets the current number of threads 
	 * that are needed for a simulation.
	 * 
	 */

	public void setThreadCount(int count) {
		threadCount = count;
	}
	
	/** setAttempts(int number)
	 * 
	 * This method sets the number of attempts each
	 * thread needs to make for incrementing the 
	 * shared counter.
	 * 
	 */
	
	public static void setAttempts(int number) {
		attempts = number;
	}

	/** runThreads(Lockable lock)
	 * 
	 * This method runs the threads according to the Lock
	 * that is passed into the parameters. This method runs
	 * the threads by calling the threadResults method.
	 * 
	 */

	private static void runThreads(Lockable lock) {
		try {
			threadResults(lock, threadCount, attempts);
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/** threadResults(Lockable lock, int threads, int counterAttempts)
	 * 
	 * This method retrieves the results of the threads with a given lock.
	 * This works by giving all the threads an ID, then starting the thread
	 * which accesses the run method in the SharedCounter Class. This method
	 * also prints the results of the simulation after all the threads have
	 * been join().
	 * 
	 */

	private static void threadResults(Lockable lock, int threads, int counterAttempts) throws InterruptedException {
		Thread[] ThreadSet = new Thread[threads];
		ThreadID.reset();
		for (int i = 0; i < threads; i++) {
			ThreadID.set(i);	
			ThreadSet[i] = new SharedCounter(lock, counterAttempts);
		}
		
		Long startTime = System.currentTimeMillis();
		for (int i = 0; i < threads; i++) {
			ThreadSet[i].start();
		}
		try {
			for (int i = 0; i < threads; i++) {
				ThreadSet[i].join();
			}
		} catch (InterruptedException ie) {
			ie.printStackTrace();
		}

		Long endTime = System.currentTimeMillis();
		int finalCount = SharedCounter.getCounter();
		journal.note("Total Finish Count : " + finalCount);
		String string = (String.format("%-5d",(endTime - startTime)));
		journal.note("Total Running Time : " + string + " millseconds.");
		if( ( finalCount!= threads * counterAttempts)) {
			journal.note("[ ERROR ] counter incremented incorrectly.");
		} else {
			journal.note("[ SUCCESS ] counter incremented correctly.");
		}
		SharedCounter.resetCounter();
		runNumber ++;
		journal.note("---------------------------------------------");
	}

	/** runLock(Bakery bakery)
	 * 
	 * This method is called from the GUI Class to start and print the 
	 * start values and information of a simulation. This method sends the 
	 * simulation to call the runThreads method.
	 *
	 */

	public void runLock(Bakery bakery) {
		journal.note("Experiement Number : " + runNumber);
		journal.note("The Lock Type Used : Bakery Lock");
		journal.note("Number of Threads  : " + threadCount);
		journal.note("Each Threads Count : " + attempts);
		runThreads(new Bakery(threadCount));
	}

	/**runLock(TTAS ttasLock)
	 * 
	 * This method is called from the GUI Class to start and print the 
	 * start values and information of a simulation. This method sends the 
	 * simulation to call the runThreads method.
	 * 
	 */

	public void runLock(TTAS ttasLock) {
		journal.note("Experiement Number : " + runNumber);
		journal.note("The Lock Type Used : TTAS Lock");
		journal.note("Number of Threads  : " + threadCount);
		journal.note("Each Threads Count : " + attempts);
		runThreads(new TTAS());
	}

	/** runLock(ALock alock)
	 *  * 
	 * This method is called from the GUI Class to start and print the 
	 * start values and information of a simulation. This method sends the 
	 * simulation to call the runThreads method.
	 *
	 */

	public void runLock(ALock aLock) {
		journal.note("Experiement Number : " + runNumber);
		journal.note("The Lock Type Used : ALock");
		journal.note("Number of Threads  : " + threadCount);
		journal.note("Each Threads Count : " + attempts);
		runThreads(new ALock(threadCount));
	}

	/** mainMethod
	 * 
	 * This is the main method that starts the program by initializing
	 * a new GUI and waits for input before simulating a Lock.
	 *
	 */

	public static void main(String[] args) {
		journal = new Journal();
		GUI frame = new GUI();
		frame.pack();
		frame.setTitle("Spin Locks");
		frame.setSize(610,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		journal.setReadout(frame);
	}
}
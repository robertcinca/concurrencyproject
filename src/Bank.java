import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank implements Executor {

	final ExecutorService employees;
	
	private int[] bankStaffFree;
	private int[] bankStaffBusy;
	
	public Bank(int m) {
		employees = Executors.newFixedThreadPool(m);
		bankStaffFree = new int[m];
		bankStaffBusy = new int[m];
		
		for(int i=0; i<m; i++) {
			bankStaffFree[i] = i;
			Runnable bankStaff = new BankStaff(i, this);
		}
	}
	
	public void doBusiness() {
		
		/**
		 * I dont quite know how to incorporate the time concept yet
		 * Maybe a global variable that acts as a counter would work.
		 * This method would then basically work as a for-loop and increment the timer each time it passes through
		 * the threads are put to sleep if it not their turn and are woken up if they are open for processing
		 * the threads would probably also need to include some kind of variables that can be used for optimization
		 */
		
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		
		while(!false) {
			
		}
		
	//	employees.shutdown();
	}

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		
	}		
		

}

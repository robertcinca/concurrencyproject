import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {

	//no idea if lists are the best data structure for this
	private List<BankStaff> staff;
	private BlockingQueue<Runnable> schedule;
	
	public Bank(int[] config, BlockingQueue<Runnable> jobs) {	
		schedule = jobs;
		staff = new LinkedList<BankStaff>();
		for(int i=0; i<config[0]; i++) {
			BankStaff bankStaff = new BankStaff(i, this);
			staff.add(bankStaff);
		}
	}

	public void doBusiness() {
		//i think the lock should probably be in the bank staff class
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		
		int timer = 0;
		
		//true as long as the schedule and the queue are not empty
		//the queue is a priorityBlockingQueue
		//the comparable compare to is not yet implemented though
		

		
		//Runnable 
		while(!false) {
			//add jobs from schedule to queue
			
			//synchronized thread actions
			
			//signal threads the timer int / put them to sleep
			
			//change queue priorities
			timer++;
		}
		
	//	bankstaff.shutdown();
	}	
		
}

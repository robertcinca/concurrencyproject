import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {

	//no idea if lists are the best data structure for this
	private List<BankStaff> staff;
	private BlockingQueue<Runnable> schedule;
	//the bank class contains a list of all jobs which are runnables
	//when this class creates the staff, it gives them a reference to the runnable list. there shouldnt be race conditions like this
	
	public Bank(int[] config, BlockingQueue<Runnable> jobs) {	
		schedule = jobs;	
		for(int i=0; i<config[0]; i++) {
			BankStaff bankStaff = new BankStaff(i, this);
			staff.add(bankStaff);
		}
	}

	public void doBusiness() {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		
		int timer = 0;
		
		//true as long as the schedule and the queue are not empty
		//the queue could be a priorityBlockingQueue
		
		//i think i fucked up with the executor. when using it, the job would implement the runnable?
		//still would work, the bankstaff would be basically the individual threads
		//no idea how to implement the job queue though
		
		//Runnable 
		while(!false) {
			//add jobs from schedule to queue
			
			//synchronized thread actions
			
			//signal threads the timer int / put them to sleep
			
			//change queue priorities
			timer++;
		}
		
	//	employees.shutdown();
	}	
		
}

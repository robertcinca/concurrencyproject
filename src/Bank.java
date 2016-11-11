import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank implements Executor {

	final ExecutorService employees;
	private List<Job> schedule;
	
	public Bank(int[] config, List<Job> jobs) {
		employees = Executors.newFixedThreadPool(config[0]);
		schedule = jobs;
		
		//this is probably wrong
		for(int i=0; i<config[0]; i++) {
			Runnable bankStaff = new BankStaff(i, this);
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

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		
	}		
		

}

package v1;

import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;

public class Bank {
	
	private Reader reader;
	private PriorityBlockingQueue<Job> schedule;
	private LinkedList<BankStaff> employees;
	private int timer;
	private CyclicBarrier barrier;
	
	/**
	 * Sets up the bank as well as its tellers and saves all needed informations for the remainder of the program in local variables
	 */
	public Bank(Reader reader) {
		this.reader = reader;
		timer = 0;
		schedule = new PriorityBlockingQueue<Job>();	
		barrier = new CyclicBarrier(reader.getConfig(0)+1);
		employees = new LinkedList<BankStaff>();
		for(int i=0; i<reader.getConfig(0); i++) {
			BankStaff teller = new BankStaff(i+1, this);
			employees.add(teller);
		}
	}
	
	/**
	 * This method is the program's core method. It runs in a loop, fetches new jobs, handles the thread queue and
	 * the global time variable.
	 */
	public void doBusiness() {	
		//starts all the threads
		for(BankStaff employee : employees) {
			Thread thread = new Thread(employee);
			thread.start();
		}
		while(true) {
			//reader.assignJobs(timer);
			
			//to determine if a customer has to step in queue, this polls the tellers if they are busy, returns a number and compares that
			//number to the size of the queue returned by the reader
			//there are barriers here
			
			try {
				barrier.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				System.out.println("Bank produced error at barrier");
			}
			System.out.println("Time=" +timer++);
		}
		
	}
	
	public CyclicBarrier getBarrier() {
		return barrier;
	}

	public int getTimer() {
		return timer;
	}
	
	public PriorityBlockingQueue<Job> getSchedule() {
		return schedule;
	}
		
}

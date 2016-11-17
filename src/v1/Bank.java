package v1;

import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;

public class Bank {
	
	private Reader reader;
	private PriorityBlockingQueue<Job> schedule;
	//is used for adding jobs
	private PriorityBlockingQueue<Job> temp;
	private LinkedList<BankStaff> employees;
	private int timer;
	private CyclicBarrier barrier1;
	private CyclicBarrier barrier2;
	private int busyCount;
	private int jobCount;
	
	
	/**
	 * Sets up the bank as well as its tellers and saves all needed informations for the remainder of the program in local variables
	 */
	public Bank(Reader reader) {
		this.reader = reader;
		timer = 0;
		schedule = new PriorityBlockingQueue<Job>();	
		barrier1 = new CyclicBarrier(reader.getConfig(0)+1);
		barrier2 = new CyclicBarrier(reader.getConfig(0)+1);
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
		//gives threads time to start
		try {
			Thread.sleep(1);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		while(true) {
			System.out.println("Time=" +timer);
			
			busyCount = 0;
			for(BankStaff employee : employees) {
				if(employee.getStatus()) {
					busyCount++;
				}
			}
			System.out.println(busyCount);
			
			temp = reader.assignJobs(timer);
			jobCount = temp.size();
			for(Job job : temp) {
				//when all tellers are busy, employees dont get added to the schedule but have to walk to the queue
				if(jobCount > busyCount) {
					job.setQueued(true);
					jobCount--;
				} else {
					schedule.add(job);
				}
			}
			
			//this barrier is used so all jobs are read and assigned to the list before the tellers do anything
			try {
				barrier1.await();
			} catch (InterruptedException | BrokenBarrierException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//to determine if a customer has to step in queue, this polls the tellers if they are busy, returns a number and compares that
			//number to the size of the queue returned by the reader
			//there are barriers here
			
			try {
				barrier2.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				System.out.println("Bank produced error at barrier");
			}
			
			timer++;
			//simple first fix
			if(timer==100) {break;}
		}
		
	}
	
	public CyclicBarrier getBarrier1() {
		return barrier1;
	}
	
	public CyclicBarrier getBarrier2() {
		return barrier2;
	}

	public int getTimer() {
		return timer;
	}
	
	public PriorityBlockingQueue<Job> getSchedule() {
		return schedule;
	}
		
}

package v1;

import java.util.LinkedList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.PriorityBlockingQueue;

public class Bank {
	
	private FileScanner reader;
	private PriorityBlockingQueue<Job> schedule; //jobs that are now under work
	private PriorityBlockingQueue<Job> temp; //an auxiliary queue used for adding jobs
	private PriorityBlockingQueue<Job> queue; //represents the actual queue
	private LinkedList<BankStaff> employees;
	private int timer; //global timer
	private CyclicBarrier barrier1;
	private CyclicBarrier barrier2;
	private CyclicBarrier barrier3;
	private CyclicBarrier barrier4;
	private int busyCount; //number of busy tellers
	private boolean done; //is the program done?
	
	/**
	 * Sets up the bank as well as its tellers and saves all needed informations (especially reader) for the remainder of the program in local variables
	 */
	public Bank(FileScanner reader) {
		this.reader = reader;
		this.timer = 0;
		this.done = false;
		schedule = new PriorityBlockingQueue<Job>();
		queue = new PriorityBlockingQueue<Job>();
		barrier1 = new CyclicBarrier(reader.getConfig(0)+1);
		barrier2 = new CyclicBarrier(reader.getConfig(0)+1);
		barrier3 = new CyclicBarrier(reader.getConfig(0)+1);
		barrier4 = new CyclicBarrier(reader.getConfig(0)+1);
		employees = new LinkedList<BankStaff>();
		for(int i=0; i<reader.getConfig(0); i++) {
			BankStaff teller = new BankStaff(i+1, this);
			employees.add(teller);
		}
	}
	
	/**
	 * This method is the program's core method. It runs in a loop, fetches new jobs, handles the thread queue and
	 * manages the global time variable.
	 * For better understanding, especially of the concurrent mechanisms, his method is visualized in the report which is turned in as
	 * a hardcopy.
	 */
	public void doBusiness()  {	
		for(BankStaff employee : employees) { //starts all the threads
			Thread thread = new Thread(employee);
			thread.start();
		}
		try { //gives threads time to start
			Thread.sleep(1);
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}
		System.out.println("----- Time = " +timer+ " -----");
		while(true) { //MAIN LOOP
			awaitBarrier(1); //used so tellers can finish tasks before the new busycount and schedule allocation is done
			busyCount = 0; //counts how many employees are currently busy
			for(BankStaff employee : employees) {
				if(employee.getStatus()) {
					busyCount++;
				}
			}	
			for(Job queued : queue) { //check if employees can step out of the queue
				queued.setQueueingTimes(0, -1);
				if((busyCount - reader.getConfig(0) < 0) && queued.getQueueingTimes(0)<=0) {
					schedule.add(queued);
					busyCount++;
					queue.remove(queued);
					System.out.println("(" +timer+ ") Employee " +queued.getEmployee()+ " steps out of queue.");
				}
			}
			temp = reader.assignJobs(timer); //reads all jobs that arrive at timer and distributes them either to the queue or directly to the schedule
			for(Job job : temp) {
				if(busyCount - reader.getConfig(0) < 0) { //job is added to schedule if teller available
					schedule.add(job);	
					busyCount++;
				} else { //job is added to queue if all telelrs are busy
					job.setQueued(true);
					System.out.println("(" +timer+ ") Employee " +job.getEmployee()+ " is going to the queue");
					queue.add(job);
				}
			}
			awaitBarrier(2); //used so scheduling can be done before tellers pick tasks from the schedule
			if(busyCount==0 && schedule.isEmpty() && queue.isEmpty() && reader.isDone(timer)) { //checks if all tasks are done
				done=true;
				System.out.println("All jobs are done");
			}	
			awaitBarrier(3); //wait for employees to finish their turn so the timer can be incremented
			timer++; //increments global time
			if(!done) {
				System.out.println("----- Time = " +timer+ " -----");
			}
			awaitBarrier(4);	
			if(done==true) {
				break;
			}	
		}
		try { //give workers chance to end day, makes output nice
			Thread.sleep(4);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * This is used to give all other threads access to the CyclicBarriers, thus public. It is all contained within one method to make 
	 * the code more readable, since the await() instruction is called quite often and requires a try-multicatch every single time.
	 */
	public void awaitBarrier(int barrierNo) {
		switch(barrierNo) {
		case 1: try {
				barrier1.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		case 2: try {
				 barrier2.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		case 3: try {
				barrier3.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		case 4: try {
				barrier4.await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		default: break;
		}
	}

	public int getTimer() {
		return timer;
	}	
	
	public PriorityBlockingQueue<Job> getSchedule() {
		return schedule;
	}
	
	public boolean getDone() {
		return done;
	}
		
}

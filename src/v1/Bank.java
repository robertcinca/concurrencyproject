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
	//this queue represents the actual queue
	private PriorityBlockingQueue<Job> queue;
	private LinkedList<BankStaff> employees;
	private int timer;
	private CyclicBarrier barrier1;
	private CyclicBarrier barrier2;
	private CyclicBarrier barrier3;
	private CyclicBarrier barrier4;
	private int busyCount;
	private boolean done;
	
	/**
	 * Sets up the bank as well as its tellers and saves all needed informations for the remainder of the program in local variables
	 */
	public Bank(Reader reader) {
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
	 * the global time variable.
	 * @throws BrokenBarrierException 
	 * @throws InterruptedException 
	 */
	public void doBusiness() throws InterruptedException, BrokenBarrierException {	
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
		System.out.println("----- Time = " +timer+ " -----");
		while(true) {
			
			//this barrier is used so tellers can finish tasks before the new busycount and schedule allocation is done
			barrier1.await();
			
			//counts how many employees are currently busy
			busyCount = 0;
			for(BankStaff employee : employees) {
				if(employee.getStatus()) {
					busyCount++;
				}
			}
			
			//check if employees can step out of the queue
			for(Job queued : queue) {
				queued.setQueueingTimes(0, -1);
				if((busyCount - reader.getConfig(0) < 0) && queued.getQueueingTimes(0)<=0) {
					schedule.add(queued);
					queue.remove(queued);
					System.out.println("(" +timer+ ") Employee " +queued.getEmployee()+ " steps out of queue.");
				}
			}
			
			//reads from the config and distributes job either to the queue or directly to the schedule
			temp = reader.assignJobs(timer);
			for(Job job : temp) {
				//when all tellers are busy, employees dont get added to the schedule but have to walk to the queue
				if(busyCount - reader.getConfig(0) < 0) {
					schedule.add(job);	
					busyCount++;
				} else {
					job.setQueued(true);
					//tellers should compare priority of jobs they take, queued jobs have lower priority,
					System.out.println("(" +timer+ ") Employee " +job.getEmployee()+ " is going to the queue");
					queue.add(job);
				}
			}
			
			///this barrier is used so scheduling can be done before tellers pick tasks from the schedule
			barrier2.await();
			
			if(busyCount==0 && schedule.isEmpty() && queue.isEmpty() && reader.isDone(timer)) {
				done=true;
				System.out.println("All jobs are done");
			}
			
			//wait for employees to finish their turn so the timer can be incremented
			barrier3.await();
		
			timer++;
			if(!done) {
				System.out.println("----- Time = " +timer+ " -----");
			}
			
			barrier4.await();
			
			if(done==true) {
				break;
			}
		
		}
		
		//give workers chance to end day
		Thread.sleep(4);
	}
	
	public CyclicBarrier getBarrier1() {
		return barrier1;
	}
	public CyclicBarrier getBarrier2() {
		return barrier2;
	}
	public CyclicBarrier getBarrier3() {
		return barrier3;
	}
	public CyclicBarrier getBarrier4() {
		return barrier4;
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

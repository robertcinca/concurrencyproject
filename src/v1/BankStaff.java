package v1;

import java.util.concurrent.BrokenBarrierException;

public class BankStaff implements Runnable {

	private Bank employer;
	private int tellerID;
	private Job task;
	private boolean busy;
	
	public BankStaff(int i, Bank bank) {
		this.tellerID = i;
		this.employer = bank;
		task = null;
	}
	
	//this makes away with a lot of annoying try catch declarations
	public void awaitBarrier(int barrierNo) {
		switch(barrierNo) {
		case 1: try {
				employer.getBarrier(1).await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		case 2: try {
				employer.getBarrier(2).await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		case 3: try {
				employer.getBarrier(3).await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		case 4: try {
				employer.getBarrier(4).await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			break;
		default: break;
		}
	}
	
	@Override
	public void run() {
		System.out.println("Teller " +tellerID+ " starts his day. Start " + Thread.currentThread().getName());
		while(true) {		
			//all tellers that have a job do that now, this should maybe done before the first barrier, there might be a need for a third one			
			if(busy) {
				if(task.getQueued()) {
					if(task.getQueueingTimes(1)<=0) {
						task.setQueued(false);
					} else {
						task.setQueueingTimes(1, -1);
					}
				} else {	
					//this should solve the concurrency problem described in the last commit
					task.execute(employer.getTimer());
					busy = false;
					task = null;
				}
			}
			
			//This barrier is used so tellers can finish tasks before the new busycount and schedule allocation is done
			awaitBarrier(1);
			
			//this barrier is used so scheduling can be done before tellers pick tasks from the schedule
			awaitBarrier(2);
			
			//idling employees try to acquire new job
			if(!busy){		
				task = employer.getSchedule().poll();
				if(!(task == null)) {
					busy = true;
					if(task.getQueued()==true) { 
						task.setAdmitted((employer.getTimer()+task.getQueueingTimes(1)), this);
					} else {
						task.setAdmitted(employer.getTimer(), this);
					}
					System.out.println("("+employer.getTimer()+") Teller " +tellerID+ " takes on Employee " +task.getEmployee()+ ".");
				}
			}
			
			//wait for employees to finish their turn so the timer can be incremented			
			awaitBarrier(3);
			
			awaitBarrier(4);
			
			if(employer.getDone()) {
				break;
			}
			
		}
		System.out.println("Teller " +tellerID+ " ends his day. Kill " + Thread.currentThread().getName());
	}
	
	public boolean getStatus() {
		return busy;
	}
	
	public Bank getEmployer() {
		return employer;
	}
	
	public int getID() {
		return tellerID;
	}

	
}

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
	
	@Override
	public void run() {
		System.out.println("Teller " +tellerID+ " starts his day. Start " + Thread.currentThread().getName());
		while(true) {		
			//all tellers that have a job do that now, this should maybe done before the first barrier, there might be a need for a third one			
			if(busy) {
				if(task.getAdmitted() + task.getProcessingTime() <= employer.getTimer()) {
					if(task.getQueued()) {
						if(task.getQueueingTimes(1)<=0) {
							task.setQueued(false);
						} else {
							task.setQueueingTimes(1, -1);
						}
					} else {
						task.execute(employer.getTimer());
						task = null;
						busy = false;
					}
				}
			}
			
			//this barrier is used so tellers can finish tasks before the new busycount and schedule allocation is done
			try {
				employer.getBarrier1().await();
			} catch (InterruptedException | BrokenBarrierException e1) {
				e1.printStackTrace();
			}
			
			//this barrier is used so scheduling can be done before tellers pick tasks from the schedule
			try {
				employer.getBarrier2().await();
			} catch (InterruptedException | BrokenBarrierException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			//idling employees try to acquire new job
			if(!busy){		
				task = employer.getSchedule().poll();
				if(!(task == null)) {
					busy = true;
					if(task.getQueued()==true) { 
						task.setAdmitted((employer.getTimer()+task.getQueueingTimes(1)), tellerID);
					} else {
						task.setAdmitted(employer.getTimer(), tellerID);
					}
					System.out.println("("+employer.getTimer()+") Teller " +tellerID+ " takes on Employee " +task.getEmployee()+ ".");
				}
			}
			
			//wait for employees to finish their turn so the timer can be incremented			
			try {
				employer.getBarrier3().await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			
			try {
				employer.getBarrier4().await();
			} catch (InterruptedException | BrokenBarrierException e) {
				e.printStackTrace();
			}
			
			if(employer.getDone()) {
				break;
			}
			
		}
		System.out.println("Teller " +tellerID+ " ends his day. Kill " + Thread.currentThread().getName());
	}
	
	public boolean getStatus() {
		return busy;
	}

	
}

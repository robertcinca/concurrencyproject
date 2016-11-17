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
	
	public void work() {
		while(true) {	
			
			try {
				employer.getBarrier1().await();
			} catch (InterruptedException | BrokenBarrierException e1) {
				e1.printStackTrace();
			}
			
			//idling employees try to acquire new job
			if(!busy){		
				task = employer.getSchedule().poll();
				if(!(task == null)) {
					busy = true;
					task.setAdmitted(employer.getTimer(), tellerID);
				}
			}
			
			//all tellers that have a job do that now
			if(busy) {
				if(task.getAdmitted() + task.getProcessingTime() == employer.getTimer()) {
					task.execute(employer.getTimer());
					task = null;
					busy = false;
				}
			}
						
			try {
				employer.getBarrier2().await();
			} catch (InterruptedException | BrokenBarrierException e) {
				System.out.println("Teller " +tellerID+ " stuck at barrier.");
			}
			
			//check for kill condition to terminate this thread in bank class. bank has to set the signal if it know that all threads are done,
			//otherwise the barriers will block everything
		}
	}
	
	@Override
	public void run() {
		System.out.println("Teller " +tellerID+ " starts his day. " + Thread.currentThread().getName());
		work();
		System.out.println("Teller " +tellerID+ " ends his day. " + Thread.currentThread().getName());
	}
	
	public boolean getStatus() {
		return busy;
	}

	
}

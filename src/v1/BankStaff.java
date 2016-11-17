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
	}
	
	public void work() {
		while(true) {	
			
			//there could be a second or maybe even third barrier up here so the job scheduling is synchronized as well, job reading in bank
			//class must be done before this line
			
			//idling employees try to acquire new job
			if(!busy){		
				task = employer.getSchedule().poll();
				if(!task.equals(null)) {
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
			
			//bankstaff could try to get a job from queue, if succesfull it is safed in a local variable and then each round it is changed until
			//it can be executed. for that a row of references is called so that employee prints it out, but all the action stays in here
			
			try {
				employer.getBarrier().await();
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

	
}

package v1;

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
	
	/**
	 * This is basically the method of each thread that corresponds with Bank's doBusiness() method.
	 * Runs in a loop, further functionality is explained with in-code comments.
	 */
	@Override
	public void run() {
		System.out.println("Teller " +tellerID+ " starts his day. Start " + Thread.currentThread().getName());
		while(true) { //MAIN LOOP FOR TELLER			
			if(busy) { //if teller has job, work on that now
				if(task.getQueued()) { //is the task coming from the queue
					if(task.getQueueingTimes(1)<=0) {
						task.setQueued(false);
					} else {
						task.setQueueingTimes(1, -1); //decrements T_out
					}
				} else {	
					task.execute(employer.getTimer()); //executes assigned task
					busy = false; //after task is executed (linear thread), employee is not busy and task is null
					task = null;
				}
			}					
			employer.awaitBarrier(1); //used so tellers can finish tasks before the new busycount and schedule allocation is done			
			employer.awaitBarrier(2); //used so scheduling can be done before tellers pick tasks from the schedule	
			if(!busy){	//idling employees try to acquire new job	
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
			employer.awaitBarrier(3); //wait for employees to finish their turn so the timer can be incremented	
			employer.awaitBarrier(4);
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

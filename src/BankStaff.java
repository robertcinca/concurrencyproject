;
public class BankStaff implements Runnable {

	private int employeeID;
	private Bank employer;
	
	public BankStaff(int i, Bank employer) {
		this.employeeID = i;
		this.employer = employer;
	}

	public void work() {
		//reentrant lock in here? reference the bank class lock
		//double while-is-empty loop for the second queue which has to be added
		//lock from bank needs to be passed on
		//while(!employer.getJobs().isEmpty()) {
			while(!employer.getSchedule().isEmpty()) {
				employer.getSchedule().poll().run();
			}
		//}
	}

	@Override
	public void run() {
			work();		
	}
	
	
}

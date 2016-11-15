;
public class BankStaff implements Runnable {

	private int employeeID;
	private Bank employer;
	
	public BankStaff(int i, Bank employer) {
		this.employeeID = i;
		this.employer = employer;
	}

	public void work() {
		while(!employer.getJobs().isEmpty()) {
			//nullpointer occurence / lock might be useful / only one teller gets all the jobs? timing problem in the employee class
			while(!employer.getSchedule().isEmpty()) {
				//System.out.println("Teller " + employeeID);
				((Job) employer.getSchedule().peek()).setAdmitted(employer.getTimer());
				employer.getSchedule().poll().run();
			}
		}
	}

	@Override
	public void run() {
			work();		
	}
	
}

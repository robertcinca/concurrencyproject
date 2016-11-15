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
			while(!employer.getSchedule().isEmpty()) {
				//employer.getLock().lock();
				//if(!employer.getSchedule().isEmpty()) {
					((Job) employer.getSchedule().peek()).setAdmitted(employer.getTimer(), employeeID);
					employer.getSchedule().poll().run();
			//		employer.getLock().unlock();
			//	} else {
			//		employer.getLock().unlock();
			//	}
			}
		}
	}

	@Override
	public void run() {
			work();		
	}
	
	public String toString() {
		return Integer.toString(employeeID);
	}
	
}

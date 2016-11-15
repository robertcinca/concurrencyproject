;
public class BankStaff implements Runnable {

	private int employeeID;
	private Bank employer;
	
	public BankStaff(int i, Bank employer) {
		this.employeeID = i;
		this.employer = employer;
	}

	public void work() {
	//	while(employer.getConfig(6)!=0) {
				while(!employer.getSchedule().isEmpty() || employer.getConfig(6)>0) {
					try {
					      while (true) {
					        Runnable x = employer.getSchedule().take();
					        ((Job) x).setAdmitted(employer.getTimer(), employeeID);
					        x.run();
					      }
				    } catch (InterruptedException e) {
				    	e.printStackTrace();
				    }
				}
//	 	}
	}

	@Override
	public void run() {
			work();		
	}
	
	public String toString() {
		return Integer.toString(employeeID);
	}
	
}

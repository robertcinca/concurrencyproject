package v1;
;
public class BankStaff implements Runnable {

	private int employeeID;
	private Bank employer;
	private boolean busy;
	
	public BankStaff(int i, Bank employer) {
		this.employeeID = i;
		this.employer = employer;
		busy = true;
	}

	public void work() {
		while(true) {
			try {
				Runnable x = employer.getSchedule().take();
				((Job) x).setAdmitted(employer.getTimer(), employeeID);
	        	x.run();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}    	
			System.out.println(employeeID + " alive");
			if(employer.getSchedule().size()==0 && employer.getConfig(6)==0) {
				busy = false;
				break;
			}
		}
		System.out.println(employeeID + " dead");
	}

	public boolean isBusy() {
		return busy;
	}
	
	@Override
	public void run() {
		work();
		Thread.currentThread().interrupt();
	}
	
	@Override
	public String toString() {
		return Integer.toString(employeeID);
	}
	
	
	
}

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
		while(!employer.schedule.isEmpty()) {
			employer.schedule.poll().run();
		}
	}

	@Override
	public void run() {
		//this should notify the bank class once done so that can shut threads down
		while(!employer.schedule.isEmpty()) {
			work();
			//System.out.println("bankstaff " + employeeID + " just chillin");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("stillalivebankstaff" + employeeID);
		}
		
	}
	
	
}

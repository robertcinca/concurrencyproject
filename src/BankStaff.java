
public class BankStaff implements Runnable {

	private int employeeID;
	private Bank employer;
	
	public BankStaff(int i, Bank employer) {
		this.employeeID = i;
		this.employer = employer;
	}

	@Override
	public void run() {
		while(true) {
			System.out.println(employeeID);
			try {
				Thread.sleep(10000);
			} catch(InterruptedException e) {
				Thread.currentThread().interrupt();
				
			}
		}
	}
	
	
}

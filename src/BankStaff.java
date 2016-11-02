
public class BankStaff implements Runnable {

	private int employeeID;
	
	public BankStaff(int i) {
		this.employeeID = i;
	}

	@Override
	public void run() {
		System.out.println(employeeID);
	}
	
	
}

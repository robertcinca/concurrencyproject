import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BankStaff implements Executor {

	private int employeeID;
	private Bank employer;
	
	public BankStaff(int i, Bank employer) {
		this.employeeID = i;
		this.employer = employer;
		Executors.newFixedThreadPool(1);
	}

	@Override
	public void execute(Runnable command) {
		
	}
	
}

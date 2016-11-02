import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Bank implements Executor {

	private int[] bankStaffFree;
	private int[] bankStaffBusy;
	
	public Bank(int m) {
		bankStaffFree = new int[m];
		bankStaffBusy = new int[m];
	}
	
	public void doBusiness(int m) {
		ExecutorService executor = Executors.newFixedThreadPool(m);
		for(int i=0; i<m; i++) {
			Runnable bankStaff = new BankStaff(i);
			executor.execute(bankStaff);
		}
	}		
		
	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		
	}

}

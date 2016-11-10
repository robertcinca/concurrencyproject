import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank implements Executor {

	final ExecutorService employees;
	
	private int[] bankStaffFree;
	private int[] bankStaffBusy;
	
	public Bank(int m) {
		employees = Executors.newFixedThreadPool(m);
		bankStaffFree = new int[m];
		bankStaffBusy = new int[m];
		
		for(int i=0; i<m; i++) {
			bankStaffFree[i] = i;
			Runnable bankStaff = new BankStaff(i, this);
		}
	}
	
	public void doBusiness() {
		
		
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		
		while(!false) {
			
		}
		
	//	employees.shutdown();
	}

	@Override
	public void execute(Runnable command) {
		// TODO Auto-generated method stub
		
	}		
		

}

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bank {

	//no idea if lists are the best data structure for this
	private List<BankStaff> staff;
	public BlockingQueue<Runnable> schedule;
	private ExecutorService executor;
	
	public Bank(int[] config, BlockingQueue<Runnable> jobs) {	
		//im not quite sure if this is perfectly in line with the task which says employeeids are assigned when the customer goes to the bank.
		//it might be better to continuoulsy add jobs to the blockingqueue depending on the timer but thats not as important right now
		schedule = jobs;
		executor = Executors.newFixedThreadPool(config[0]);
		staff = new LinkedList<BankStaff>();
		for(int i=0; i<config[0]; i++) {
			BankStaff bankStaff = new BankStaff(i, this);
			staff.add(bankStaff);
		}
	}

	public void doBusiness() {
		//i think the lock should probably be in the bank staff class / no then it would be several locks.. lock should be in here and accesed by the instances
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();		
		int timer = 0;		
		
		//should wait for signal that executor threads are finished then shut them down. outer loop, inner loop maybe the timer?
		for(BankStaff bankstaff : staff) {
			executor.execute(bankstaff);

		}
		//bankstaff.shutdown();
		
		while(true) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("bankLoopCheck");
		}

		
	
	}	
		
}

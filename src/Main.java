import java.util.List;
import java.util.concurrent.BlockingQueue;

public class Main {
	
	//contains M, T_d, T_w, T_b, T_in, T_out in this particular order
	private static int[] config;
	private static BlockingQueue<Runnable> jobs;
	private static Company[] companies;
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		
		config = reader.setup();
		companies = reader.createEconomy();
		//this is now a priorityblockingqueue
		jobs = reader.assignJobs(companies);
		
		/**
		 * Outputs the configuration details
		 */
		for(int i=0; i<companies.length; i++) {
			System.out.println(companies[i]);
		}
		System.out.println();
		for(int i=0; i<jobs.size(); i++) {
			try {
				System.out.println(jobs.take());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Bank bank = new Bank(config, jobs);
		bank.doBusiness();
		
		System.out.println("checkMain2");
	}
	
}

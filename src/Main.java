import java.util.concurrent.PriorityBlockingQueue;

public class Main {
	
	//contains M, T_d, T_w, T_b, T_in, T_out, Limiter in this particular order
	private static int[] config;
	private static PriorityBlockingQueue<Runnable> jobs;
	private static Company[] companies;
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		Bank bank = new Bank();
		
		config = reader.setup(bank);
		companies = reader.createEconomy();
		jobs = reader.assignJobs(companies);
		//this is the limiter
		config[6] = jobs.size();
		
		bank.setUpBank(config, jobs);

		bank.doBusiness();
		
		System.out.println("mainDone");
		
	}
	
}

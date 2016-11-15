import java.util.concurrent.BlockingQueue;

public class Main {
	
	//contains M, T_d, T_w, T_b, T_in, T_out in this particular order
	private static int[] config;
	private static BlockingQueue<Runnable> jobs;
	private static Company[] companies;
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		Bank bank = new Bank();
		
		config = reader.setup(bank);
		companies = reader.createEconomy();
		jobs = reader.assignJobs(companies);
		
		bank.setUpBank(config, jobs);

		bank.doBusiness();
		
		System.out.println("mainDone");
	}
	
}

import java.util.List;

public class Main {
	
	//contains M, T_d, T_w, T_b, T_in, T_out in this particular order
	private static int[] config;
	private static List<Job> jobs;
	private static Company[] companies;
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		
		config = reader.setup();
		companies = reader.createEconomy();
		jobs = reader.assignJobs(companies);
		
		/**
		 * Outputs the configuration details
		 */
		for(int i=0; i<companies.length; i++) {
			System.out.println(companies[i]);
		}
		System.out.println();
		for(int i=0; i<jobs.size(); i++) {
			System.out.println(jobs.get(i));
		}
		
		Bank bank = new Bank(config[0]);
		bank.doBusiness();
		
		System.out.println("checkMain2");
	}
	
}

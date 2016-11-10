
public class Main {
	
	//contains M, T_d, T_w, T_b, T_in, T_out in this particular order
	private static int[] config;
	private static Job[] jobs;
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		int configNo = reader.input();
		config = reader.setup(configNo);
		jobs = reader.assign(configNo);
		
		Bank bank = new Bank(config[0]);
		bank.doBusiness();
		
		System.out.println("fooMain");
	}
	
}

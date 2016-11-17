package v1;

public class Main {
	
	public static void main(String[] args) {
		Reader reader = new Reader();
		reader.setup();
		Bank bank = new Bank(reader);
				
		System.out.println("Main setup check");
		
		bank.doBusiness();
	}
	
		
}

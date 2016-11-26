package v1;

public class Main {
	
	public static void main(String[] args) {
		FileScanner reader = new FileScanner();
		Bank bank = new Bank(reader);			
		System.out.println("Bank is now open!");
		bank.doBusiness();
		System.out.println("Bank is now closed");
	}
			
}

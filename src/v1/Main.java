package v1;

import java.util.concurrent.BrokenBarrierException;

public class Main {
	
	public static void main(String[] args) {
		FileScanner reader = new FileScanner();
		Bank bank = new Bank(reader);
				
		System.out.println("Bank is now open!");
		
		try {
			bank.doBusiness();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		System.out.println("Bank is now closed");
	}
	
		
}

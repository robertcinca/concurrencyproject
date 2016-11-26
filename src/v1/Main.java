package v1;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
//		FileScanner reader = new FileScanner();
//		Bank bank = new Bank(reader);
//		
//		BankFrame frame = new BankFrame();
//		frame.BankFrame();
//		//frame.redirectSystemStreams();
//		
//		System.out.println("Bank is now open!");
//		bank.doBusiness();
//		System.out.println("Bank is now closed");
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BankFrame window = new BankFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
			
}

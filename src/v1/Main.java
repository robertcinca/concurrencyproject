package v1;

import java.awt.EventQueue;

public class Main {
	
	public static void main(String[] args) {
		//Run GUI frame
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

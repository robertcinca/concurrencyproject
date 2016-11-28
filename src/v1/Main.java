package v1;

import java.awt.EventQueue;

/**
 * Read Documentation for Execution Details.
 * For proper execution, this project has to contain:
 * - src folder with v1 package; should contain 8 classes
 * - resources folder with 3 configs, named config1.txt, config2.txt, config3.txt
 */
public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() { //Run GUI frame
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

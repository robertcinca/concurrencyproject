package v1;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Lorenz
 * Don't touch code, magic!
 * This actually needs to be redone. The prof uploaded some new files, they are
 * strictly text and have another format. So no property files
 * https://www.cs.swarthmore.edu/~newhall/unixhelp/Java_files.html
 * http://stackoverflow.com/questions/731365/reading-and-displaying-data-from-a-txt-file
 * http://stackoverflow.com/questions/8877483/how-to-read-data-from-a-text-file-and-save-some-data-from-it-to-an-array
 * use scanner class for this
 */

public class FileScanner {
	
	private String data;
	//Contains in this particular order: M, T_d, T_w, T_b, T_in, T_out
	private int[] config;
	private Company[] companies;
	
	/**
	 * Sets up the reader and asks which configuration should be run
	 */
	public FileScanner() {
		Scanner keyboard = new Scanner(System.in);
		int configNo;
		do {
			System.out.println("Which configuration should be run? (1-3)");
			while(!keyboard.hasNextInt()) {
				System.out.println("This is not a number.");
				keyboard.next();
			}
			configNo = keyboard.nextInt();
		} while(configNo<=0);
		keyboard.close();			
		data = "resources/config" + configNo + ".txt";
	}
	
	/**
	 * fills the reader class with more details so main can pass it on to bank.
	 * reads the config file and retrieves all the configuration variables.
	 */
	public void setup() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		config = new int[6];
		String temp;
		while(scanner.hasNext()) {
			temp = scanner.next();
			if(temp.equals("M")) {
				for(int j=0; j<6; j++) {
					config[j] = Integer.parseInt(scanner.next());
					if(j!=5) {
						scanner.next();
					}
				}
				break;
			}
		}
		createEconomy();
	}
	
	/**
	 * Searches the config for company entries, creates an array, and fills it with companies and their corresponding balance
	 */
	private void createEconomy() {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String temp; 
		int i = 0;
		while(scanner.hasNext()) {
			temp = scanner.next();
			if(temp.startsWith("Time")) {
				break;
			}
			if(temp.startsWith("Company")) {
				i++;
			}
		}
		scanner.close();
		companies = new Company[i];
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(new File(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		int j = 1;
		while(scanner2.hasNext()) {
			temp = scanner2.next();
			if(temp.equals("Time")) {
				break;
			}
			if(temp.startsWith("Company")) {
				scanner2.next();
				temp = scanner2.next();
				//cuts the dollar sign
				temp = temp.substring(1);	
				//cuts out the commas
				int balanceInt = Integer.valueOf(temp.replaceAll(",", "").toString());	
				Company corporation = new Company(j, balanceInt);
				companies[j-1] = corporation;
				j++;
			}
		}
		scanner2.close();
	}
	
	/**
	 * This method searches the config file for events.
	 * @param timer: The current state of the timer
	 * @return a list which contains the events at timer, if any
	 */
	public PriorityBlockingQueue<Job> assignJobs(int timer) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String temp;
		int size;
		PriorityBlockingQueue<Job> list = new PriorityBlockingQueue<Job>();
		while(scanner.hasNext()) {
			temp = scanner.next();
			if(temp.equals("Time")) {
				temp = scanner.next();
				size = temp.length();
				temp = temp.substring(0, size-1);
				int x = Integer.parseInt(temp);
				if(x == timer) {	
					scanner.next();
					int id = Integer.parseInt(scanner.next());
					//takes the next value and cuts out the companypart
					temp = scanner.next();
					size = temp.length();
					int employer = Integer.parseInt((temp.substring(7, size-1)));
					Employee emp = new Employee(id, companies[(employer-1)]);	
					
					int transactionType;
					int amount = 0;
					temp = scanner.next();
					
					if(temp.equals("deposit")) {
						transactionType = 1;
						String amountTemp = scanner.next();
						//cuts the dollar sign
						amountTemp = amountTemp.substring(1);	
						//cuts out the commas
						amount = Integer.valueOf(amountTemp.replaceAll(",", "").toString());					
					} else if(temp.equals("withdrawal")) {
						transactionType = 2;
						String amountTemp = scanner.next();
						//cuts the dollar sign
						amountTemp = amountTemp.substring(1);	
						//cuts out the commas
						amount = Integer.valueOf(amountTemp.replaceAll(",", "").toString());				
					} else {
						transactionType = 3;
						amount = 0;
					}	
					//calls the only constructor in job and fills it
					Job newJob = new Job(emp, timer, transactionType,
							amount , config[transactionType], config[4], config[5]);
					list.add(newJob);	
				}			
			}
		}	
		scanner.close();
		return list;
	}
	
	/**
	 * 
	 */
	public boolean isDone(int timer) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		boolean isDone = false;
		String temp; 
		int i = 0;
		while(scanner.hasNext()) {
			temp = scanner.next();
			if(temp.equals("Time")) {
				i++;
			}
		}
		scanner.close();
		Scanner scanner2 = null;
		try {
			scanner2 = new Scanner(new File(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(scanner2.hasNext()) {
			temp = scanner2.next();
			if(temp.equals("Time")) {
				temp = scanner2.next();
				int size = temp.length();
				temp = temp.substring(0, size-1);
				int x = Integer.parseInt(temp);
				for(int j=0; j<timer; j++) {
					if(x ==j) {
						i--;
					}
				}
			}
		}
		if(i==0) {
			isDone = true;
		}
		scanner2.close();
		return isDone;
	}
	
	public int getConfig(int x) {
		return config[x];
	}
}

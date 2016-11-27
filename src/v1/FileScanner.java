package v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class FileScanner {
	
	private String data;
	//Contains in this particular order: M, T_d, T_w, T_b, T_in, T_out
	private int[] config;
	private Company[] companies;
	
	/**
	 * Sets up the reader and asks which configuration should be run
	 */
	public FileScanner(int chosenFileNumber) {
		boolean fileExists;
		boolean printStream = true;
		//If Option 'Other' is selected in GUI, this manual selection will run in the console.
		if (chosenFileNumber == 4) {
			Scanner keyboard = new Scanner(System.in);
			do {
				System.out.println("Which manual configuration should be run? (If in doubt, use 1, 2 or 3!)");
				while(!keyboard.hasNextInt()) {
					System.out.println("This is not a number.");
					keyboard.next();
				}
				chosenFileNumber = keyboard.nextInt();
				
				data = "resources/config" + chosenFileNumber + ".txt";
				File varTmpDir = new File(data);
				fileExists = varTmpDir.exists();
				if (!fileExists)
					System.out.println("This is not a valid file name.");
			} while(fileExists == false);
			
			System.out.println("File exists!");
			System.out.println("Do you wish to print results in Console or Frame?");
			System.out.println("(Enter 'console' or 'frame')");
			
			String manualConfigRun;
			boolean manualConfigRunBool;
			
			do {
				manualConfigRun = keyboard.next();
				
				if (manualConfigRun.equals("console") || manualConfigRun.equals("frame"))
				{
					manualConfigRunBool = true;
					
					switch(manualConfigRun) {
					   case "console" :
						   printStream = false;
					      break;
					   
					   default : 
					      break;
					}
				}
				else
				{
					manualConfigRunBool = false;
					System.out.println("This is not a valid selection. Please try again.");
					System.out.println("Hint: enter console or frame.");
				}
			} while(manualConfigRunBool == false);
			
			keyboard.close();
		}
		
		data = "resources/config" + chosenFileNumber + ".txt";
		
		BankFrame.printStream(printStream);
		setup();
		createEconomy();

	}

	
	/**
	 * fills the reader class with more details so main can pass it on to bank.
	 * reads the config file and retrieves all the configuration variables.
	 */
	private void setup() {
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
					scanner.next();
				}
				break;
			}
		}
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
					temp = scanner.next(); //takes the next value and cuts out the companypart
					size = temp.length();
					int employer = Integer.parseInt((temp.substring(7, size-1)));
					Employee emp = new Employee(id, companies[(employer-1)]);		
					int transactionType;
					int amount = 0;
					temp = scanner.next();
					if(temp.equals("deposit")) {
						transactionType = 1;
						String amountTemp = scanner.next();
						amountTemp = amountTemp.substring(1); //cuts the dollar sign
						amount = Integer.valueOf(amountTemp.replaceAll(",", "").toString()); //cuts out the commas			
					} else if(temp.equals("withdrawal")) {
						transactionType = 2;
						String amountTemp = scanner.next();	
						amountTemp = amountTemp.substring(1); //cuts the dollar sign	
						amount = Integer.valueOf(amountTemp.replaceAll(",", "").toString()); //cuts out the commas				
					} else {
						transactionType = 3;
						amount = 0;
					}	
					Job newJob = new Job(emp, timer, transactionType,
							amount , config[transactionType], config[4], config[5]);
					list.add(newJob);	
				} else if(x>timer) {
					break;
				}
			}
		}	
		scanner.close();
		return list;
	}
	
	/**
	 * Checks if there are more jobs to do in the .txt file
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

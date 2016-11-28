package v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

public class FileScanner {
	
	private String data;
	private int[] config; //Contains in this particular order: M, T_d, T_w, T_b, T_in, T_out
	private Company[] companies;
	
	/**
	 * Sets up the reader and asks which configuration should be run
	 */
	public FileScanner(int chosenFileNumber) {
		boolean fileExists;
		boolean printStream = true;
		
		//If Option 'Other' is selected in GUI, this manual selection will run in the console.
		if (chosenFileNumber == 4) {
			Scanner keyboard = new Scanner(System.in); //open keyboard for scanning
			do {
				System.out.println("Which manual configuration should be run? (If in doubt, use 1, 2 or 3!)");
				while(!keyboard.hasNextInt()) { //if input is not a number, do the following
					System.out.println("This is not a number.");
					keyboard.next();
				}
				chosenFileNumber = keyboard.nextInt();
				
				data = "resources/config" + chosenFileNumber + ".txt"; //File name
				File varTmpDir = new File(data);
				fileExists = varTmpDir.exists(); //Checks if filename with given input exists
				if (!fileExists) //if input does not give a valid file name.
					System.out.println("This is not a valid file name.");
			} while(fileExists == false);
			
			System.out.println("File exists!");
			System.out.println("Do you wish to print results in Console or Frame?");
			System.out.println("(Enter 'console' or 'frame')");
			
			/* Once the file has been found, the user is asked whether 
		 	he wants to run the rest of the program in the console or in the frame */
			String manualConfigRun;
			boolean manualConfigRunBool;
			
			do {
				manualConfigRun = keyboard.next();
				
				//Once user enters console or frame, do switch case to determine which one was chosen.
				if (manualConfigRun.equals("console") || manualConfigRun.equals("frame"))
				{
					manualConfigRunBool = true;
					
					switch(manualConfigRun) {
					   case "console" :
						   printStream = false;
					      break;
					   
					   default : //default is run on frame
					      break;
					}
				}
				//If user didn't pick valid selection, keep running until they do.
				else
				{
					manualConfigRunBool = false;
					System.out.println("This is not a valid selection. Please try again.");
					System.out.println("Hint: enter console or frame.");
				}
			} while(manualConfigRunBool == false);
			
			keyboard.close();
		}
		
		data = "resources/config" + chosenFileNumber + ".txt"; // File name
		
		//Starts up the rest of the program
		BankFrame.printStream(printStream);
		setup();
		createEconomy();

	}

	
	/**
	 * Fills the reader class with more details so main can pass it on to bank.
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
		scanner.close();
	}
	
	/**
	 * Searches the config-file for company entries to count how many there are, 
	 * creates an array depending on the result, and then searches a second time
	 * to fill the array with company-instances and their corresponding balances.
	 */
	private void createEconomy() {
		Scanner scanner = null; //used for counting how many companies there are
		try {
			scanner = new Scanner(new File(data));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String temp; 
		int i = 0;
		while(scanner.hasNext()) {
			temp = scanner.next();
			if(temp.startsWith("Time")) { //if this occurs, the configuration details have all been read
				break;
			}
			if(temp.startsWith("Company")) {
				i++;
			}
		}
		scanner.close();
		companies = new Company[i];
		Scanner scanner2 = null; //used to fill the array
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
				temp = temp.substring(1); //cuts the dollar sign	
				int balanceInt = Integer.valueOf(temp.replaceAll(",", "").toString()); //cuts out the commas	
				Company corporation = new Company(j, balanceInt); //creates company instance
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
				if(x == timer) { //if the time in the file corresponds with the parameter, a job instance is created
					scanner.next();
					int id = Integer.parseInt(scanner.next());
					temp = scanner.next(); //takes the next value and cuts out the company-part
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
				} else if(x>timer) { //since the config is in chronological order, search can be interrupted once x is bigger than the parameter
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
		Scanner scanner = null; //counts all the jobs in the file
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
		Scanner scanner2 = null; //counts, with timer, how many jobs in the file are already done
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

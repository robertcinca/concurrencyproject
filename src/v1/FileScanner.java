package v1;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Properties;
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
	
	private Scanner reader;
	//Contains in this particular order: M, T_d, T_w, T_b, T_in, T_out
	private int[] config;
	private LinkedList<Company> companies;
	
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
		String dat = "config" + configNo + ".txt";
		
		try {
			reader = new Scanner(new File("resources/" + dat));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * fills the reader class with more details so main can pass it on to bank.
	 * reads the config file and retrieves all the configuration variables.
	 */
	public void setup() {
		config = new int[6];
		String temp;
		while(reader.hasNext()) {
			temp = reader.next();
			if(temp.equals("M")) {
				for(int j=0; j<6; j++) {
					config[j] = Integer.parseInt(reader.next());
					if(j!=5) {
						reader.next();
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
		companies = new LinkedList<Company>();
		String temp; 
		int i = 1;
		while(reader.hasNext()) {
			temp = reader.next();
			if(temp.equals("Time")) {
				break;
			}
			System.out.println(temp);
			if(temp.startsWith("Company")) {
				reader.next();
				String balance = reader.next();
				//cuts the dollar sign
				balance = balance.substring(1);	
				//cuts out the commas
				int balanceInt = Integer.valueOf(balance.replaceAll(",", "").toString());	
				Company corporation = new Company(i, balanceInt);
				companies.add(corporation);
				i++;
			}
		}
		for(Company company : companies) {
			System.out.println(company);
		}

	}
	
	/**
	 * This method searches the config file for events.
	 * @param timer: The current state of the timer
	 * @return a list which contains the events at timer, if any
	 */
	public PriorityBlockingQueue<Job> assignJobs(int timer) {
		Enumeration<?> enumerate = prop.propertyNames();
		String temp;
		PriorityBlockingQueue<Job> list = new PriorityBlockingQueue<Job>();
		while(enumerate.hasMoreElements()) {
			temp = (String) enumerate.nextElement();
			if(temp.equals("Time" + timer)) {
				String[] parts = prop.getProperty(temp).split("[.]");
				for(int j=0; j<parts.length; j++) {
					String[] parts2 = parts[j].split(",");	
					Employee emp = new Employee(Integer.parseInt(parts2[0].substring(8)),
							companies[Integer.parseInt(parts2[1].substring(7))-1]);	
					int transactionType;
					if(parts2[2].equals("deposit")) {
						transactionType = 1;
					} else if(parts2[2].equals("withdraw")) {
						transactionType = 2;
					} else {
						transactionType = 3;
					}
					//calls the only constructor in job and fills it
					Job newJob = new Job(emp, Integer.parseInt(temp.substring(4)), transactionType,
							Integer.parseInt(parts2[3]), config[transactionType], config[4], config[5]);
					list.add(newJob);
				}			
			}
		}	
		return list;
	}
	
	public boolean isDone(int timer) {
		boolean isDone = false;
		String temp; 
		Enumeration<?> enumerate1 = prop.propertyNames();	
		int i = 0;
		while(enumerate1.hasMoreElements()) {
			temp = (String) enumerate1.nextElement();
			if(temp.startsWith("Time")) {
				i++;
			}
		}
		Enumeration<?> enumerate2 = prop.propertyNames();
		while(enumerate2.hasMoreElements()) {
			temp = (String) enumerate2.nextElement();
			for(int j=0; j<timer; j++) {
				if(temp.equals("Time" + j)) {
					i--;
				}
			}
		}
		if(i==0) {
			isDone = true;
		}
		return isDone;
	}
	
	public int getConfig(int x) {
		return config[x];
	}
}

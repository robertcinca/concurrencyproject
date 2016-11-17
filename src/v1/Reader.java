package v1;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author Lorenz
 * Don't touch code, magic!
 */

public class Reader {
	
	private Properties prop;
	//Contains in this particular order: M, T_d, T_w, T_b, T_in, T_out
	private int[] config;
	private Company[] companies;
	
	/**
	 * Sets up the reader and asks which configuration should be run
	 */
	public Reader() {
		Scanner keyboard = new Scanner(System.in);
		int configNo;
		do {
			System.out.println("Which configuration should be run? (1-2)");
			while(!keyboard.hasNextInt()) {
				System.out.println("This is not a number.");
				keyboard.next();
			}
			configNo = keyboard.nextInt();
		} while(configNo<=0);
		keyboard.close();
		prop = new Properties();
		InputStream input = null;		
		try {
			String propFileName = "config" + configNo + ".properties";
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (input != null) {
				prop.load(input);
			} else {
				throw new FileNotFoundException();
			}	
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * fills the reader class with more details so main can pass it on to bank.
	 * reads the config file and retrieves all the configuration variables.
	 */
	public void setup() {
		config = new int[6];	
		config[0] = Integer.parseInt(prop.getProperty("M"));
		config[1] = Integer.parseInt(prop.getProperty("T_d"));
		config[2] = Integer.parseInt(prop.getProperty("T_w"));
		config[3] = Integer.parseInt(prop.getProperty("T_b"));
		config[4] = Integer.parseInt(prop.getProperty("T_in"));
		config[5] = Integer.parseInt(prop.getProperty("T_out"));	
		createEconomy();
	}
	
	/**
	 * Searches the config for company entries, creates an array, and fills it with companies and their corresponding balance
	 */
	private void createEconomy() {
		Enumeration<?> enumerate = prop.propertyNames();
		String temp; 
		int i = 0;
		while(enumerate.hasMoreElements()) {
			temp = (String) enumerate.nextElement();
			if(temp.startsWith("Company")) {
				i++;
			}
		}
		companies = new Company[i];
		for(int j=0; j<companies.length; j++) {
			int balance = Integer.parseInt(prop.getProperty("Company" + (j+1)));
			Company corporation = new Company(j+1, balance);
			companies[j] = corporation;
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
			if(temp.startsWith("Time" + timer)) {
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
							Integer.parseInt(parts2[3]), config[transactionType]);
					list.add(newJob);
				}			
			}
		}	
		return list;
	}
	
	public int getConfig(int x) {
		return config[x];
	}
}

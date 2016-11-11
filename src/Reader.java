import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Lorenz
 * Don't touch code, magic!
 */

public class Reader {
	
	Properties prop;
	
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
	
	public int[] setup() {
		int[] config = new int[6];	
		config[0] = Integer.parseInt(prop.getProperty("M"));
		config[1] = Integer.parseInt(prop.getProperty("T_d"));
		config[2] = Integer.parseInt(prop.getProperty("T_w"));
		config[3] = Integer.parseInt(prop.getProperty("T_b"));
		config[4] = Integer.parseInt(prop.getProperty("T_in"));
		config[5] = Integer.parseInt(prop.getProperty("T_out"));
		return config;
	}
	
	public Company[] createEconomy() {
		Enumeration<?> enumerate = prop.propertyNames();
		String temp; 
		int i = 0;
		while(enumerate.hasMoreElements()) {
			temp = (String) enumerate.nextElement();
			if(temp.startsWith("Company")) {
				i++;
				
				System.out.println(i);
			}
		}
		Company[] companies = new Company[i];
		for(int j=0; j<companies.length; j++) {
			int balance = Integer.parseInt(prop.getProperty("Company" + (j+1)));
			Company corporation = new Company(j+1, balance);
			companies[j] = corporation;
		}
		return companies;
	}
	
	public List<Job> assignJobs(Company[] employer) {
		List<Job> jobs = new LinkedList<Job>();			
		Enumeration<?> enumerate = prop.propertyNames();
		String temp;
		int i = 0;
		while(enumerate.hasMoreElements()) {
			temp = (String) enumerate.nextElement();
			if(temp.startsWith("Time")) {
				String[] parts = prop.getProperty(temp).split("[.]");
				for(int j=0; j<parts.length; j++) {
					String[] parts2 = parts[j].split(",");	
					Employee emp = new Employee(Integer.parseInt(parts2[0].substring(8)), employer[Integer.parseInt(parts2[1].substring(7))-1]);
					Job newJob = new Job(emp, Integer.parseInt(temp.substring(4)), parts2[2], Integer.parseInt(parts2[3]));
					jobs.add(newJob);
				}
				i++;	
				System.out.println(i);
			}
		}	
		System.out.println("readerCheck1");			
		return jobs;
	}
	
}

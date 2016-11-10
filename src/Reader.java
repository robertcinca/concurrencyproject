import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Scanner;

public class Reader {

	public int input() {
		Scanner keyboard = new Scanner(System.in);
		int temp;
		do {
			System.out.println("Please enter a natural number greater than 0!");
			while(!keyboard.hasNextInt()) {
				System.out.println("This is not a number.");
				keyboard.next();
			}
			temp = keyboard.nextInt();
		} while(temp<=0);
		keyboard.close();
		return temp;
	}
	
	public int[] setup(int configNo) {
		int[] config = new int[6];
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String propFileName = "config" + configNo + ".properties";
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (input != null) {
				prop.load(input);
			} else {
				throw new FileNotFoundException();
			}
			config[0] = Integer.parseInt(prop.getProperty("M"));
			config[1] = Integer.parseInt(prop.getProperty("T_d"));
			config[2] = Integer.parseInt(prop.getProperty("T_w"));
			config[3] = Integer.parseInt(prop.getProperty("T_b"));
			config[4] = Integer.parseInt(prop.getProperty("T_in"));
			config[5] = Integer.parseInt(prop.getProperty("T_out"));	
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
		return config;
	}
	
	public Job[] assign(int configNo) {
		Job[] jobs = null;
		Properties prop = new Properties();
		Enumeration enumerate = prop.propertyNames();
		InputStream input = null;
		try {
			String propFileName = "config" + configNo + ".properties";
			input = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (input != null) {
				prop.load(input);
			} else {
				throw new FileNotFoundException();
			}
		
			int i = 0;
			
			/*
			 * shit doesnt work yet but getting there
			 */
			while(enumerate.hasMoreElements()) {
				if(enumerate.nextElement().toString().startsWith("Time")) {
					i++;	
					System.out.println(i);
				}
			}
				String[] foos = new String[i];
				foos = prop.getProperty("foo").split(".");
				
			
		jobs = new Job[0];
			
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
		return jobs;
	}
}

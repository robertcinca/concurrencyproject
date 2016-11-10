import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Reader {

	public int[] setup() {
		int[] config = new int[6];
		Properties prop = new Properties();
		InputStream input = null;
		try {
			String propFileName = "config1.properties";
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
}

package v1;

import java.awt.EventQueue;
import java.io.File;
import java.util.ArrayList;

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
//	
//	public void restartApplication()
//	{
//	  final String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";
//	  final File currentJar = new File(MyClassInTheJar.class.getProtectionDomain().getCodeSource().getLocation().toURI());
//
//	  /* is it a jar file? */
//	  if(!currentJar.getName().endsWith(".jar"))
//	    return;
//
//	  /* Build command: java -jar application.jar */
//	  final ArrayList<String> command = new ArrayList<String>();
//	  command.add(javaBin);
//	  command.add("-jar");
//	  command.add(currentJar.getPath());
//
//	  final ProcessBuilder builder = new ProcessBuilder(command);
//	  builder.start();
//	  System.exit(0);
//	}
}

package v1;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;


public class BankFrame {

	JFrame frame;
	JPanel center;
	JPanel north;
	private static JTextArea jTextArea1 = new JTextArea(); //Creates text area to print messages
	private static JScrollPane scroll = new JScrollPane(jTextArea1, 
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); //Creates scroll feature for text area window
	private final JButton btnRestartProgram = new JButton("Exit Program");
	
	
	/**
	 * Launch the application. Only needed if you want to run/test the Interface on its own.
	 */
//	public static void main(String[] args) {	
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					BankFrame window = new BankFrame();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the application.
	 */
	public BankFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame(); //create the frame
		frame.setTitle("Bank Simulator - Control Panel"); //frame title
		center = new JPanel(); //create panels for dynamic adjustment of window
		center.setLayout(new BorderLayout());
		north = new JPanel();
		north.setLayout(new BorderLayout());
		center.add(scroll, BorderLayout.CENTER);
		frame.setBounds(100, 100, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnStartTheBusiness = new JButton("Start the Business Day!"); //Create launch button
		btnStartTheBusiness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { //when user clicks on the Start the Business Day button, the code below executes
				
				String[] fileNumber = { "1","2","3","Other" }; //Options for what files can be selected

				//Function 'Alert-style' box that gives user choice to select which program to run.
				String chosenNumber = (String) JOptionPane.showInputDialog(frame, 
				        "Which configuration should be run?",
				        "Choose a Number",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        fileNumber, 
				        fileNumber[0]);
				
				if (chosenNumber == "Other"){ //Not file 1,2 or 3, will revert to manual mode
					System.out.println("Entering manual configuration mode...reverting to console use");
					System.out.println("Please use console to manually enter a file number.");
					chosenNumber = "4"; 
				}
				if (chosenNumber == null) //User cancelled file selection, will revert to manual mode
				{
					System.out.println("WARNING: File Choosing Operation Cancelled!");
					System.out.println("Entering manual configuration mode...reverting to console use");
					System.out.println("Please use console to manually enter a file number.");
					chosenNumber = "4"; 
				}
				
				int chosenFileNumber = Integer.parseInt(chosenNumber); //convert from string to int
				run(chosenFileNumber); //To run program
			}
		});
		
		//Button properties and design
		btnStartTheBusiness.setForeground(Color.BLUE);
		btnStartTheBusiness.setBackground(Color.PINK);
		btnStartTheBusiness.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnStartTheBusiness.setBounds(31, 6, 182, 54);
		north.add(btnStartTheBusiness, BorderLayout.WEST);
		
		btnRestartProgram.addActionListener(new ActionListener() { //create Restart button
			public void actionPerformed(ActionEvent e) { //if restart button clicked
				String[] restartBox = { "Return to program","Exit program"}; //options for this button

				//Function 'Alert-style' box that gives user choice to select either to return to or exit program.
				String restartAnswer = (String) JOptionPane.showInputDialog(frame, 
				        "How do you wish to proceed?",
				        "WARNING!",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        restartBox, 
				        restartBox[0]); 
				if (restartAnswer == "Exit program"){
					System.exit(0);
				}
			}
		});
		
		//Restart button properties
		btnRestartProgram.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnRestartProgram.setForeground(Color.RED);
		btnRestartProgram.setBounds(242, 6, 182, 54);
		north.add(btnRestartProgram, BorderLayout.EAST);
		
		//Frame properties
		frame.add(center, BorderLayout.CENTER);
		frame.add(north, BorderLayout.NORTH);
	}
	
	/**
	 * Takes file input from user, passes it to FileScanner class for file parsing and starting the program
	 * @param chosenFileNumber
	 */
	public void run(int chosenFileNumber)
    {
		jTextArea1.setText(null);
		FileScanner reader = new FileScanner(chosenFileNumber);
		Bank bank = new Bank(reader);
		System.out.println("Bank is now open!");
		bank.doBusiness();
		System.out.println("Bank is now closed");
                    
    }
	
	/**
	 * Determines whether to print messages in frame or console.
	 * @param printStream
	 */
	public static void printStream(boolean printStream)
	{
		if (printStream == true)
		{	
			redirectSystemStreams(); //Redirects print statements
		}
		else {
			System.out.println("PRINTING TO CONSOLE");
			jTextArea1.append("PRINTING TO CONSOLE\n");
			jTextArea1.append("Check console for program execution");
		}
		
	}
	
	  /**
	   * The following codes set where the text from console get redirected. In this case, jTextArea1    
	   */
	  private static void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
				jTextArea1.setEditable(false);
				jTextArea1.append(text);        
	      }
	    });
	  }
	 
	  /**
	   * Methods that do the Redirect to jTextArea1.
	   * Forces program to print to frame instead of console. 
	   */
	  private static void redirectSystemStreams() {
	    OutputStream out = new OutputStream() {
	      @Override
	      public void write(int b) throws IOException {
	        updateTextArea(String.valueOf((char) b));
	      }
	 
	      @Override
	      public void write(byte[] b, int off, int len) throws IOException {
	        updateTextArea(new String(b, off, len));
	      }
	 
	      @Override
	      public void write(byte[] b) throws IOException {
	        write(b, 0, b.length);
	      }
	    };
	 
	    System.setOut(new PrintStream(out, true));
	    System.setErr(new PrintStream(out, true));
	  }
	  
}

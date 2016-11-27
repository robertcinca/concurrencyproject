package v1;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;


public class BankFrame {

	JFrame frame;
	private static JTextArea jTextArea1 = new JTextArea();
	private static JScrollPane scroll = new JScrollPane(jTextArea1, 
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
			JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	private final JButton btnRestartProgram = new JButton("Exit Program");
	
	
	/*
	 * Launch the application. Only needed if you want to run the Interface on its own.
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
		frame = new JFrame();
		frame.getContentPane().add(scroll);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		//Create launch button
		JButton btnStartTheBusiness = new JButton("Start the Business Day!");
		btnStartTheBusiness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String[] fileNumber = { "1","2","3","Other" };

				String chosenNumber = (String) JOptionPane.showInputDialog(frame, 
				        "Which configuration should be run?",
				        "Choose a Number",
				        JOptionPane.QUESTION_MESSAGE, 
				        null, 
				        fileNumber, 
				        fileNumber[0]);
				
				//Not file 1,2 or 3
				if (chosenNumber == "Other"){
					System.out.println("Entering manual configuration mode...reverting to console use");
					System.out.println("Please use console to manually enter a file number.");
					chosenNumber = "4"; 
				}
				if (chosenNumber == null)
				{
					System.out.println("WARNING: File Choosing Operation Cancelled!");
					System.out.println("Entering manual configuration mode...reverting to console use");
					System.out.println("Please use console to manually enter a file number.");
					chosenNumber = "4"; 
				}
				
				int chosenFileNumber = Integer.parseInt(chosenNumber);
				run(chosenFileNumber); //To run program
			}
		});
		btnStartTheBusiness.setForeground(Color.BLUE);
		btnStartTheBusiness.setBackground(Color.PINK);
		btnStartTheBusiness.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnStartTheBusiness.setBounds(31, 6, 182, 54);
		frame.getContentPane().add(btnStartTheBusiness);
		
		btnRestartProgram.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] restartBox = { "Return to program","Exit program"};

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
		btnRestartProgram.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnRestartProgram.setForeground(Color.RED);
		btnRestartProgram.setBounds(242, 6, 182, 54);
		frame.getContentPane().add(btnRestartProgram);
		
	}
	
	public void run(int chosenFileNumber)
    {
		FileScanner reader = new FileScanner(chosenFileNumber);
		Bank bank = new Bank(reader);
		
		System.out.println("Bank is now open!");
		bank.doBusiness();
		System.out.println("Bank is now closed");
                    
    }
	
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
	
	//The following codes set where the text from console get redirected. In this case, jTextArea1    
	  private static void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
				
				//Create text area with bank data when button is pressed.
				scroll.setBounds(6, 62, 438, 181);	
				//frame.add(scroll);
				
				jTextArea1.setEditable(false);
				jTextArea1.append(text);
	        
	      }
	    });
	  }
	 
	//Methods that do the Redirect to jTextArea1. 
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

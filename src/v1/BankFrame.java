package v1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;


public class BankFrame {

	JFrame frame;
	private JTextArea jTextArea1 = new JTextArea();
	private JScrollPane scroll;
	
	/**
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
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		JButton btnStartTheBusiness = new JButton("Start the Business Day!");
		btnStartTheBusiness.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jTextArea1.setBounds(6, 62, 438, 181);
				frame.getContentPane().add(jTextArea1);
				jTextArea1.setEditable(false);

				run();
			}
		});
		btnStartTheBusiness.setForeground(Color.BLUE);
		btnStartTheBusiness.setBackground(Color.PINK);
		btnStartTheBusiness.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnStartTheBusiness.setBounds(126, 6, 182, 54);
		frame.getContentPane().add(btnStartTheBusiness);
		


	}
	
	public void run()
    {
        //redirectSystemStreams();
		FileScanner reader = new FileScanner();
		Bank bank = new Bank(reader);
		
		//BankFrame frame = new BankFrame();
		//frame.initialize();
		//frame.redirectSystemStreams();

		
		redirectSystemStreams(); 
		System.out.println("Bank is now open!");
		bank.doBusiness();
		System.out.println("Bank is now closed");
                    
    }
	
	//The following codes set where the text get redirected. In this case, jTextArea1    
	  private void updateTextArea(final String text) {
	    SwingUtilities.invokeLater(new Runnable() {
	      public void run() {
	        jTextArea1.append(text);
	        
	      }
	    });
	  }
	 
	//Followings are The Methods that do the Redirect, you can simply Ignore them. 
	  private void redirectSystemStreams() {
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

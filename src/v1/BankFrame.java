package v1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class BankFrame {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
				JOptionPane.showMessageDialog(null, "Test 123");
			}
		});
		btnStartTheBusiness.setForeground(Color.BLUE);
		btnStartTheBusiness.setBackground(Color.PINK);
		btnStartTheBusiness.setFont(new Font("Lucida Grande", Font.PLAIN, 14));
		btnStartTheBusiness.setBounds(158, 85, 182, 54);
		frame.getContentPane().add(btnStartTheBusiness);
	}
}

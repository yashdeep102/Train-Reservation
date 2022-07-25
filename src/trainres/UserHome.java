package trainres;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.Color;

public class UserHome extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserHome frame = new UserHome();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserHome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 469, 629);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Book Ticket");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(255, 127, 80));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookTicket bt = new BookTicket();
				bt.setLocationRelativeTo(null);
				bt.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnNewButton.setBounds(127, 110, 202, 66);
		contentPane.add(btnNewButton);
		
		JButton btnSavedPassengers = new JButton("Saved Passengers");
		btnSavedPassengers.setForeground(new Color(255, 255, 255));
		btnSavedPassengers.setBorder(null);
		btnSavedPassengers.setBackground(new Color(255, 127, 80));
		btnSavedPassengers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				setVisible(false);
				SavedPassengers sp = new SavedPassengers();
				sp.setLocationRelativeTo(null);
				sp.setVisible(true);
			}
		});
		btnSavedPassengers.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnSavedPassengers.setBounds(127, 256, 202, 66);
		contentPane.add(btnSavedPassengers);
		
		JButton btnBookedHistory = new JButton("Booked History");
		btnBookedHistory.setBorder(null);
		btnBookedHistory.setForeground(new Color(255, 255, 255));
		btnBookedHistory.setBackground(new Color(255, 127, 80));
		btnBookedHistory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				BookedHistory bk = new BookedHistory();
				bk.setLocationRelativeTo(null);
				bk.setVisible(true);
			}
		});
		btnBookedHistory.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnBookedHistory.setBounds(127, 416, 202, 66);
		contentPane.add(btnBookedHistory);
		
		JLabel lblNewLabel = new JLabel("User Id = " +  Welcome.user_id);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel.setBounds(38, 29, 103, 28);
		contentPane.add(lblNewLabel);
		
		
	}
}

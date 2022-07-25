package trainres;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class Summary extends JFrame {

	private JPanel contentPane;
	private JLabel lblNewLabel_1;
	private int distance;
	private int passengerCount;
	private String berth;
	private String trainCat;
	private int berthCharge;
	private int trainCatCharge;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Summary frame = new Summary();
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
	public Summary() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 372, 667);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Distance");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(43, 105, 86, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNoOfPassengers = new JLabel("No of passengers");
		lblNoOfPassengers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNoOfPassengers.setBounds(43, 158, 119, 22);
		contentPane.add(lblNoOfPassengers);
		
		JLabel lblBerth = new JLabel("Berth");
		lblBerth.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBerth.setBounds(43, 207, 86, 22);
		contentPane.add(lblBerth);
		
		JLabel lblTrainCategory = new JLabel("Train Category");
		lblTrainCategory.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTrainCategory.setBounds(43, 254, 119, 22);
		contentPane.add(lblTrainCategory);
		
		String query1 = "SELECT distance, berth FROM ticket WHERE pnr = ?";
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query1);){

			pst.setInt(1, BookTicket.pnr);
			ResultSet rst = pst.executeQuery();
			if(rst.next()) {
				distance = rst.getInt(1);
				berth = rst.getString(2);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
		lblNewLabel_1 = new JLabel("" + distance);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(248, 110, 45, 13);
		contentPane.add(lblNewLabel_1);
		
		String query2 = "SELECT count(*) FROM passenger WHERE pnr = ?";
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query2);){

			pst.setInt(1, BookTicket.pnr);
			ResultSet rst = pst.executeQuery();
			if(rst.next()) {
				passengerCount = rst.getInt(1);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
		JLabel lblNewLabel_2 = new JLabel("" + passengerCount);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(248, 163, 45, 13);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel(berth);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(248, 216, 45, 13);
		contentPane.add(lblNewLabel_3);
		
		
		String query3 = "SELECT t_category FROM ticket, train WHERE pnr = ? AND ticket.train_no = train.t_code;";
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query3);){

			pst.setInt(1, BookTicket.pnr);
			ResultSet rst = pst.executeQuery();
			if(rst.next()) {
				trainCat = rst.getString(1);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
		JLabel lblNewLabel_4 = new JLabel(trainCat);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblNewLabel_4.setBounds(248, 263, 100, 13);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Fare");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_5.setBounds(84, 395, 64, 22);
		contentPane.add(lblNewLabel_5);
		
		String query4 = "SELECT charge FROM ticket, berth_charge WHERE ticket.berth = berth_charge.berth AND pnr = ?";
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query4);){

			pst.setInt(1, BookTicket.pnr);
			ResultSet rst = pst.executeQuery();
			if(rst.next()) {
				berthCharge = rst.getInt(1);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
		String query5 = "SELECT charge FROM train_cat, (SELECT t_category FROM ticket, train WHERE train_no = t_code AND pnr = ?) as temp WHERE t_category = t_cat";
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query5);){

			pst.setInt(1, BookTicket.pnr);
			ResultSet rst = pst.executeQuery();
			if(rst.next()) {
				trainCatCharge = rst.getInt(1);
			}
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
		int fare = distance * passengerCount * berthCharge * trainCatCharge;
		JLabel lblNewLabel_6 = new JLabel("" + fare);
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel_6.setBounds(226, 400, 45, 13);
		contentPane.add(lblNewLabel_6);
		
		JButton btnNewButton = new JButton("Book Ticket");
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(255, 127, 80));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Ticket Booked");
				dispose();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(184, 516, 127, 21);
		contentPane.add(btnNewButton);
		
		
	}
}

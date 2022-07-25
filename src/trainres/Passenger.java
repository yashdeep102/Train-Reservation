package trainres;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class Passenger extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	public static int count = 0;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Passenger frame = new Passenger();
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
	public Passenger() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 307, 592);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField.setBounds(176, 67, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBorder(null);
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_1.setColumns(10);
		textField_1.setBounds(176, 123, 96, 19);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBorder(null);
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textField_2.setColumns(10);
		textField_2.setBounds(176, 169, 96, 19);
		contentPane.add(textField_2);
		
		JButton btnNewButton = new JButton("Summary");
		btnNewButton.setBorder(null);
		btnNewButton.setBackground(new Color(255, 127, 80));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Summary f = new Summary();
				f.setLocationRelativeTo(null);
				f.setVisible(true);
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton.setBounds(187, 476, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add Passenger");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(count < 4) {
				Passenger passenger = new Passenger();
				passenger.setLocationRelativeTo(null);
				passenger.setVisible(true);
				dispose();
				count++;
				
				String query = "INSERT INTO passenger (f_name, l_name, age, pnr) VALUES (?, ?, ?, ?)";
				try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query);){
					
					String fName = textField.getText();
					String lName = textField_1.getText();
					int age = Integer.parseInt(textField_2.getText());
					
					pst.setString(1, fName);
					pst.setString(2, lName);
					pst.setInt(3, age);
					pst.setInt(4, BookTicket.pnr);
					pst.executeUpdate();
					
				}catch(Exception e1){
					e1.printStackTrace();
				}
				
				}else {
					JOptionPane.showMessageDialog(null, "Only 4 Passengers allowed per ticket");
				}
			}
		});
		btnNewButton_1.setBounds(18, 476, 141, 21);
		contentPane.add(btnNewButton_1);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(18, 70, 78, 13);
		contentPane.add(lblNewLabel);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setBounds(18, 126, 78, 13);
		contentPane.add(lblLastName);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAge.setBounds(18, 169, 78, 19);
		contentPane.add(lblAge);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(18, 287, 254, 153);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				textField.setText(model.getValueAt(selectedRowIndex, 0).toString());
				textField_1.setText(model.getValueAt(selectedRowIndex, 1).toString());
				textField_2.setText(model.getValueAt(selectedRowIndex, 2).toString());
			}
		});
		scrollPane.setViewportView(table);
		
		JButton btnNewButton_2 = new JButton("Show saved passengers");
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query1 = "SELECT f_name AS 'First Name', l_name AS 'Last Name', age AS 'Age' FROM saved_passenger WHERE user_id = ?";
 				try(Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query1);) {
					pst.setInt(1, Welcome.user_id);
					ResultSet rst = pst.executeQuery();
					table.setModel(DbUtils.resultSetToTableModel(rst));
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
			}
		});
		btnNewButton_2.setBounds(18, 256, 254, 21);
		contentPane.add(btnNewButton_2);
	}
}

package trainres;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class SavedPassengers extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTable table;
	private int passengerID;
	
	public void refreshTable() {
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement("SELECT saved_passenger_id as ID, f_name as 'First Name', l_name as 'Last Name', age as 'Age' FROM saved_passenger WHERE user_id = ?");) {
			
			pst.setString(1, String.valueOf(Welcome.user_id));
			ResultSet rst = pst.executeQuery();

			table.setModel(DbUtils.resultSetToTableModel(rst));
		}catch(Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SavedPassengers frame = new SavedPassengers();
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
	public SavedPassengers() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 716);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnNewButton = new JButton("Add");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement("INSERT INTO saved_passenger (f_name, l_name, age, user_id) VALUES (?, ?, ?, ?)");){
					String fName = textField.getText();
					String lName = textField_1.getText();
					String age = textField_2.getText();
					
					if(fName.length() == 0 || lName.length() == 0 || age.length() == 0) {
						JOptionPane.showMessageDialog(null, "Passenger details can't be empty");
					}else {
					pst.setString(1, fName);
					pst.setString(2, lName);
					pst.setString(3, age);
					pst.setInt(4, Welcome.user_id);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "Passenger details saved");
					}
					
					textField.setText("");
					textField_1.setText("");
					textField_2.setText("");
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				refreshTable();
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(28, 492, 111, 25);
		contentPane.add(btnNewButton);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "DELETE FROM saved_passenger WHERE saved_passenger_id = ?";
				try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query);) {
					
					pst.setInt(1, passengerID);
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Passenger Details Deleted");
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
				refreshTable();
			}
		});
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDelete.setBounds(163, 492, 111, 25);
		contentPane.add(btnDelete);
		
		JButton btnUpdate = new JButton("Show");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement("SELECT saved_passenger_id as ID, f_name as 'First Name', l_name as 'Last Name', age as 'Age' FROM saved_passenger WHERE user_id = ?");) {
					
					pst.setString(1, String.valueOf(Welcome.user_id));
					ResultSet rst = pst.executeQuery();

					table.setModel(DbUtils.resultSetToTableModel(rst));
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
				
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdate.setBounds(294, 492, 111, 25);
		contentPane.add(btnUpdate);
		
		JButton btnBack = new JButton("Back");
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setBackground(new Color(255, 127, 80));
		btnBack.setBorder(null);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				UserHome().setVisible(true);
				dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBack.setBounds(294, 594, 111, 25);
		contentPane.add(btnBack);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(243, 71, 144, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBorder(null);
		textField_1.setColumns(10);
		textField_1.setBounds(243, 154, 144, 19);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBorder(null);
		textField_2.setColumns(10);
		textField_2.setBounds(243, 238, 144, 19);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel = new JLabel("First Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(28, 77, 87, 19);
		contentPane.add(lblNewLabel);
		
		JLabel lblLastName = new JLabel("Last Name");
		lblLastName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblLastName.setBounds(28, 156, 87, 17);
		contentPane.add(lblLastName);
		
		JLabel lblAge = new JLabel("Age");
		lblAge.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblAge.setBounds(28, 240, 87, 17);
		contentPane.add(lblAge);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(38, 296, 349, 163);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				passengerID = (Integer)model.getValueAt(selectedRowIndex, 0);
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{},
			},
			new String[] {
			}
		));
	}
}

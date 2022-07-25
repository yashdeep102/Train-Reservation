package trainres;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import java.awt.Color;

public class NewUser extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewUser frame = new NewUser();
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
	public NewUser() {
		
		Connection conn = DB.dbconnect();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 471, 555);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(293, 131, 96, 19);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("User Name");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(46, 121, 116, 34);
		contentPane.add(lblNewLabel);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPassword.setBounds(46, 221, 116, 34);
		contentPane.add(lblPassword);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.setBackground(new Color(255, 127, 80));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBorder(null);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					String userName = textField.getText();
					String password = String.valueOf(passwordField.getPassword());
					String passwordMatch = String.valueOf(passwordField_1.getPassword());

					if(userName.isBlank() || password.isBlank() || passwordMatch.isBlank()) {
						JOptionPane.showMessageDialog(null, "Fields can not be empty");

					}else if(password.equals(passwordMatch) == false) {
						JOptionPane.showMessageDialog(null, "Passwords don't match");
					}
					else {
						PreparedStatement pst = conn.prepareStatement("INSERT INTO user (user_name, password) VALUES (?, ?)");
						pst.setString(1, userName);
						pst.setString(2, password);
						pst.executeUpdate();
						JOptionPane.showMessageDialog(null, "User Added");
						
						textField.setText("");
						passwordField.setText("");
						passwordField_1.setText("");
						textField.requestFocusInWindow();
					}
					
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, e1);   //For throwing the SQL exception
				}
				
				
				
			}
		});
		btnNewButton.setBounds(46, 465, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancel");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton_1.setBorder(null);
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnNewButton_1.setBounds(303, 465, 85, 21);
		contentPane.add(btnNewButton_1);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(null);
		passwordField.setBounds(293, 231, 96, 19);
		contentPane.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBorder(null);
		passwordField_1.setBounds(293, 328, 96, 19);
		contentPane.add(passwordField_1);
		
		JLabel lblNewLabel_1 = new JLabel("Re-enter password");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(46, 321, 139, 28);
		contentPane.add(lblNewLabel_1);
	}
}

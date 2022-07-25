package trainres;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.JPanel;



public class Welcome {

	private JFrame frame;
	private JTextField textField;
	public static int user_id;
	private JLabel lblNewLabel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Welcome window = new Welcome();
					window.frame.setLocationRelativeTo(null);
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
	private JPasswordField passwordField;
	public Welcome() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(220, 220, 220));
		frame.setBounds(100, 100, 422, 567);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBorder(null);
		textField.setBounds(249, 178, 96, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("New user?");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				frame.setVisible(false);
				NewUser newUser = new NewUser();
				newUser.setLocationRelativeTo(null);
				newUser.setVisible(true);
			}
		});
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblNewLabel.setBounds(61, 424, 96, 42);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setBorder(null);
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setBackground(new Color(255, 127, 80));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query = "SELECT * FROM user WHERE user_name = ? AND password = ?";
				try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query);){
					
					String userName = textField.getText();
					String password = String.valueOf(passwordField.getPassword());   //for converting to string

					pst.setString(1, userName);
					pst.setString(2, password);
					ResultSet rst = pst.executeQuery();
					if(rst.next()) {
						user_id = rst.getInt(1);
						frame.dispose();
						UserHome userHome = new UserHome();
						userHome.setLocationRelativeTo(null);
						userHome.setVisible(true);
					}else {
						JOptionPane.showMessageDialog(null, "Incorrect User Name or Password");
					}
					
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnNewButton.setBounds(192, 431, 145, 29);
		frame.getContentPane().add(btnNewButton);
		
		passwordField = new JPasswordField();
		passwordField.setBorder(null);
		passwordField.setBounds(249, 293, 96, 19);
		frame.getContentPane().add(passwordField);
		
		JLabel lblUserName = new JLabel("User Name");
		lblUserName.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblUserName.setBounds(39, 167, 96, 42);
		frame.getContentPane().add(lblUserName);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Consolas", Font.PLAIN, 14));
		lblPassword.setBounds(39, 282, 96, 42);
		frame.getContentPane().add(lblPassword);
		
		JLabel lblNewLabel_1 = new JLabel("Train Reservation");
		lblNewLabel_1.setFont(new Font("Verdana", Font.PLAIN, 22));
		lblNewLabel_1.setBounds(141, 23, 267, 68);
		frame.getContentPane().add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("image");
		lblNewLabel_2.setIcon(new ImageIcon(getClass().getResource("./image/train-1045.png")));
		lblNewLabel_2.setBounds(28, 23, 84, 79);
		frame.getContentPane().add(lblNewLabel_2);
		
		
		


	}
}

package trainres;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import java.awt.Color;

public class BookTicket extends JFrame {

	public static int pnr;
	private int trainNo;
	private String from;
	private String to;
	private int distance;
	private JPanel contentPane;
	private JComboBox comboBox;
	private JTable table;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookTicket frame = new BookTicket();
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
	public BookTicket() {
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 537, 599);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JComboBox comboBox = new JComboBox();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				
			}
		});
//		comboBox.setEditable(true);
		comboBox.setBounds(277, 109, 178, 21);
		contentPane.add(comboBox);
		String query1 = "SELECT stn_name FROM station";
		try(Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query1);) {
			ResultSet rst = pst.executeQuery();
			while(rst.next()) {
				comboBox.addItem((rst.getString(1)));
			}
			
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		
		
		JComboBox comboBox_1 = new JComboBox();
//		comboBox_1.setEditable(true);
		comboBox_1.setBounds(277, 178, 178, 21);
		contentPane.add(comboBox_1);
		String query2 = "SELECT stn_name FROM station";
		try(Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query2);) {
			ResultSet rst = pst.executeQuery();
			while(rst.next()) {
				comboBox_1.addItem((rst.getString(1)));
			}
			
		}catch(Exception e1) {
			JOptionPane.showMessageDialog(null, e1);
		}
		
		
		JLabel lblNewLabel = new JLabel("Source");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(51, 104, 101, 26);
		contentPane.add(lblNewLabel);
		
		JLabel lblTo = new JLabel("Destination");
		lblTo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTo.setBounds(51, 173, 101, 26);
		contentPane.add(lblTo);
		
		JButton btnNewButton = new JButton("Search ");
		btnNewButton.setBorder(null);
		btnNewButton.setForeground(new Color(0, 0, 0));
		btnNewButton.setBackground(new Color(255, 160, 122));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				from = comboBox.getSelectedItem().toString();				
				to = comboBox_1.getSelectedItem().toString();
				
				if(from.equals(to)) {
					JOptionPane.showMessageDialog(null, "Please select different stations for From and To");
				}else {
					
					String query3 = "WITH train_code(t_code) AS (SELECT b1.t_code FROM bridge b1 JOIN bridge b2 ON b1.t_code = b2.t_code WHERE b2.stn_code = (SELECT stn_code FROM station WHERE stn_name = ?) AND b1.stn_code = (SELECT stn_code FROM station WHERE stn_name = ?) AND b1.distance - b2.distance > 1) SELECT train_code.t_code AS 'Train No', t_name AS 'Train Name', 3ac AS '3AC', 2ac AS '2AC', sl AS 'Sleeper' FROM train_code, train WHERE train_code.t_code = train.t_code";
//					SELECT DISTINCT t_code FROM bridge WHERE stn_code IN ('CNB', 'LKO');
					try(Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query3);) {
						pst.setString(1, to);
						pst.setString(2, from);
						ResultSet rst = pst.executeQuery();
						table.setModel(DbUtils.resultSetToTableModel(rst));
					}catch(Exception e1) {
						JOptionPane.showMessageDialog(null, e1);
					}
				}
				
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNewButton.setBounds(206, 254, 85, 21);
		contentPane.add(btnNewButton);
		
		JButton btnNext = new JButton("Next");
		btnNext.setForeground(new Color(255, 255, 255));
		btnNext.setBorder(null);
		btnNext.setBackground(new Color(255, 127, 80));
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query1 = "SELECT distance - (SELECT distance FROM bridge WHERE t_code = ? AND stn_code = (SELECT stn_code FROM station WHERE stn_name = ?)) as distance FROM bridge WHERE t_code = ? AND stn_code = (SELECT stn_code FROM station WHERE stn_name = ?)";
				try(Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query1);) {
					pst.setInt(1, trainNo);
					pst.setString(2, from);
					pst.setInt(3, trainNo);
					pst.setString(4, to);
					ResultSet rst = pst.executeQuery();
					if(rst.next()) {
						distance = Math.abs(rst.getInt(1));
						dispose();
					}
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				
				String query2 = "INSERT INTO ticket (source, dest, train_no, distance, user_id, berth) VALUES (?, ?, ?, ?, ?, ?)";
				try(Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query2, Statement.RETURN_GENERATED_KEYS);) {
					
					String berth = null;
					if(rdbtnNewRadioButton.isSelected()) {
						berth = "sl";
					}else if(rdbtnNewRadioButton_1.isSelected()) {
						berth = "3ac";
					}else if(rdbtnNewRadioButton_2.isSelected()) {
						berth = "2ac";
					}
					
					pst.setString(1, from);
					pst.setString(2, to);
					pst.setInt(3, trainNo);
					pst.setInt(4, distance);
					pst.setInt(5, Welcome.user_id);
					pst.setString(6, berth);
					pst.executeUpdate();
					ResultSet rst = pst.getGeneratedKeys();
					if(rst.next()) {
					pnr = rst.getInt(1);
					}
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, e1);
				}
				
				Passenger p = new Passenger();
				p.setLocationRelativeTo(null);
				p.setVisible(true);
			}
		});
		btnNext.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnNext.setBounds(413, 505, 85, 21);
		contentPane.add(btnNext);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(51, 299, 404, 166);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				trainNo = (Integer)model.getValueAt(selectedRowIndex, 0);
			}
		});
		scrollPane.setViewportView(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		
		rdbtnNewRadioButton = new JRadioButton("Sleeper");
		rdbtnNewRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButton.isSelected()) {
					rdbtnNewRadioButton_1.setSelected(false);
					rdbtnNewRadioButton_2.setSelected(false);

				}
			}
		});
		rdbtnNewRadioButton.setBounds(51, 483, 85, 21);
		contentPane.add(rdbtnNewRadioButton);
		
		rdbtnNewRadioButton_1 = new JRadioButton("3AC");
		rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButton_1.isSelected()) {
					rdbtnNewRadioButton.setSelected(false);
					rdbtnNewRadioButton_2.setSelected(false);

				}
			}
		});
		rdbtnNewRadioButton_1.setBounds(138, 483, 53, 21);
		contentPane.add(rdbtnNewRadioButton_1);
		
		rdbtnNewRadioButton_2 = new JRadioButton("2AC");
		rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButton_2.isSelected()) {
					rdbtnNewRadioButton_1.setSelected(false);
					rdbtnNewRadioButton.setSelected(false);

				}
			}
		});
		rdbtnNewRadioButton_2.setBounds(216, 483, 103, 21);
		contentPane.add(rdbtnNewRadioButton_2);
	}
}

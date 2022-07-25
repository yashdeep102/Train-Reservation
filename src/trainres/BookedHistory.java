package trainres;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import javax.swing.JButton;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class BookedHistory extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private int pnr;
	
	public void refreshTable() {
		String query1 = "SELECT pnr as 'PNR', source as 'Source', dest as 'Destination', train_no as 'Train Number', distance as Distance, berth as Berth FROM ticket WHERE user_id = ?";
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query1);) {
			
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
					BookedHistory frame = new BookedHistory();
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
	public BookedHistory() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 517, 686);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String query2 = "DELETE FROM ticket WHERE pnr = ?";
				try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query2);) {
					
					pst.setInt(1, pnr);
					pst.executeUpdate();

					JOptionPane.showMessageDialog(null, "Ticket Cancelled");
				}catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2);
				}
				refreshTable();
			}
		});
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(204, 429, 111, 25);
		contentPane.add(btnCancel);
		
		JButton btnBack = new JButton("Back");
		btnBack.setBorder(null);
		btnBack.setForeground(new Color(255, 255, 255));
		btnBack.setBackground(new Color(255, 127, 80));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnBack.setBounds(360, 578, 111, 25);
		contentPane.add(btnBack);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 483, 260);
		contentPane.add(scrollPane);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectedRowIndex = table.getSelectedRow();
				pnr = (Integer)model.getValueAt(selectedRowIndex, 0);
			}
		});
		scrollPane.setViewportView(table);
		String query1 = "SELECT pnr as 'PNR', source as 'Source', dest as 'Destination', train_no as 'Train Number', distance as Distance, berth as Berth FROM ticket WHERE user_id = ?";
		try (Connection conn = DB.dbconnect(); PreparedStatement pst = conn.prepareStatement(query1);) {
			
			pst.setString(1, String.valueOf(Welcome.user_id));
			ResultSet rst = pst.executeQuery();

			table.setModel(DbUtils.resultSetToTableModel(rst));
		}catch(Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}
}

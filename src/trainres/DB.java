package trainres;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {
	java.sql.PreparedStatement pst;
	public static Connection dbconnect()
	{
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/train", "user", "password");
			return conn;
		}
		catch(Exception e2)
		{
			System.out.println(e2);
			return null;
		}
	}
}

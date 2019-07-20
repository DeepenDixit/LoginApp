package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectivity {
	public static Connection getConnection()
	{
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost/LoginJFX","root","");
			return connection;
		}
		catch(Exception e)
		{
			System.out.println(e);
			return null;
		}
	}
}

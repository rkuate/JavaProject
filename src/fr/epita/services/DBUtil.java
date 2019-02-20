package fr.epita.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class DBUtil {
	
	/**
	 * Represents the connection string to the database
	 */
	private static final String CONN_STRING = "jdbc:mysql://localhost/quiz_db";
	
	/**
	 * This function will create an instance of the Class Connection 
	 * @param dbType An enum representing the type of database
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return A instance of the Connection Class
	 * @throws SQLException Handle the SQL Excpetion thrown by the closure of the Result Set
	 */
	public static Connection getConnection (DBType dbType, String username, String password) throws SQLException {
		
		switch (dbType) {
		case MYSQL:
			return DriverManager.getConnection(CONN_STRING, username, password);
			
		default:
			return null;
		}
		
	}
	
	/**
	 * This function will print error messages corresponding to an SQLException
	 * @param e An SQL Exception representing the SQLException to process
	 */
	public static void processException(SQLException e) {
		
		System.out.println("Error message " + e.getMessage());
		System.out.println("Error code " + e.getErrorCode() );
		System.out.println("SQLException " + e.getSQLState() );
		
	}
	

}

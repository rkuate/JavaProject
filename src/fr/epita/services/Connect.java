package fr.epita.services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class Connect {
	
	/**
	 * This function will connect to the database
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @throws SQLException Handle the SQLException thrown by the Connection
	 */
	public static void connect(String username, String password) throws SQLException {

		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				Statement statement = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				
				){
			System.out.println("Connected!");
		
		} catch (SQLException e) {
			DBUtil.processException(e);			
			System.err.println(e);
		} 
	}

}

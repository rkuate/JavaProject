package fr.epita.jdbcDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.Statement;

import fr.epita.datamodel.MCQChoice;
import fr.epita.datamodel.MCQQuestion;
import fr.epita.services.DBType;
import fr.epita.services.DBUtil;
import fr.epita.services.End;
import fr.epita.services.InputHelper;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class MCQChoiceDAO {
	
	/**
	 * Represents the query to use for creating a MCQChoice
	 */
	private static final String CREATE_C = "INSERT INTO mcqchoice ( mcq_id , choice_text , choice_value ) Values ( ? , ? , ? )";
	
	/**
	 * Represents the query to use for updating a MCQChoice
	 */
	private static final String UPDATE_C = "UPDATE mcqchoice SET choice_text = ? , choice_value = ? WHERE choice_id = ?";
	
	/**
	 * Represents the query to use for deleting a MCQChocie
	 */
	private static final String DELETE_C = "DELETE FROM mcqchoice WHERE choice_id = ?";
	
	/**
	 * Represents the query to use for searching a MCQChoice
	 */
	private static final String SEARCH_C = "SELECT * FROM mcqchoice WHERE mcq_id = ?";
	
	/**
	 * This function will create the choices for the a given MCQQuestion and will store it the database
	 * @param new_mcq_id An int containing the id of the corresponding MCQQuestion
	 * @param lChoice A List containing the list of choices for the belonging MCQQuestion
	 * @param lValue A list containing the list of values of choices for the belonging MCQQuestion
	 * @param username A String containing the username of the user
 	 * @param password A String containing the password of the user
	 * @throws SQLException Handle the exception thrown by the closure of the Result Set
	 */
	public static void create (int new_mcq_id , List<String> lChoice, List<Boolean> lValue, String username , String password) throws SQLException {
		
		MCQChoice mcqchoice = new MCQChoice ();
		MCQQuestion mcqquestion = new MCQQuestion ();
		
		boolean result = true;
		ResultSet keys = null;
		
		mcqquestion.setIdMCQQuestion(new_mcq_id);
		
		while (!lChoice.isEmpty() && !lValue.isEmpty()) {
			
			mcqchoice.setChoice(lChoice.remove(0));
			mcqchoice.setValid(lValue.remove(0));
			
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
					PreparedStatement stmt = conn.prepareStatement(CREATE_C, Statement.RETURN_GENERATED_KEYS)
				) {
				
				stmt.setInt(1, mcqquestion.getIdMCQQuestion());
				stmt.setString(2, mcqchoice.getChoice());
				stmt.setBoolean(3, mcqchoice.isValid());
				
				int affected = stmt.executeUpdate();
				
				if (affected == 1) {
					keys = stmt.getGeneratedKeys();
					keys.next();
					int newKey = keys.getInt(1);
					mcqchoice.setIdMCQChoice(newKey);
				} else {
					System.err.println("Answer Successfully added");
					result = false;
				}
			} catch (SQLException e) {
				System.err.println(e);
				result = false;
			} finally{
				if (keys != null) keys.close();
			}
			End.endAnswer(result, "created" , mcqchoice.getIdMCQChoice());
			
		}
		
		
	}
	
	/**
	 * This function will update a choice in the database identified by the id passed as parameter. The function
	 * will replace actual values by the values gave by the user
	 * @param key An int containing the id of the of the MCQChoice to modify
	 * @param username A String containing the username of the user
	 * @param password A String containing the password of the user
	 * @throws SQLException Handle the exception thrown by the closure of the Result Set
	 */
	public static void update (int key, String username , String password) throws SQLException {
		
		MCQChoice mcqchoice = new MCQChoice ();
		
		boolean result = true;
		
		mcqchoice.setIdMCQChoice(key);
		mcqchoice.setChoice(InputHelper.getStringInput("Enter the new text of the choice: "));
		mcqchoice.setValid(InputHelper.getBooleanInput("Enter the new value of the choice: "));
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(UPDATE_C)) {
			
			stmt.setString(1, mcqchoice.getChoice());
			stmt.setBoolean(2, mcqchoice.isValid());
			stmt.setInt(2, mcqchoice.getIdMCQChoice());
			
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				
			} else {
				System.err.println("No rows affected");
				result = false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			result = false;
		}
		End.endAnswer(result, "updated", mcqchoice.getIdMCQChoice());
		
	}

	/**
	 * This function will delete the a MCQChoice in a the database corresponding to the id passed as parameter
	 * @param oldKey An int containing the id of the MCQChoice to delete
	 * @param username A String containing the username of the user
	 * @param password A String containing the password of the user
	 */
	public static void delete (int oldKey ,String username , String password) {
		
		MCQChoice mcqchoice = new MCQChoice() ;
		
		boolean result = true ;
		
		mcqchoice.setIdMCQChoice(oldKey);
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(DELETE_C)
							
	 ) {
			
			stmt.setInt(1, mcqchoice.getIdMCQChoice());
			
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				
			} else {
				System.err.println("No rows affected");
				result = false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			result = false;
		}
		
		End.endAnswer(result, "deleted", mcqchoice.getIdMCQChoice());
		
	}
	
	/**
	 * This function will retrieve and print the fields of the MCQChoice for which the id is passed as parameter
	 * @param mcqchoice_id An int containing the id of the MCQChoice to search
	 * @param username A String containing the username of the user
	 * @param password A String containing the password of the user
	 * @param mcqchoice_id An int containing the id  of the MCQChoice to search for
	 * @return A Result Set representing the fields of the MCQChoice found
	 * @throws SQLException Handle the exception thrown by the closure of the Result Set
	 */
	public static ResultSet search (int mcqchoice_id, String username , String password) throws SQLException {
		
		MCQChoice mcqchoice = new MCQChoice ();
		
		ResultSet rs = null;
		boolean result = true;
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(SEARCH_C)
			) {
			
			stmt.setInt(1, mcqchoice_id);
			rs = stmt.executeQuery();
			while (rs.next()) {
				mcqchoice.setIdMCQChoice(rs.getInt("choice_id"));
				mcqchoice.setChoice(rs.getString("choice_text"));
				mcqchoice.setValid(rs.getBoolean("choice_value"));
				End.endAnswer(result, "found" ,mcqchoice.getIdMCQChoice());
				System.out.println("Choice text: " + mcqchoice.getChoice());
				System.out.println("Choice value: " + mcqchoice.isValid());
			}
		} catch (SQLException e) {
			System.err.println(e);
			result = false;
		}finally {
			if (rs != null) {
				rs.close();
			}
		
		}
		
		return rs;
	}
	
	/**
	 * This function will check the number of choices stored in the database and the value of the choice to delete before deleting
	 * @param rs A Result Set containing the field of MCQChoice
	 * @param oldKey An int containing the id of the choice for which the value need to be checked
	 * @return A boolean representing the result of the function 
	 * @throws SQLException Handle the exception thrown by the closure of the Result Set
	 */
	public static boolean check(ResultSet rs, int oldKey) throws SQLException {
		boolean check = true;
		if (rs.next()) {
			rs.last();
			int num = rs.getRow();
			if (num < 5) {
				check = false;
			}
		rs.beforeFirst();
		while (rs.next()) {
			if (rs.getInt("choice_id") == oldKey ) {
				if (rs.getBoolean("choice_value")) {
						check = false;
				}
			}
		}
		rs.close();
		}
		return check;
	}
}

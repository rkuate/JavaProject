package fr.epita.jdbcDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

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
public class MCQQuestionDAO {
	
	/**
	 * Represents the query to use for creating the MCQQuestion 
	 */
	private static final String CREATE_MCQ = "INSERT INTO mcqquestion ( mcq_title , mcq_level ) Values (? , ?)";
	
	/**
	 * Represents the query to use for updating the MCQQuestion
	 */
	private static final String UPDATE_MCQ = "UPDATE mcqquestion SET mcq_title = ? , mcq_level = ? WHERE mcq_id = ?";
	
	/**
	 * Represents the query to execute for deleting an MCQQuestion
	 */
	private static final String DELETE_MCQ = "DELETE FROM mcqquestion WHERE mcq_id =?";
	
	/**
	 * Represents the query to execute for searching an MCQQuestion
	 */
	private static final String SEARCH_MCQ = "SELECT * FROM mcqquestion WHERE mcq_id = ?";
	
	/**
	 * This function will create a MCQQuestion filling the fields in the database by those the user will gave
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return An int representing the id of the MCQQuestion created
	 * @throws SQLException Handle the Exceptiomn thrown by the closure of the Result Set
	 */
	public static int create (String username, String password) throws SQLException {
		MCQQuestion mcqquestion = new MCQQuestion();
		
		ResultSet keys = null;
		boolean result = true;
		
		mcqquestion.setTitle(InputHelper.getStringInput("Enter the title of the MCQ Question: "));
		mcqquestion.setLevel(InputHelper.getIntegerInput("Enter the level: "));
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(CREATE_MCQ, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setString(1, mcqquestion.getTitle());
			stmt.setInt(2, mcqquestion.getLevel());

			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				int newKey = keys.getInt(1);
				mcqquestion.setQuestionId(newKey);
			} else {
				System.err.println("No rows affected");
				result = false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			result = false;
		} finally{
			if (keys != null) keys.close();
		}
		End.endMCQQuestion(result, "created", mcqquestion.getQuestionId());
		return mcqquestion.getQuestionId();
	}

	/**
	 * This function will update the MCQQuestion corresponding to the id the user will give from the actuals
	 * values to the values the user will enter
	 * @param username A String representing the username of the user 
	 * @param password A String representing the password of the user
	 * @throws SQLException Handle the SQL Exception
	 */
	public static void update (String username , String password) throws SQLException{
		
		MCQQuestion mcqquestion = new MCQQuestion ();
		
		boolean result = true;
		
		mcqquestion.setQuestionId(InputHelper.getIntegerInput("Enter the id of the MCQ Question you want to modify: "));
		mcqquestion.setTitle(InputHelper.getStringInput("Enter the new title of the MCQ Question: "));
		mcqquestion.setLevel(InputHelper.getIntegerInput("Enter the new level: "));
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(UPDATE_MCQ)) {
			
			stmt.setString(1, mcqquestion.getTitle());
			stmt.setInt(2, mcqquestion.getLevel());
			stmt.setInt(3, mcqquestion.getQuestionId());
			
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
		End.endMCQQuestion(result, "updated", mcqquestion.getQuestionId());
	}

	/**
	 * This function will delete the MCQQuestion corresponding to the id passed as parameter
	 * @param mcqDelete An int representing the id of the question to delete
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return An int containing the id of the MCQQuestion deleted
	 * @throws SQLException Handle the Exception
	 */
	public static int delete (int mcqDelete, String username , String password) throws SQLException {
		
		MCQQuestion mcqquestion = new MCQQuestion ();
		
		boolean result = true;
		
		mcqquestion.setQuestionId(mcqDelete);
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(DELETE_MCQ)) {
			
			stmt.setInt(1, mcqquestion.getQuestionId());
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
		End.endMCQQuestion(result, "deleted" , mcqquestion.getQuestionId());
		return mcqquestion.getQuestionId();
	}
	
	/**
	 * This function will retrieve and prints the field corresponding to the MCQQuestion for which the id is 
	 * passed as parameter 
	 * @param mcqquestion_id An int representing the id of the question to search
	 * @param username A String representing the username of the user
	 * @param password A String representing the username of the user
	 * @return A Result representing the fields of the MCQQuestion found
	 * @throws SQLException Handle the Exception thrown by the closure of the Result Set
	 */
	public static ResultSet search (int mcqquestion_id, String username , String password) throws SQLException {
		
		ResultSet rs = null;
		boolean result = true;
		
		MCQQuestion mcqquestion = new MCQQuestion ();
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(SEARCH_MCQ)
			) {
			
			stmt.setInt(1, mcqquestion_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				mcqquestion.setQuestionId(mcqquestion_id);
				mcqquestion.setTitle(rs.getString("mcq_title"));
				mcqquestion.setLevel(rs.getInt("mcq_level"));
				System.out.println("Question title: " + mcqquestion.getTitle());
				System.out.println("Level: " + mcqquestion.getLevel());
			}else {
				System.err.println("No rows found");
				result = false;
			}
		} catch (SQLException e) {
			System.err.println(e);
			result = false;
		}finally {
			if (rs != null) {
				rs.close();
			}
		
		}
		End.endMCQQuestion(result, "found" ,mcqquestion.getQuestionId());
		return rs;
	}
}
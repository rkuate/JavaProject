package fr.epita.jdbcDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import fr.epita.datamodel.Question;
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
public class QuestionDAO {
	
	/**
	 * Represents the query to use for creating a Question
	 */
	private static final String CREATE_Q = "INSERT INTO Question (question_title , hint_one , hint_two , hint_three , q_level) Values (? , ? , ? , ? , ?)";
	
	/**
	 * Represents the query to use for updating a Question
	 */
	private static final String UPDATE_Q = "UPDATE Question SET question_title = ? ,hint_one = ? , hint_two = ? , hint_three = ? , q_level = ? WHERE question_id = ?";
	
	/**
	 * Represents the query to use for deleting a Question
	 */
	private static final String DELETE_Q = "DELETE FROM Question WHERE question_id = ?";
	
	/**
	 * Represents the query to use for deleting a Question
	 */
	private static final String SEARCH_Q = "SELECT * FROM Question WHERE question_id = ?";
	
	/**
	 * This function will create a new Question with the values entered by the user
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return An int containing the id of the Question created
	 * @throws SQLException Handle the Exception thrown by the closure of the Result Set
	 */
	public static int create(String username, String password) throws SQLException {
		
		Question question = new Question ();
		
		ResultSet keys = null;
		boolean result = true;
		int newKey = 0;
		
		question.setTitle(InputHelper.getStringInput("Enter the title of the question: "));
		question.setHintOne(InputHelper.getStringInput("Enter the first hint: "));
		question.setHintTwo(InputHelper.getStringInput("Enter the second hint: "));
		question.setHintThree(InputHelper.getStringInput("Enter the third hint: "));
		question.setLevel(InputHelper.getIntegerInput("Enter the level: "));
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(CREATE_Q, Statement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, question.getTitle());
			stmt.setString(2, question.getHintOne());
			stmt.setString(3, question.getHintTwo());
			stmt.setString(4, question.getHintThree());
			stmt.setInt(5, question.getLevel());

			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				newKey = keys.getInt(1);
				question.setQuestionId(newKey);
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
		End.endQuestion(result, "created", question.getQuestionId());
		return newKey;
	}
	
	/**
	 * This function will update the Question with the id the user will give from the actuals values to the values the user will enter
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 */
	public static void update(String username, String password){
		
		Question question = new Question ();
		boolean result = true;
		question.setQuestionId(InputHelper.getIntegerInput("Enter the id of the question you want to modify: "));
		question.setTitle(InputHelper.getStringInput("Enter the new title of the question: "));
		question.setHintOne(InputHelper.getStringInput("Enter the new first hint: "));
		question.setHintTwo(InputHelper.getStringInput("Enter the new second hint: "));
		question.setHintThree(InputHelper.getStringInput("Enter the new third hint: "));
		question.setLevel(InputHelper.getIntegerInput("Enter the new level: "));
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(UPDATE_Q)) {
			
			stmt.setString(1, question.getTitle());
			stmt.setString(2, question.getHintOne());
			stmt.setString(3, question.getHintTwo());
			stmt.setString(4, question.getHintThree());
			stmt.setInt(5, question.getLevel());
			stmt.setInt(6, question.getQuestionId());
			
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
		End.endQuestion(result, "updated", question.getQuestionId());
	}
	
	/**
	 * This function will delete the Question corresponding to the id given by the user
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return An int representing the id of the question deleted
	 */
	public static int delete(String username, String password){
		
		Question question = new Question ();
		
		boolean result = true;
		
		question.setQuestionId(InputHelper.getIntegerInput("Enter the id of the question you want to delete: "));
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(DELETE_Q)) {
			
			stmt.setInt(1, question.getQuestionId());
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
		End.endQuestion(result, "deleted" , question.getQuestionId());
		return question.getQuestionId();
	}
	
	/**
	 * This function will retrieves and print fields corresponding to the Question for which the id is passed as parameter
	 * @param question_id An int representing the question to search
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return A Result Set containing the fields of the question found 
	 * @throws SQLException Handle the Exception thrown by the closure of the Result Set
	 */
	public static ResultSet search(int question_id, String username, String password) throws SQLException {
		
		ResultSet rs = null;
		boolean result = true;
		
		Question question = new Question ();
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(SEARCH_Q)
		) {
			
			stmt.setInt(1, question_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				question.setQuestionId(question_id);
				question.setTitle(rs.getString("question_title"));
				question.setHintOne(rs.getString("hint_one"));
				question.setHintTwo(rs.getString("hint_two"));
				question.setHintThree(rs.getString("hint_three"));
				question.setLevel(rs.getInt("q_level"));
				System.out.println("Question title: " + question.getTitle());
				System.out.println("First hint: " + question.getHintOne());
				System.out.println("Second hint: " + question.getHintTwo());
				System.out.println("Third hint: " + question.getHintThree());
				System.out.println("Level: " + question.getLevel());
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
		End.endQuestion(result, "found" ,question.getQuestionId());
		return rs;
	}
	
}
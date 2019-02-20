package fr.epita.jdbcDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import fr.epita.datamodel.Answer;
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
public class AnswerDAO {
	
	/**
	 * Represents the query used to create an answer
	 */
	private static final String CREATE_A = "INSERT INTO Answer (question_id, answer_text) Values (? , ?)";
	
	/**
	 * Represents the query to use for updating an answer
	 */
	private static final String UPDATE_A = "UPDATE Answer SET answer_text = ? WHERE question_id = ?";
	
	/**
	 * Represents the query to use for deleting an answer 
	 */
	private static final String DELETE_A = "DELETE FROM Answer WHERE question_id = ?";
	
	/**
	 *  Represents the query to use for searching an answer
	 */
	private static final String SEARCH_A = "SELECT * FROM Answer WHERE question_id = ?";
	
	/**
	 * Represents the query to retrieve the id of the question 
	 */
	private static final String GET_A_ID = "SELECT answer_id FROM Answer where question question_id = ?";
	
	/**
	 * This function will create a new answer with values entered by the user and will store it in the database
	 * @param new_question_id An int containing the id of the corresponding answer
	 * @param username A String containing the username of the user
	 * @param password A String containing the password of the user
	 * @throws SQLException Handle the exception of closing the Result Set
	 */
	public static void create(int new_question_id ,String username, String password) throws SQLException {
		
		Question question = new Question ();
		Answer answer = new Answer ();
		
		ResultSet keys = null;
		boolean result = true;
		
		//question.setQuestionId(InputHelper.getIntegerInput("Enter the id of the question: "));
		question.setQuestionId(new_question_id);
		answer.setText(InputHelper.getStringInput("Enter the answer: "));
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(CREATE_A, Statement.RETURN_GENERATED_KEYS)
			) {
			
			stmt.setInt(1, question.getQuestionId());
			stmt.setString(2, answer.getText());
			
			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				int newKey = keys.getInt(1);
				answer.setIdAnswer(newKey);
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
		End.endAnswer(result, "created" , answer.getIdAnswer());
	}
	
	/**
	 * This function will modify an answer corresponding to the id given by the user by replacing the actual values by the given values of the user 
	 * @param username A String containing the username of the user 
	 * @param password A String containing the password of the user
	 */
	public static void update(String username, String password){
		
		Question question = new Question ();
		Answer answer = new Answer ();
		
		boolean result = true;
		
		question.setQuestionId(InputHelper.getIntegerInput("Enter the id of the question: "));
		answer.setText(InputHelper.getStringInput("Enter the new answer text: "));
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(UPDATE_A)) {
			
			stmt.setString(1, answer.getText());
			stmt.setInt(2, question.getQuestionId());
			
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
		End.endAnswer(result, "updated", answer.getIdAnswer());
	}
	
	/**
	 * This function will delete an answer corresponding to the id given by the user
	 * @param new_question_id An int containing the id of the corresponding answer 
	 * @param username A String containing the username of the user
	 * @param password A String containing the password of the user
	 * @throws SQLException Handle the exception of closing the Result Set
	 */
	public static void delete(int new_question_id , String username, String password) throws SQLException {
		
		Answer answer = new Answer ();
		Question question = new Question();
		
		boolean result = true;
		ResultSet rs = null;
		
		question.setQuestionId(new_question_id);
		
	  try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(DELETE_A)
							
	 ) {
			
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
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(GET_A_ID)
			) {
			
			stmt.setInt(1, new_question_id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				answer.setIdAnswer(rs.getInt("answer_id"));
			}else {
				System.err.println("No rows found");
			}
		} catch (SQLException e) {
			System.err.println(e);
		}finally {
			if (rs != null) {
				rs.close();
			}
		
		}
		End.endAnswer(result, "deleted", answer.getIdAnswer());
	}

	/**
	 * This function will retrieve and print the field of an answer corresponding to the id passed as parameter
	 * @param new_question_id An int containing the id of the answer 
	 * @param username A String containing the username of the user
	 * @param password A String containing the password of the user
	 * @return A ResultSet representing the fields of the answer found
	 * @throws SQLException Handle the exception throws by the closure of the Result Set
	 */
	public static ResultSet search(int new_question_id, String username, String password) throws SQLException {
		
		Answer answer = new Answer ();
		
		ResultSet rs = null;
		boolean result = true;
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(SEARCH_A)
			) {
			
			stmt.setInt(1, new_question_id);
			rs = stmt.executeQuery();
			if (rs.next()) {//
				answer.setIdAnswer(rs.getInt("answer_id"));//
				answer.setText(rs.getString("answer_text"));//
				System.out.println("Answer text: " + answer.getText());//
			}else {//
				System.err.println("No rows found");//
				result = false;//
			}
		} catch (SQLException e) {
			System.err.println(e);
			result = false;
		}finally {
			if (rs != null) {
				rs.close();
			}
		
		}
		End.endAnswer(result, "found" ,answer.getIdAnswer());
		return rs;
	}
}

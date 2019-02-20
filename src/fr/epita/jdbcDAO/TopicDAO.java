package fr.epita.jdbcDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

import fr.epita.datamodel.MCQQuestion;
import fr.epita.datamodel.Question;
import fr.epita.datamodel.Topic;
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
public class TopicDAO {
	
	/**
	 * Represents the query to use for creating a set of topics for Questions
	 */
	private static final String CREATE_TQ = "INSERT INTO Topic (question_id , topic_one , topic_two , topic_three , topic_four , topic_five , topic_six , topic_seven , topic_eight , topic_nine , topic_ten) Values (?, ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
	
	/**
	 * Represents the query to use for creating a set of topics for MCQQuestions
	 */
	private static final String CREATE_TM = "INSERT INTO Topic (mcq_id , topic_one , topic_two , topic_three , topic_four , topic_five , topic_six , topic_seven , topic_eight , topic_nine , topic_ten) Values (?, ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
	
	/**
	 * Represents the query to use for updating a topic
	 */
	private static final String UPDATE_T = "UPDATE Topic SET topic_one = ? , topic_two = ? , topic_three = ? , topic_four = ? , topic_five = ? , topic_six = ? , topic_seven = ? , topic_eight = ? , topic_nine = ? , topic_ten = ? WHERE question_id = ? OR mcq_id = ?";
	
	/**
	 * Represents the query to use for deleting a topic
	 */
	private static final String DELETE_T = "DELETE FROM Topic WHERE question_id = ? OR mcq_id = ?";
	
	/**
	 * Represents the query to use for searching a question
	 */
	private static final String SEARCH_T = "SELECT * FROM Topic WHERE question_id = ? OR mcq_id = ?";
	
	/**
	 * This function create a set of topic for a the question for which the id is passed as parameter
	 * @param tq An int representing the id of the question for which the user would like to create a set of topics
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @throws SQLException Handle the Exception throws by the closure of the Result Set
	 */
	public void createTQ(int tq, String username, String password) throws SQLException {
		
		Topic topic = new Topic();
		Question question = new Question ();
		
		ResultSet keys = null;
		boolean result = false;
		
		question.setQuestionId(tq);
		topic.setTopicOne(InputHelper.getStringInput("Enter the first topic: "));
		topic.setTopicTwo(InputHelper.getStringInput("Enter the second topic: "));
		topic.setTopicThree(InputHelper.getStringInput("Enter the third topic: "));
		topic.setTopicFour(InputHelper.getStringInput("Enter the fourth topic: "));
		topic.setTopicFive(InputHelper.getStringInput("Enter the fifth topic: "));
		topic.setTopicSix(InputHelper.getStringInput("Enter the sixth topic: "));
		topic.setTopicSeven(InputHelper.getStringInput("Enter the seventh topic: "));
		topic.setTopicEight(InputHelper.getStringInput("Enter the eightth topic: "));
		topic.setTopicNine(InputHelper.getStringInput("Enter the nineth topic: "));
		topic.setTopicTen(InputHelper.getStringInput("Enter the tenth topic: "));
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(CREATE_TQ, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setInt(1, question.getQuestionId());
			stmt.setString(2, topic.getTopicOne());
			stmt.setString(3, topic.getTopicTwo());
			stmt.setString(4, topic.getTopicThree());
			stmt.setString(5, topic.getTopicFour());
			stmt.setString(6, topic.getTopicFive());
			stmt.setString(7, topic.getTopicSix());
			stmt.setString(8, topic.getTopicSeven());
			stmt.setString(9, topic.getTopicEight());
			stmt.setString(10, topic.getTopicNine());
			stmt.setString(11, topic.getTopicTen());

			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				int newKey = keys.getInt(1);
				topic.setTopicId(newKey);
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
		End.endTopic(result, "created", topic.getTopicId());

	}
	
	/**
	 * @param tmcq An int representing the id of the MCQQuestion for which the user would like to create a set of topics
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @throws SQLException Handle the exception thrown by the closure of the Result Set
	 */
	public void createTMCQ(int tmcq, String username, String password) throws SQLException {
		
		Topic topic = new Topic();
		MCQQuestion mcqquestion = new MCQQuestion();
		
		ResultSet keys = null;
		boolean result = false;
		
		mcqquestion.setIdMCQQuestion(tmcq);
		topic.setTopicOne(InputHelper.getStringInput("Enter the first topic: "));
		topic.setTopicTwo(InputHelper.getStringInput("Enter the second topic: "));
		topic.setTopicThree(InputHelper.getStringInput("Enter the third topic: "));
		topic.setTopicFour(InputHelper.getStringInput("Enter the fourth topic: "));
		topic.setTopicFive(InputHelper.getStringInput("Enter the fifth topic: "));
		topic.setTopicSix(InputHelper.getStringInput("Enter the sixth topic: "));
		topic.setTopicSeven(InputHelper.getStringInput("Enter the seventh topic: "));
		topic.setTopicEight(InputHelper.getStringInput("Enter the eightth topic: "));
		topic.setTopicNine(InputHelper.getStringInput("Enter the nineth topic: "));
		topic.setTopicTen(InputHelper.getStringInput("Enter the tenth topic: "));
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(CREATE_TM, Statement.RETURN_GENERATED_KEYS)) {
			
			stmt.setInt(1, mcqquestion.getIdMCQQuestion());
			stmt.setString(2, topic.getTopicOne());
			stmt.setString(3, topic.getTopicTwo());
			stmt.setString(4, topic.getTopicThree());
			stmt.setString(5, topic.getTopicFour());
			stmt.setString(6, topic.getTopicFive());
			stmt.setString(7, topic.getTopicSix());
			stmt.setString(8, topic.getTopicSeven());
			stmt.setString(9, topic.getTopicEight());
			stmt.setString(10, topic.getTopicNine());
			stmt.setString(11, topic.getTopicTen());

			int affected = stmt.executeUpdate();
			
			if (affected == 1) {
				keys = stmt.getGeneratedKeys();
				keys.next();
				int newKey = keys.getInt(1);
				topic.setTopicId(newKey);
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
		End.endTopic(result, "created", topic.getTopicId());

	}
	
	/**
	 * @param key An int representing the id of the set of topic for which we would like to modify the values
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 */
	public static void update (int key, String username , String password){
		
		Topic topic = new Topic ();
		Question question = new Question ();
		MCQQuestion mcqquestion = new MCQQuestion ();
		
		boolean result = true;

		question.setQuestionId(key);
		mcqquestion.setIdMCQQuestion(key);
		topic.setTopicOne(InputHelper.getStringInput("Enter the new first topic: "));
		topic.setTopicTwo(InputHelper.getStringInput("Enter the new second topic: "));
		topic.setTopicThree(InputHelper.getStringInput("Enter the new third topic: "));
		topic.setTopicFour(InputHelper.getStringInput("Enter the new fourth topic: "));
		topic.setTopicFive(InputHelper.getStringInput("Enter the new fifth topic: "));
		topic.setTopicSix(InputHelper.getStringInput("Enter the new sixth topic: "));
		topic.setTopicSeven(InputHelper.getStringInput("Enter the new seventh topic: "));
		topic.setTopicEight(InputHelper.getStringInput("Enter the new eightth topic: "));
		topic.setTopicNine(InputHelper.getStringInput("Enter the new nineth topic: "));
		topic.setTopicTen(InputHelper.getStringInput("Enter the new tenth topic: "));
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(UPDATE_T)) {
			
			stmt.setString(1, topic.getTopicOne());
			stmt.setString(2, topic.getTopicTwo());
			stmt.setString(3, topic.getTopicThree());
			stmt.setString(4, topic.getTopicFour());
			stmt.setString(5, topic.getTopicFive());
			stmt.setString(6, topic.getTopicSix());
			stmt.setString(7, topic.getTopicSeven());
			stmt.setString(8, topic.getTopicEight());
			stmt.setString(9, topic.getTopicNine());
			stmt.setString(10, topic.getTopicTen());
			stmt.setInt(11, question.getQuestionId());
			stmt.setInt(12, mcqquestion.getIdMCQQuestion());
			
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
		End.endTopic(result, "updated", topic.getTopicId());
	}
	
	/**
	 * This function delete the set of topic for which the id will be gave by the user
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 */
	public static void delete (String username, String password) {
		
		Question question = new Question ();
		MCQQuestion mcqquestion = new MCQQuestion ();
		Topic topic = new Topic ();
		
		boolean result = true;
		Integer id = InputHelper.getIntegerInput("Enter the id of the Question/MCQ Question you want to delete: ");
		
		question.setQuestionId(id);
		mcqquestion.setQuestionId(id);
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(DELETE_T)) {
			
			stmt.setInt(1, question.getQuestionId());
			stmt.setInt(2, mcqquestion.getIdMCQQuestion());
			
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
		End.endTopic(result, "deleted" , topic.getTopicId());	
	}

	/**
	 * This function retrieve and print the fields corresponding to the set of topics for which the user will give the id 
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @throws SQLException Handle the SQL Expetion thrown by the closure of the Result Set
	 */
	public static void search (String username , String password) throws SQLException {
		
		ResultSet rs = null;
		boolean result = true;
		int id = InputHelper.getIntegerInput("Enter the id of the Question/MCQ Question you want to search: ");
		
		Topic topic = new Topic ();
		
		
		
		try (
				Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
				PreparedStatement stmt = conn.prepareStatement(SEARCH_T)
			) {
			
			stmt.setInt(1, id);
			stmt.setInt(2, id);
			
			rs = stmt.executeQuery();
			
			if (rs.next()) {
				topic.setTopicOne(rs.getString("topic_one"));
				topic.setTopicTwo(rs.getString("topic_two"));
				topic.setTopicThree(rs.getString("topic_three"));
				topic.setTopicFour(rs.getString("topic_four"));
				topic.setTopicFive(rs.getString("topic_five"));
				topic.setTopicSix(rs.getString("topic_six"));
				topic.setTopicSeven(rs.getString("topic_seven"));
				topic.setTopicEight(rs.getString("topic_eight"));
				topic.setTopicNine(rs.getString("topic_nine"));
				topic.setTopicTen(rs.getString("topic_ten"));
				System.out.println("Topic one: " + topic.getTopicOne());
				System.out.println("Topic two: " + topic.getTopicTwo());
				System.out.println("Topic three: " + topic.getTopicThree());
				System.out.println("Topic four: " + topic.getTopicFour());
				System.out.println("Topic five: " + topic.getTopicFive());
				System.out.println("Topic six: " + topic.getTopicSix());
				System.out.println("Topic seven: " + topic.getTopicSeven());
				System.out.println("Topic eight: " + topic.getTopicEight());
				System.out.println("Topic nine: " + topic.getTopicNine());
				System.out.println("Topic ten: " + topic.getTopicTen());
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
		End.endTopic(result, "found" ,topic.getTopicId());
	}
}

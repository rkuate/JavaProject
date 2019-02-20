package fr.epita.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.epita.jdbcDAO.AnswerDAO;
import fr.epita.jdbcDAO.QuestionDAO;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class OpenQuestion {
	
	/**
	 * Represents the query used to perform OpenQuestions without topics
	 */
	private static final String OPEN_ST = "SELECT * FROM question";
	
	/**
	 * Represents the query used to perform OpenQuestions with topics
	 */
	private static final String OPEN_AT = "SELECT question_id FROM topic WHERE topic_one = ? OR topic_two = ? OR topic_three = ? OR topic_four = ? OR topic_five = ? OR topic_six = ? OR topic_seven = ? OR topic_eight = ? OR topic_nine = ? OR topic_ten = ?";
		
	/**
	 * This function will operate OpenQuestions without topics
	 * @param username A String representing the username of the user 
	 * @param password A String representing the password of the user
	 * @param number An int representing the number of OpenQuestions needed to be retrieve
	 * @return An ArrayList of ArrayList containing the OpenQuestions with their answers 
	 * @throws SQLException Handle the SQL Excpetion thrown by the closure of the Result Set
	 */
	public static ArrayList <ArrayList <String> > openQuestionWithout (String username , String password, int number) throws SQLException {
		
		ArrayList <ArrayList <String> > openArr = new ArrayList <ArrayList <String> > ();
		ArrayList <String> tempArr = new ArrayList <String>();
		ArrayList <Integer> ids = new ArrayList <Integer>();
		
		for (int i = 1 ; i <= number ; i++) {
			do {
				ResultSet rs = null;
				ResultSet answer = null;
				
				tempArr.clear();
				
					try (
							Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
							PreparedStatement stmt = conn.prepareStatement(OPEN_ST)
					) {
						
						rs = stmt.executeQuery();
						if (rs.next()) {
							rs.beforeFirst();
							int size = 0;
							rs.last();                          // moves cursor to the last row
						  	size = rs.getRow();                 // get row id 
						  	int rand = (int) Math.random() * size;
							rs.absolute(rand);
							tempArr.add(Integer.toString(rs.getInt("question_id")));
							tempArr.add(rs.getString("question_title"));
							tempArr.add(rs.getString("hint_one"));								
							tempArr.add(rs.getString("hint_two"));
							tempArr.add(rs.getString("hint_three"));
							tempArr.add(rs.getString("q_level"));
							answer = AnswerDAO.search(Integer.parseInt(tempArr.get(0)), username, password);
							if (answer.next()) {
								tempArr.add(Integer.toString(rs.getInt("answer_id")));
								tempArr.add(rs.getString("answer_text"));
							}else {
								System.err.println("No rows found");								
							}
						}
						
					} catch (SQLException e) {
						System.err.println(e);
					}finally {
						if (rs != null) {
							rs.close();
						}
					
					}
					
			} while (!ids.isEmpty()&&ids.contains(Integer.parseInt(tempArr.get(0))));
			if (!tempArr.isEmpty()) {
				ids.add(Integer.parseInt(tempArr.get(0)));	
				openArr.add(tempArr);
			}
		}
		
		
		return openArr;
	}
	
	/**
	 * This function will operate OpenQuestions with topics
	 * @param topics A List representing the topics entered by the user
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return An ArrayList of ArrayList of ArrayList of String containing the OpenQuestions with their answers
	 * @throws SQLException Handle the SQL Excpetion thrown by the closure of the Result Set
	 */
	public static ArrayList <ArrayList < ArrayList <String> > > openQuestionWith (List <String> topics, String username, String password) throws SQLException{
		
		ArrayList <ArrayList < ArrayList <String> > > openArr = new ArrayList <ArrayList < ArrayList <String> > >();
		ResultSet rs = null;
		ResultSet answer = null;
		
		while (!topics.isEmpty()) {
			
			String topic = topics.remove(0);
			ArrayList <ArrayList <String> > topicArr = new ArrayList <ArrayList <String> >();
			
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
					PreparedStatement stmt = conn.prepareStatement(OPEN_AT)
			) {
				stmt.setString(1, topic);
				stmt.setString(2, topic);
				stmt.setString(3, topic);
				stmt.setString(4, topic);
				stmt.setString(5, topic);
				stmt.setString(6, topic);
				stmt.setString(7, topic);
				stmt.setString(8, topic);
				stmt.setString(9, topic);
				stmt.setString(10, topic);
				rs = stmt.executeQuery();
				if (rs.next()) {
					rs.beforeFirst();
					while (rs.next()) {
						ArrayList <String> tempArr = new ArrayList <String>();
						ResultSet temp = QuestionDAO.search(rs.getInt("question_id"), username, password);
						tempArr.add(Integer.toString(temp.getInt("question_id")));
						tempArr.add(temp.getString("question_title"));
						tempArr.add(temp.getString("hint_one"));
						tempArr.add(temp.getString("hint_two"));
						tempArr.add(temp.getString("hint_three"));
						tempArr.add(temp.getString("q_level"));
						answer = AnswerDAO.search(Integer.parseInt(tempArr.get(0)), username, password);
						if (answer.next()) {
							tempArr.add(Integer.toString(answer.getInt("answer_id")));
							tempArr.add(answer.getString("answer_text"));
						}else {
							System.err.println("No rows found");
						}
						topicArr.add(tempArr);
					}
				}
				
			} catch (SQLException e) {
				System.err.println(e);
			}finally {
				if (rs != null) {
					rs.close();
				}
			
			}
			if(!topicArr.isEmpty()) openArr.add(topicArr);
		}
		
		return openArr;
	}
	
	/**
	 * This function will perform a quiz with the ArrayList of ArrayList of ArrayList of String passed in as parameter and return the grade 
	 * @param openList An ArrayList of ArrayList of ArrayList of String containing the OpenQuestions with their answers
	 * @return A double containing the final grade of the user
	 */
	public static double performWith (ArrayList <ArrayList < ArrayList <String> > > openList) {
		double total = 0;
		int i = 0;
		for (ArrayList<ArrayList<String>> topicList : openList) {
			for (ArrayList<String> tempList : topicList) {
				i++;
				total = openQuestion(tempList, total, i);
			}
			
		}
		return total;
	}
	
	/**
	 * This function will perform a quiz with the ArrayList of ArrayList of String passed in as parameter and return the grade 
	 * @param openArr An ArrayList of ArrayList of ArrayList of String containing the OpenQuestions with their answers
	 * @return A double containing the final grade of the user
	 */
	public static double performWithout (ArrayList<ArrayList<String>> openArr) {
		double total = 0;
		int i = 0;
		for (ArrayList <String> tempList : openArr ) {
			i++;
			total = openQuestion(tempList, total, i);
		}
		return total; 
	}
	
	/**
	 * This function will perform a quiz with the ArrayList of String passed in as parameter and return the grade 
	 * @param tempList An ArrayList of ArrayList of ArrayList of String containing the OpenQuestions with their answers
	 * @param i An int containing the number of OpenQuestions need to be performed
	 * @param total A double containing the grade of the user after perfoming OpenQuestions
	 * @return A double containing the final grade of the user
	 */
	public static double openQuestion (ArrayList <String> tempList, double total, int i) {
		System.out.println(i + "." + tempList.get(1));
		System.out.print("These are some hints: ");
		for (int hints = 2 ; hints < 5 ; hints++) {
			if (!tempList.get(hints).equals("null_hint")) {
				System.out.print(tempList.get(hints) + " - ");
			}
		}
		String answer = InputHelper.getStringInput("Your answer: ");
		if (answer.equals(tempList.get(7))) {
			total++;
		}
		return (double) total / i;
	}
}
	
	
	
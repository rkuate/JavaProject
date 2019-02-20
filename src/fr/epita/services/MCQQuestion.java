package fr.epita.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;

import fr.epita.jdbcDAO.MCQChoiceDAO;
import fr.epita.jdbcDAO.MCQQuestionDAO;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class MCQQuestion {
	
	/**
	 * Represents the query to be executed to perform a MCQQuestion quiz without topics
	 */
	private static final String MCQ_ST = "SELECT * FROM mcqquestion";
	
	/**
	 *  Represents the query to be executed to perform a MCQQuestion quiz with topics
	 */
	private static final String MCQ_AT = "SELECT mcq_id FROM topic WHERE topic_one = ? OR topic_two = ? OR topic_three = ? OR topic_four = ? OR topic_five = ? OR topic_six = ? OR topic_seven = ? OR topic_eight = ? OR topic_nine = ? OR topic_ten = ?";
	
	/**
	 * @param topics A List of topics entered by the user
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return An ArrayList of ArrayList of ArrayList of ArrayList of String containing the MCQQuestions with their MCQChoices
	 * @throws SQLException Handle the SQL Excpetion thrown by the closure of the Result Set
	 */
	public static ArrayList <ArrayList <ArrayList <ArrayList <String>>>> mcqQuestionWith (List <String> topics, String username, String password) throws SQLException{
		
		ArrayList <ArrayList <ArrayList <ArrayList <String>>>> mcqArr = new ArrayList <ArrayList <ArrayList <ArrayList <String>>>>();
		
		ResultSet rs = null;
		ResultSet choice = null;
		
		while (!topics.isEmpty()) {
			
			String topic = topics.remove(0);
			ArrayList <ArrayList <String>> tempArr = new ArrayList <ArrayList <String>>();
			ArrayList <ArrayList <ArrayList <String>>> topicArr = new ArrayList <ArrayList <ArrayList <String>>>();
			
			try (
					Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
					PreparedStatement stmt = conn.prepareStatement(MCQ_AT)
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
				while (rs.next()) {
					ArrayList <String> mcqquestion = new ArrayList <String>();
					ResultSet temp = MCQQuestionDAO.search(rs.getInt("mcq_id"), username, password);
					mcqquestion.add(Integer.toString(temp.getInt("mcq_id")));
					mcqquestion.add(temp.getString("mcq_title"));
					mcqquestion.add(Integer.toString(temp.getInt("mcq_level")));
					tempArr.add(mcqquestion);
					choice = MCQChoiceDAO.search(Integer.parseInt(mcqquestion.get(0)), username, password);
					if (choice.next()) {
						choice.beforeFirst();
						while (choice.next()) {
							ArrayList <String> mcqchoice = new ArrayList <String>();
							mcqchoice.add(Integer.toString(choice.getInt("choice_id")));
							mcqchoice.add(choice.getString("choice_text"));
							mcqchoice.add(Boolean.toString(choice.getBoolean("choice_value")));
							tempArr.add(mcqchoice);
						}						
					}else {
						System.err.println("No rows found");
					}
					topicArr.add(tempArr);
				}
				
			} catch (SQLException e) {
				System.err.println(e);
			}finally {
				if (rs != null) {
					rs.close();
				}
			
			}
			mcqArr.add(topicArr);
		}
		
		return mcqArr;
	}
	
	/**
	 * This function will perform a quiz with the ArrayList of ArrayList of ArrayList of ArrayList of String passed in as parameter and return the grade 
	 * @param mcqArr An ArrayList of ArrayList of ArrayList of String containing the OpenQuestions with their answers
	 * @return A double containing the final grade of the user
	 */
	public static double performWith (ArrayList <ArrayList <ArrayList <ArrayList <String>>>> mcqArr) {
		double total = 0; 
		int i = 0 ;
		for (ArrayList<ArrayList<ArrayList <String>>> topicList : mcqArr) {
			for (ArrayList<ArrayList<String>> tempList : topicList) {
				i++;
				total = mcqQuestion(tempList, total, i);
			}
			
		}
		
		return total;
	}
	
	/**
	 * @param number An int representing the number of MCQQuestions to be retrieve from the database
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return An ArrayList of ArrayList of ArrayList of String containing the MCQQuestions with their MCQChoices
	 * @throws SQLException Handle the SQL Excpetion thrown by the closure of the Result Set
	 */
	public static ArrayList <ArrayList <ArrayList <String>>> mcqQuestionWithout (int number, String username , String password) throws SQLException {
		
		ArrayList <ArrayList <ArrayList <String>>> mcqArr = new ArrayList <ArrayList <ArrayList <String>>>();
		ArrayList <String> ids = new ArrayList <String>();
		ArrayList <String> mcqquestion = new ArrayList <String>();
		for (int i =0 ; i > number ; i++) {
			ArrayList <ArrayList <String>> tempArr = new ArrayList <ArrayList <String>>();
			do {
				ResultSet rs = null;
				ResultSet choice = null;
				tempArr.clear();
				
					try (
							Connection conn = DBUtil.getConnection(DBType.MYSQL, username, password);
							PreparedStatement stmt = conn.prepareStatement(MCQ_ST)
					) {
						
						rs = stmt.executeQuery();
						int size = 0;
						if (rs != null) 
						{
						  rs.last();                          // moves cursor to the last row
						  size = rs.getRow();                 // get row id 
						}
						int rand = (int) Math.random() * size;
						rs.absolute(rand);
						mcqquestion.add(Integer.toString(rs.getInt("mcq_id")));
						mcqquestion.add(rs.getString("mcq_title"));
						mcqquestion.add(Integer.toString(rs.getInt("mcq_level")));
						tempArr.add(mcqquestion);
						choice = MCQChoiceDAO.search(Integer.parseInt(mcqquestion.get(0)), username, password); 
						if (choice.next()) {
							choice.beforeFirst();
							while (choice.next()) {
								ArrayList <String> mcqchoice = new ArrayList <String>();
								mcqchoice.add(Integer.toString(rs.getInt("answer_id")));
								mcqchoice.add(rs.getString("answer_text"));
								mcqchoice.add(Boolean.toString(rs.getBoolean("mcq_value")));
								tempArr.add(mcqchoice);
							}
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
					
			} while (ids.contains(mcqquestion.get(0)));
			ids.add(mcqquestion.get(0));
			mcqArr.add(tempArr);
		}
		
		
		return mcqArr;
	}
	
	/**
	 * This function will perform a quiz with the ArrayList of ArrayList of ArrayList of String passed in as parameter and return the grade 
	 * @param mcqArr An ArrayList of ArrayList of ArrayList of String containing the OpenQuestions with their answers
	 * @return A double containing the final grade of the user
	 */
	public static double performWithout (ArrayList <ArrayList <ArrayList <String>>> mcqArr) {
		double total = 0;
		int i = 0;
		for (ArrayList<ArrayList<String>> tempList : mcqArr) {
			i++;
			total = mcqQuestion(tempList, total, i);
		}
		return total;
	}
	
	
	/**
	 * This function will perform a quiz with the ArrayList of ArrayList of String passed as parameters
	 * and will return the final grade of the user
	 * @param tempList The ArrayList of ArrayList of String representing the MCQQuestions retrieved from the databases with their MCQChoices
	 * @param total A double representing the initial grade of the user
	 * @param i An int representing the starting number of MCQQuestions
	 * @return A double representing the grade of the user after performing MCQQuestions
	 */
	public static double mcqQuestion (ArrayList<ArrayList<String>> tempList, double total, int i) {
			ArrayList<String> mcqquestion = new ArrayList<String> ();
			mcqquestion = tempList.get(0);
			System.out.println(mcqquestion.get(1));
			ArrayList<Integer> correct = new ArrayList<Integer> ();
			for (i = 1 ; i > tempList.size() ; i++) {
				ArrayList<String> mcqchoice = new ArrayList<String> ();
				mcqchoice = tempList.get(i);
				System.out.println(i + ". " + mcqchoice.get(1));
				if ((mcqchoice.get(2)).equals("true")) {
					correct.add(i);
				}
			}
			System.out.println("Enter the correct choices numbers separated by ', '");
			String input = InputHelper.getStringInput("Your String: ");
			StringBuilder sb = new StringBuilder("[");
				sb.append(input);
				sb.append("]");
			if (sb.toString().equals(correct.toString())) {
				total++;
			}
			return total;
	}

}

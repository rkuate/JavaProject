package fr.epita.launcher;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import fr.epita.jdbcDAO.AnswerDAO;
import fr.epita.jdbcDAO.MCQChoiceDAO;
import fr.epita.jdbcDAO.MCQQuestionDAO;
import fr.epita.jdbcDAO.QuestionDAO;
import fr.epita.jdbcDAO.TopicDAO;
import fr.epita.services.AskRequests;
import fr.epita.services.AssociativeQuestion;
import fr.epita.services.Connect;
import fr.epita.services.DBUtil;
import fr.epita.services.InputHelper;
import fr.epita.services.Logger;
import fr.epita.services.MCQQuestion;
import fr.epita.services.OpenQuestion;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class Launcher { 
	
	/**
	 * This is the main class of the program
	 * @param args A String Array 
	 * @throws SQLException Handle the exception thrown by the
	 */
	@SuppressWarnings("null")
	public static void main(String[] args) throws SQLException {
		Logger.logMessage("------- Program started");
		System.out.println("Welcome to the application");
		System.out.println("Please, enter your credentials");
		Scanner scanner = new Scanner(System.in);
                                                                        
		String username = InputHelper.getStringInput("Username: ").toLowerCase();
		String password = InputHelper.getStringInput("Password: ").toLowerCase();
		
		if (authenticate(username, password)) {
			Logger.logMessage("user " + username + " authenticated succesfully");
			System.out.println("Welcome " + username);
			try {
				Connect.connect(username , password);
			} catch (SQLException e) {
				DBUtil.processException(e);
			}
			String start = null;
			while (start == null) {
				String askOne = AskRequests.askLevelOne(scanner);
				switch(askOne){
					case "M":
						if (admin(username, password)) {
							Logger.logMessage("user " + username + " managing the Database");
							String manage = null;
							while (manage == null) {
								String askManage = AskRequests.askLevelManage(scanner);
								switch(askManage) {
									case "A":
										Logger.logMessage("user " + username + " managing Answer's Table");
										String answer = null;
										while (answer == null) {
											System.out.println("Which operation would you like to perform with the table Answer ?: ");
											String askAnswer = AskRequests.askLevelTwo(scanner);
											switch (askAnswer) {
												case "C" :
													Logger.logMessage("user " + username + " choose Create An Answer");
													notPossible();
													break;
												case "U" :
													Logger.logMessage("user " + username + " choose Update An Answer");
													AnswerDAO.update(username, password);
													break;
												case "D" :
													Logger.logMessage("user " + username + " choose Delete an answer");
													notPossible();
													break;
												case "S" :
													Logger.logMessage("user " + username + " choose Search an Answer");
													int question_id = InputHelper.getIntegerInput("Enter the id of the question you want to search for the answer: ");
													AnswerDAO.search(question_id, username, password);
													break;
												case "X" :
													answer = "";
													break;
											}
										}
										break;
									case "Q":
										Logger.logMessage("user " + username + " managing Question's Table");
										String question = null;
										while(question == null) {
											System.out.println("Which operation would you like to perform with the table Question ?: ");
											String askQuestion = AskRequests.askLevelTwo(scanner);
											switch (askQuestion) {
												case "C" :
													Logger.logMessage("user " + username + " choose Create A Question");
													int newKey = QuestionDAO.create(username, password);
													AnswerDAO.create(newKey, username, password);
													break;
												case "U" :
													Logger.logMessage("user " + username + " choose Update A Question");
													QuestionDAO.update(username, password);
													break;
												case "D" :
													Logger.logMessage("user " + username + " choose Delete A Question");
													int oldKey = QuestionDAO.delete(username, password);
													AnswerDAO.delete(oldKey, username, password);
													break;
												case "S" :
													Logger.logMessage("user " + username + " choose Search A Question");
													int question_id = InputHelper.getIntegerInput("Enter the id of the question you want to search: ");
													QuestionDAO.search(question_id, username, password);
													break;
												case "X" :
													question = "";
													Logger.logMessage("--------Exiting from Question Table Manager");
													break;
													
											}
										}
										break;
									case "T":
										Logger.logMessage("user " + username + " managing Topic's Table");
										String topic = null;
										while (topic == null) {
											System.out.println("Which operation would you like to perform with the table Question ?: ");
											String askTopic = AskRequests.askLevelTwo(scanner);
											switch (askTopic) {
												case "C" :
													Logger.logMessage("user " + username + " choose Create A Topic");
													notPossible();
													break;
												case "U" :
													Logger.logMessage("user " + username + " choose Update A Topic");
													System.out.println("Enter the id of the Question/MCQ Question you want to update: ");
													int key = InputHelper.getIntegerInput("Enter the id of the Question/MCQ Question you want to update: ");
													TopicDAO.update(key, username, password);
													break;
												case "D" :
													Logger.logMessage("user " + username + " choose Delete A Topic");
													TopicDAO.delete(username, password);
													break;
												case "S" :
													Logger.logMessage("user " + username + " choose Search A Topic");
													TopicDAO.search(username, password);
													break;
												case "X" :
													topic = "";
													Logger.logMessage("--------Exiting from Topic Table Manager");
											}
										}
										break;
									case "MQ":
										Logger.logMessage("user " + username + " managing MCQQuestion's Table");
										String mcqquestion = null;
										while (mcqquestion == null) {
											System.out.println("Which operation would you like to perform with the table MCQQuestion ?: ");
											String askMCQQuestion = AskRequests.askLevelTwo(scanner);
											switch (askMCQQuestion) {
												case "C":
													Logger.logMessage("user " + username + " choose Create A MCQQuestion");
													int newKey = MCQQuestionDAO.create(username, password);
													String next = "O";
													List <String> lChoice = new ArrayList <String>();
													List <Boolean> lValue = new ArrayList <Boolean>();
													while ((lChoice.size() < 4 && lValue.size() > 4) || next.equals("O") ) {
														if (next.equals("X")) {
															MCQQuestionDAO.delete(newKey, username, password);
															break;
														}else {
															String choice = InputHelper.getStringInput("Enter a choice: ");
															Boolean value = InputHelper.getBooleanInput("Enter a value ");
															if (value.toString() == null) {
																System.out.println("Wrong values entered");
																System.out.println("Can not saves theses values");
															}else {
																lChoice.add(choice);
																lValue.add(value);
															}
															next = AskRequests.askLevelMCQCreate(scanner);
														}
													MCQChoiceDAO.create(newKey, lChoice, lValue, username, password);	
													}
													break;
												case "U":
													Logger.logMessage("user " + username + " choose Update A MCQQuestion");
													MCQQuestionDAO.update(username, password);
													break;
												case "D" :
													Logger.logMessage("user " + username + " choose Delete A MCQQuestion");
													int oldKey = InputHelper.getIntegerInput("Enter the id of the MCQQuestion you would like to delete :");
													MCQQuestionDAO.delete(oldKey, username, password);
													break;
												case "S" :
													Logger.logMessage("user " + username + " choose Search A MCQQuestion");
													int key = InputHelper.getIntegerInput("Enter the id of the MCQQuestion you would to search");
													MCQQuestionDAO.search(key, username, password);
													break;
												case "X" :
													mcqquestion = "";
													Logger.logMessage("--------Exiting from MCQCQuestion Table Manager");
													break;
											}
										}
										break;
									case "C" :
										Logger.logMessage("user " + username + " choose managing MCQChoice's table");
										String mcqchoice = null;
										while (mcqchoice == null) {
											System.out.println("Which operation would you like to perform with the table topic ?:");
											String askMCQChoice = AskRequests.askLevelOne(scanner);
											switch (askMCQChoice) {
												case "C" :
													Logger.logMessage("user " + username + " choose Create A MCQChoice");
													notPossible();
													break;
												case "U" :
													Logger.logMessage("user " + username + " choose Update A MCQChoice");
													int key = InputHelper.getIntegerInput("Enter the value of the MCQChoice to update: ");
													MCQChoiceDAO.update(key, username, password);
													break;
												case "D" :
													Logger.logMessage("user " + username + " choose Delete A MCQChoice");
													int oldKey = InputHelper.getIntegerInput("Enter the id of the choice to delete: ");
													ResultSet rs = MCQChoiceDAO.search(oldKey, username, password);
													if (MCQChoiceDAO.check(rs, oldKey)) {
														MCQChoiceDAO.delete(oldKey, username, password);
													}else {
														System.out.println("Can not delete this choice ; either there is less than 5 choices for the question or the choice is true.");
													}
													break;
												case "S" :
													Logger.logMessage("user " + username + " choose Search A MCQChoice");
													int anotherKey = InputHelper.getIntegerInput("Enter the id of the question for which you look the choices: ");
													MCQChoiceDAO.search(anotherKey, username, password);
													break;
												case "X" :
													mcqchoice = "";
													Logger.logMessage("--------Exiting from MCQChoice Table Manager");
													break;
											}
										}
									case "X":
										manage = "";
										System.out.println("Exiting");
										Logger.logMessage("--------Exiting from Database Manager");
										break;
								}
							}
						}else {
							System.out.println("Not authorized");
						}
						break;
						
					case "Q":
						Logger.logMessage("user " + username + " performing the quiz");
						System.out.println("Do you want to read the rules ?: ");
						String rule = AskRequests.askBool(scanner);
						if (rule.equals("Y")) {
							Logger.logMessage("user " + username + " reading the rules");
						}else {
							Logger.logMessage("user " + username + " not reading the rules");
						}
						double totalAssociative = 0;
						double totalOpen = 0;
						double totalmcq = 0;
						boolean quiz = true;
						System.out.println("Would you like to work with topics ?: ");
						String work = AskRequests.askBool(scanner);
						ArrayList <String> topicList = new ArrayList <String>();
						switch (work) {
							case "Y" :
								String topic = null;
								while (topic == null) {
									topic = InputHelper.getStringInput("Your topic: ");
									if (!topic.equals(null) && !topic.equals("TOPIC")) {
										topicList.add(topic);
										topic = null;
									}else if(topic.equals("TOPIC")) {
										break;
									}else {
										topic = null;
									}
								}
								ArrayList <ArrayList < ArrayList <String> > > openList = new ArrayList <ArrayList < ArrayList <String> > >();
								openList = OpenQuestion.openQuestionWith(topicList, username, password);
								if (!openList.isEmpty()) {
									totalOpen = OpenQuestion.performWith(openList);
								}else {
									System.out.println("Can't perform a quiz; there is no question found");
									quiz = false;
									break;
								}
								ArrayList <ArrayList < ArrayList <ArrayList <String>> > > mcqList = new ArrayList <ArrayList < ArrayList <ArrayList <String>> > >();
								mcqList = MCQQuestion.mcqQuestionWith(topicList, username, password);
								if (!mcqList.isEmpty()) {
									totalmcq = MCQQuestion.performWith(mcqList);
								}else {
									System.out.println("Can't perform a quiz; there is no question found");
									quiz = false;
									break;
								}
								break;
							default :
								ArrayList <ArrayList <String> > openArr = new ArrayList <ArrayList <String> >();
								openArr = OpenQuestion.openQuestionWithout(username, password, 4);
								if (!openArr.isEmpty()) {
									System.out.println(openArr);
									totalOpen = OpenQuestion.performWithout(openArr);
								}else {
									System.out.println("Can't perform a quiz; there is no question found");
									quiz = false;
									break;
								}
								ArrayList <ArrayList <ArrayList <String>> > mcqArr = new ArrayList <ArrayList <ArrayList <String>> >();
								mcqArr = MCQQuestion.mcqQuestionWithout(3, username, password);
								if (!mcqArr.isEmpty()) {
									totalmcq = MCQQuestion.performWithout(mcqArr);
								}else {
									System.out.println("Can't perform a quiz; there is no question found");
									quiz = false;
									break;
								}
								break;
						}
						if (quiz) totalAssociative = (double) AssociativeQuestion.Associative(username, password, 3);		
						if (quiz) {
							double total = (totalAssociative + totalOpen + totalmcq) / 3;
							System.out.println("Your final grade is: " + total*100 + "%.");
							break;
						}else {
							System.out.println("Quiz aborted.");
						}
					case "X":
						start = "";
						System.out.println("Exiting");
						Logger.logMessage("--------Exiting from the system");
						break;
				}
			}
			
			
		}else {
			System.out.println("Wrong credentials; not authenticated, exiting");
			Logger.logMessage("authentication failure for that user " + username);
		}
		

		
		scanner.close();
		Logger.logMessage("-------- End of program");

	}

	/**
	 * This function will check if the user is authorised to access to the system
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return A boolean is the user is authorized or not
	 */
	private static boolean authenticate(String username, String password) {
		File file = new File("auth.txt");
		Scanner scanner;
		String user = null;
		String pwd = null;
		try {
			scanner = new Scanner(file);
			String nextLine = scanner.nextLine();
			String[] parts = nextLine.split("=");
			user  = parts[0];
			pwd = parts[1];
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return (username.equals(user) && password.equals(pwd));
		
	}
	
	/**
	 * This function will check if the user is authorised to manage the system
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @return A boolean regarding to the fact that the user is an admin or not
	 */
	private static boolean admin(String username, String password) {
		File file = new File("admin.txt");
		Scanner scanner;
		String user = null;
		String pwd = null;
		try {
			scanner = new Scanner(file);
			String nextLine = scanner.nextLine();
			String[] parts = nextLine.split("=");
			user  = parts[0];
			pwd = parts[1];
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
		return (username.equals(user) && password.equals(pwd));
		
	}
	
		
	/**
	 * Prints a message to the user
	 */
	private static void notPossible () {
		System.out.println("This operation is not possible");
	}

}
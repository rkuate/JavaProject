package fr.epita.services;

import java.util.Scanner;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class AskRequests {
	
	/**
	 * Represents a simple String
	 */
	public static final String ASK = "Your choice: ";
	
	/**
	 * This function will ask to the user to operate a choice.
	 * @param scanner A Scanner allowing the user input values
	 * @return A representing the input entered by the user
	 */
	public static String askLevelMCQCreate(Scanner scanner){
		System.out.println("Which action do you want to operate next ?");
		System.out.println("O - Continue | X - Abort the operation | Any other input - No");
		System.out.println("If you enter less than 4 choices and values, you must continue ; or abort the operation");
		String ask = InputHelper.getStringInput("Your choice: ");
		return ask;
	}
	
	/**
	 * This function will ask to the user to operate a choice.
	 * @param scanner A Scanner allowing the user input values
	 * @return A representing the input entered by the user
	 */
	public static String askLevelCreateTopic (Scanner scanner) {
		String ask = null;
		System.out.println("Would you want to update a Question's topic or an MCQQuestion's topic ?");
		System.out.println("Q - Question | M - MCQQuestion | X - Exit");
		while (ask == null) {
			ask = InputHelper.getStringInput("Your choice: ");
			if (ask.equals("Q") || ask.equals("M") || ask.equals("X")) {
				System.out.println("Correct choice.");
			}else {
				System.out.println("Wrong choice.");
				ask = null;
			}
		}
		return ask;
	}
	
	/**
	 * This function will ask to the user to operate a choice.
	 * @param scanner A Scanner allowing the user input values
	 * @return A representing the input entered by the user
	 */
	public static String askLevelTwo (Scanner scanner) {
		String askQuestion = null;
		System.out.println("C - Create | D - Delete | U - Update | S - Search | E - Exit");
		while (askQuestion==null) {
			askQuestion = InputHelper.getStringInput(ASK);
			if (askQuestion.equals("C") || askQuestion.equals("D") || askQuestion.equals("U") || askQuestion.equals("S") || askQuestion.equals("E")) {
				System.out.println("Correct Choice");
			}else {
				System.out.println("Wrong choice");
				askQuestion=null;
			}
		}
		return askQuestion;
	}
	
	/**
	 * This function will ask to the user to operate a choice.
	 * @param scanner A Scanner allowing the user input values
	 * @return A representing the input entered by the user
	 */
	public static String askBool (Scanner scanner) {
		String rule = null;
		System.out.println("y/Y - [Yes] | Any other input - [No]");
		rule = InputHelper.getStringInput(ASK);
		return rule;
	}
	
	/**
	 * This function will ask to the user to operate a choice.
	 * @param scanner A Scanner allowing the user input values
	 * @return A representing the input entered by the user
	 */
	public static String askLevelManage (Scanner scanner) {
		String askManage = null;
		System.out.print("Which table do you want to manage: ");
		System.out.println("A - Answer | Q - Question | T - Topic | MQ - MCQQuestion | C - MCQChoices | X - Exit");
		while (askManage==null) {
			askManage = InputHelper.getStringInput(ASK);
			if (askManage.equals("A") || askManage.equals("Q") || askManage.equals("T") || askManage.equals("MQ") || askManage.equals("X")) {
				System.out.println("Correct Choice");
			}else {
				System.out.println("Wrong choice");
				askManage = null;
			}
		}
		return askManage;
	}
	
	/**
	 * This function will ask to the user to operate a choice.
	 * @param scanner A Scanner allowing the user input values
	 * @return A representing the input entered by the user
	 */
	public static String askLevelOne (Scanner scanner) {
		System.out.print("Choose an operation: ");
		System.out.println("M - To manage the database | Q - To perform a quiz | X - To exit");
		String askOne=null;
		while (askOne==null) {
			askOne = InputHelper.getStringInput(ASK);
			askOne=askOne.toUpperCase();
			if (askOne.equals("M") || askOne.equals("Q") || askOne.equals("X")) {
				System.out.println("Correct choice");
			}else{
				askOne=null;
				System.out.println("Wrong Choice");
			}
		}
 	return askOne;
	}
}

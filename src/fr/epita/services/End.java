package fr.epita.services;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class End {
	
	/**
	 * This function will print the ending message after performing a CRUD on Questions
	 * @param result A boolean representing the exit status of the query
	 * @param theEnd A String representing the String to print
	 * @param num An int representing the id of the question involved in the query
	 */
	public static void endQuestion (boolean result, String theEnd, int num) {
			if (result) {
				System.out.println("Question with primary key '" + num + "' successfully " + theEnd);
			}else {
				System.out.println("No question " + theEnd);
			}	
	}
	
	/**
	 * This function will print the ending message after performing a CRUD on Answers
	 * @param result A boolean representing the exit status of the query
	 * @param theEnd A String representing the String to print
	 * @param num An int representing the id of the answer involved in the query
	 */
	public static void endAnswer (boolean result, String theEnd, int num) {
		if (result) {
			System.out.println("Answer with primary key '" + num + "' successfully " + theEnd);
		}else {
			System.out.println("No Answer " + theEnd);
		}
		
	}
	
	/**
	 * This function will print the ending message after performing a CRUD on Topic
	 * @param result A boolean representing the exit status of the query
	 * @param theEnd A String representing the String to print
	 * @param num An int representing the id of the Topic involved in the query
	 */
	public static void endTopic (boolean result, String theEnd, int num) {
		if (result) {
			System.out.println("Topic with primary key '" + num + "' successfully " + theEnd);
		}else {
			System.out.println("No topic " + theEnd);
		}
	}
	
	/**
	 * This function will print the ending message after performing a CRUD on MCQQuestions
	 * @param result A boolean representing the exit status of the query
	 * @param theEnd A String representing the String to print
	 * @param num An int representing the id of the MCQQuestion involved in the query
	 */
	public static void endMCQQuestion (boolean result, String theEnd, int num) {
		if (result) {
			System.out.println("MCQ Question with primary '" + num + "' successfully " + theEnd);
		}else {
			System.out.println("No MCQ Question " + theEnd);
		}
	}
	
	/**
	 * This function will print the ending message after performing a CRUD on MCQChoices
	 * @param result A boolean representing the exit status of the query
	 * @param theEnd A String representing the String to print
	 * @param num An int representing the id of the MCQChoice involved in the query
	 */
	public static void endMCQChoice (boolean result, String theEnd, int num) {
		if (result) {
			System.out.println("MCQ Choice with primary '" + num + "' successfully " + theEnd);
		}else {
			System.out.println("No MCQ Choice " + theEnd);
		}
	}

}

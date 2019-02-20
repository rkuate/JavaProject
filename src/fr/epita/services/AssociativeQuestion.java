package fr.epita.services;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class AssociativeQuestion {
	
	/**
	 * This function will perform a set of associative questions
	 * @param username A String representing the username of the user
	 * @param password A String representing the password of the user
	 * @param number An int representing the number of Associative Questions needed
	 * @return An int the grade of the user after performing the Associative Questions 
	 * @throws SQLException Handle the SQL Exception thrown by the closure of the Result Set
	 */
	public static int Associative (String username, String password, int number) throws SQLException {
		int total = 0;
		ArrayList <ArrayList <String> > associativeList = new ArrayList <ArrayList <String> > ();
		ArrayList<String> questionList = new ArrayList<String>();
		ArrayList<String> answerList = new ArrayList<String>();
		ArrayList<String> answerListInput = new ArrayList<String>();
		
		associativeList = OpenQuestion.openQuestionWithout(username, password, number);
		for (ArrayList<String> arrayList : associativeList) {
			questionList.add(arrayList.get(1));
			answerList.add(arrayList.get(7));
		}
		if (!questionList.isEmpty() && !answerList.isEmpty()) {
			int i = 0;
			System.out.println("These are the questions");
			for (String question : questionList) {
				i++;
				System.out.println(i + ". " + question);
			}
			i = 0;
			System.out.println("These are your choices");
			for (String answer : answerList) {
				i++;
				System.out.println(i + ". " + answer);
			}
			System.out.println("Match questions with answers ; enter the correct order of answer separated by '-' without spaces");
			String answerString = InputHelper.getStringInput("Your String: ");
			String [] answerStringArray = answerString.split("-");
			for (int j = 0 ; j < answerStringArray.length ; j++) {
				switch (answerStringArray[j]) {
					case "1" :
						answerListInput.add(answerList.get(0));
						break;
					case "2" :
						answerListInput.add(answerList.get(1));
						break;
					case "3" :
						answerListInput.add(answerList.get(2));
						break;
					case "4" :
						answerListInput.add(answerList.get(3));
						break;
				}
			}
			if (answerList.toString().equals(answerListInput.toString())) {
				total++;
			}
		}else {
			System.out.println("Can't continue the quiz, there is no assciative question found");
		}
		
		return total;
	}
}

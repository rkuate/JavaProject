package fr.epita.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class InputHelper {

	/**
	 * This function will return an input entered by the user
	 * @param prompt A String representing the String to print to the user
	 * @return A String containing the input of the user
	 */
	public static String getInput(String prompt) {
		BufferedReader stdin = new BufferedReader(
				new InputStreamReader(System.in));

		System.out.print(prompt);
		System.out.flush();

		try {
			return stdin.readLine();
		} catch (Exception e) {
			return "Error: " + e.getMessage();
		}
	}

	/**
	 * This function will get a String input from the user
	 * @param prompt A String representing the prompt to display to the user
	 * @return A String containing the input of the user in UPPERCASE
	 */
	public static String getStringInput(String prompt){
		return getInput(prompt).toUpperCase();

	}
	
	/**
	 * This function will get an Integer input from the user
	 * @param prompt A String representing the prompt to display to the user
	 * @return An Integer containing the Integer input of the user
	 */
	public static int getIntegerInput(String prompt){
		return Integer.parseInt(getInput(prompt));	
	}
	
	/**
	 * This function will get a Boolean input from the user
	 * @param prompt A String representing the prompt to display to the user
	 * @return A boolean containing the Boolean input of the user
	 */
	public static boolean getBooleanInput (String prompt) {
		String enteredBoolean = getInput(prompt).toLowerCase();
		Boolean finalBoolean = null;
		if (enteredBoolean.equals("true") || enteredBoolean.equals("false")) {
			finalBoolean = Boolean.parseBoolean(enteredBoolean);
		}else {
			System.out.println("Wrong boolean entered"); 
		}
		return finalBoolean;
	}
}

package fr.epita.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class Logger {

	/**
	 * A PrintWriter 
	 */
	private static PrintWriter writer;

	/**
	 * Represents the boolean to check for keeping track of operations or initialise a new instance of Writer first
	 */
	private static boolean initialized;

	/**
	 * This function will keep track of all the operations performed by the connected user
	 * @param message A String representing the operation to keep track for
	 */
	public static void logMessage(String message) {
		if (!initialized) {
			try {
				writer = new PrintWriter(new FileWriter(new File("application.log"), true));
			}catch(IOException e) {
				System.out.println("error while initializing logging file");
				e.printStackTrace();
				writer = new PrintWriter(System.out);
			}
			initialized = true;
		}
		Date date = new Date();
		String format = "yyyy-MM-dd_HH:mm:ss,SSS";

		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		writer.println(dateFormat.format(date) + " " + message);
		writer.flush();
	}

}

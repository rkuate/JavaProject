package fr.epita.datamodel;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class MCQChoice {
	
	/**
	 * Represents the id of the MCQChoice
	 */
	private int idMCQChoice;
	
	/**
	 * Represents the text of the MCQChoice
	 */
	private String choice;
	
	/**
	 * Represents the value of the MCQChoice
	 */
	private boolean valid;
	
	/**
	 * Represents the MCQAnswer of the MCQChoice
	 */
	private MCQAnswer mcQAnswer;
	
	/** Get the text of the MCQChoice
	 * @return A String representing the text of the MCQChoice
	 */
	public String getChoice() {
		return choice;
	}
	
	/** Get the MCQAnswer of the MCQAnswer
	 * @return A MCQAnswer representing the MCQAnswer of the MCQChoice
	 */
	public MCQAnswer getMcQAnswer() {
		return mcQAnswer;
	}

	/** Set the MCQAnswer of the MCQChoice
	 * @param mcQAnswer A MCQAnswer containing the MCQAnswer of the MCQChoice
	 */
	public void setMcQAnswer(MCQAnswer mcQAnswer) {
		this.mcQAnswer = mcQAnswer;
	}

	/** Set the text of the MCQChoice
	 * @param choice A String containing the text of the MCQChoice
	 */
	public void setChoice(String choice) {
		this.choice = choice;
	}
	
	/** Get the value of the MCQChoice
	 * @return A boolean representing the value of the MCQChoice
	 */
	public boolean isValid() {
		return valid;
	}
	
	/** Set the value of the MCQChoice
	 * @param valid A boolean containing the value of the MCQChoice
	 */
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
	/** Set the id of the MCQChoice
	 * @param idMCQChoice An int containing the id of the MCQChoice
	 */
	public void setIdMCQChoice( int idMCQChoice) {
		this.idMCQChoice = idMCQChoice;
	}
	
	/** Get the id of the MCQChoice
	 * @return A int representing the id of the MCQChoice
	 */
	public int getIdMCQChoice() {
		return idMCQChoice;
	}
	
}

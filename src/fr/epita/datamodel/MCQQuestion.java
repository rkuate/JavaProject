package fr.epita.datamodel;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class MCQQuestion extends Question {
	
	/**
	 * Represents the id of the MCQQuestion
	 */
	private int idMCQQuestion;
	
	/**
	 * Represents the MCQChoice of the MCQQuestion 
	 */
	private MCQChoice mcqChoice;
	
	/** Get the id of the MCQQuestion
	 * @return An int representing the id of the MCQQuestion
	 */
	public int getIdMCQQuestion() {
		return idMCQQuestion;
	}

	/** Get the MCQChoice of the MCQQuestion
	 * @return A MCQChoice representing the MCQChoice of the MCQQuestion
	 */
	public MCQChoice getMcqChoice() {
		return mcqChoice;
	}

	/** Set the MCQChoice of the MCQQuestion
	 * @param mcqChoice A MCQChoice containing the MCQChoice of the MCQQuestion
	 */
	public void setMcqChoice(MCQChoice mcqChoice) {
		this.mcqChoice = mcqChoice;
	}

	/** Set the id of the MCQQuestion
	 * @param idMCQQuestion An int containing the id of the MCQQuestion
	 */
	public void setIdMCQQuestion(int idMCQQuestion) {
		this.idMCQQuestion = idMCQQuestion;
	}
	
}

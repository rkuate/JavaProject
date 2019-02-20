package fr.epita.datamodel;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class Answer {
	
	/**
	 * Represents the text of the answer  
	 */
	private String text;
	/**
	 * Represents the id of the answer
	 */
	private int idAnswer;

	/** Get the text of the answer
	 * @return A String representing the text of the answer
	 */
	public String getText() {
		return text;
	}

	/** Set the text of the answer
	 * @param text A String containing the text of the answer
	 */
	public void setText(String text) {
		this.text = text;
	}

	/** Get the id of the answer
	 * @return An int representing the id of the answer
	 */
	public int getIdAnswer() {
		return idAnswer;
	}

	/** Set the id of the answer
	 * @param idAnswer An int containing the id of the answer
	 */
	public void setIdAnswer(int idAnswer) {
		this.idAnswer = idAnswer;
	}
	
	
	
}

package fr.epita.datamodel;


/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class Question {
	
	/**
	 * Represents the id of the question
	 */
	private int questionId;
	
	/**
	 * Represents the title of the question
	 */
	private String title;
	
	/**
	 * Represents the first hint of the question
	 */
	private String hintOne;
	
	/**
	 * Represents the second hint of the question
	 */
	private String hintTwo;
	
	/**
	 *  Represents the third hint of the question
	 */
	private String hintThree;
	
	/**
	 * Represents the difficulty of the question
	 */
	private int level;
	
	/**
	 * Represents the Answer of the question
	 */
	private Answer answer;
	
	/** Get the title of the question
	 * @return A String representing the title of the question
	 */
	public String getTitle() {
		return title;
	}
	
	/** Set the title of the question
	 * @param question A String containing the title of the question
	 */
	public void setTitle(String question) {
		this.title = question;
	}
	
	/** Get the difficulty of the question
	 * @return An int representing the difficulty of the question
	 */
	public int getLevel() {
		return level;
	}
	
	/** Set the difficulty of the question
	 * @param level An int representing the difficulty of the question
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/** Get the id of the question
	 * @return An int representing the id of the question
	 */
	public int getQuestionId() {
		return questionId;
	}
	
	/** Set the id of the question
	 * @param questionId An int containing the id of the question
	 */
	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	/** Get the first hint of the question
	 * @return A String representing the first hint of the question
	 */
	public String getHintOne() {
		return hintOne;
	}
	
	/** Set the first hint of the question
	 * @param hintOne A String containing the first hint of the question
	 */
	public void setHintOne(String hintOne) {
		this.hintOne = hintOne;
	}
	
	/** Get the second hint of the question
	 * @return A String representing the second hint of the question
	 */
	public String getHintTwo() {
		return hintTwo;
	}
	
	/** Set the second hint of the question
	 * @param hintTwo A String containing the second hint of the question
	 */
	public void setHintTwo(String hintTwo) {
		this.hintTwo = hintTwo;
	}
	
	/** Get the third hint
	 * @return A String representing the third hint of the question
	 */
	public String getHintThree() {
		return hintThree;
	}
	
	/** Set the third hint of the question
	 * @param hintThree A String containing the third hint of the question
	 */
	public void setHintThree(String hintThree) {
		this.hintThree = hintThree;
	}
	 
	/** Get the Answer of the Question
	 * @return An Answer representing the Answer of the question 
	 */
	public Answer getAnswer() {
		return answer;
	}
	
	/** Set the Answer of the question
	 * @param answer An Answer containing the Answer of the question
	 */
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
}

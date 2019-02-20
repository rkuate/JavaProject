package fr.epita.datamodel;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class Quiz {
	
	/**
	 * Represents the id of the quiz
	 */
	private int idQuiz;
	
	/**
	 * Represents the title of the quiz 
	 */
	private String title;
	
	/**
	 * Represents the MCQAnswer of the Quiz 
	 */
	private MCQAnswer mcqAnswer;
	
	/**
	 * Represents the Student of the quiz 
	 */
	private Student student;

	/** Get the title of the quiz
	 * @return A String representing the title of the quiz
	 */
	public String getTitle() {
		return title;
	}

	/** Set the title of the title
	 * @param title A String containing the title of the quiz
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** Get the id of the quiz
	 * @return An int representing the id of the quiz
	 */
	public int getIdQuiz() {
		return idQuiz;
	}

	/** Get the MCQAnswer of the quiz
	 * @return A MCQAnswer representing the MCQAnswer of the quiz
	 */
	public MCQAnswer getMcqAnswer() {
		return mcqAnswer;
	}

	/** Set the MCQAnswer of the quiz
	 * @param mcqAnswer A MCQAnswer containing the MCQAnswer of the quiz
	 */
	public void setMcqAnswer(MCQAnswer mcqAnswer) {
		this.mcqAnswer = mcqAnswer;
	}

	/** Get the student of the quiz
	 * @return A Student representing the Student of the quiz
	 */
	public Student getStudent() {
		return student;
	}

	/** Set the Student of the quiz
	 * @param student A Student object containing the Student of the quiz
	 */
	public void setStudent(Student student) {
		this.student = student;
	}

	/** Set the id of the quiz
	 * @param idQuiz An int containing the id of the quiz
	 */
	public void setIdQuiz(int idQuiz) {
		this.idQuiz = idQuiz;
	}
	
	
}

package fr.epita.datamodel;

/**
 * @author Russel Kuate
 * @author russelr064@gmail.com
 * @author Jacques Huchard
 * @author jstephaneh@gmail.com
 */
public class Student {
	
	/**
	 * Represents the id of the student
	 */
	private int idStudent;
	
	/**
	 * Represents the name of the Student
	 */
	private String name;
	
	/**
	 * Represents the Answer of the Student 
	 */
	private Answer answer;
	
	/**
	 * Represents the MCQAnswer of the Student
	 */
	private MCQAnswer mcqAnswer;
	
	/** Get the name of the Student
	 * @return A String representing the name of the Student
	 */
	public String getName() {
		return name;
	}
	
	/** Set the name of the Student
	 * @param name A String containing the name of the Student
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/** Get the id of the Student
	 * @return An int representing the id of the Student
	 */
	public int getIdStudent() {
		return idStudent;
	}
	
	/** Get the Answer of the Student
	 * @return An Answer representing the Answer of the Student
	 */
	public Answer getAnswer() {
		return answer;
	}
	
	/** Set the Answer of the Student
	 * @param answer An Answer containing the Answer of the Student
	 */
	public void setAnswer(Answer answer) {
		this.answer = answer;
	}
	
	/** Get the MCQAnswer of the Student
	 * @return A MCQAnswer representing the MCQAnswer of the Student
	 */
	public MCQAnswer getMcqAnswer() {
		return mcqAnswer;
	}
	
	/** Set the MCQAnswer of the Student
	 * @param mcqAnswer A MCQAnswer containing the MCQAnswer of the Student
	 */
	public void setMcqAnswer(MCQAnswer mcqAnswer) {
		this.mcqAnswer = mcqAnswer;
	}
	
	/** Set the id of the Student
	 * @param idStudent An int containing the id of the Student
	 */
	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}
	
	
}

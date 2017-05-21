package exceptions;

public class DuplicateAthleteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public DuplicateAthleteException() { super(); }
	public DuplicateAthleteException(String message) { super(message); }
	public DuplicateAthleteException(String message, Throwable cause) { super(message, cause); }
	public DuplicateAthleteException(Throwable cause) { super(cause); }
}

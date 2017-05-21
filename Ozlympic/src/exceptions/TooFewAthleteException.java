package exceptions;

public class TooFewAthleteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public TooFewAthleteException() { super(); }
	public TooFewAthleteException(String message) { super(message); }
	public TooFewAthleteException(String message, Throwable cause) { super(message, cause); }
	public TooFewAthleteException(Throwable cause) { super(cause); }
}

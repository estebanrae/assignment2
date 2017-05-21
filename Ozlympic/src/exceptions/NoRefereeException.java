package exceptions;

public class NoRefereeException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public NoRefereeException() { super(); }
	public NoRefereeException(String message) { super(message); }
	public NoRefereeException(String message, Throwable cause) { super(message, cause); }
	public NoRefereeException(Throwable cause) { super(cause); }
}

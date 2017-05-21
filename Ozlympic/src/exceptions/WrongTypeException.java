package exceptions;

public class WrongTypeException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public WrongTypeException() { super(); }
	public WrongTypeException(String message) { super(message); }
	public WrongTypeException(String message, Throwable cause) { super(message, cause); }
	public WrongTypeException(Throwable cause) { super(cause); }

}

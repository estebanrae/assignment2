package exceptions;

public class GameFullException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GameFullException() { super(); }
	public GameFullException(String message) { super(message); }
	public GameFullException(String message, Throwable cause) { super(message, cause); }
	public GameFullException(Throwable cause) { super(cause); }
}

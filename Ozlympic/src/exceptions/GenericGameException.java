package exceptions;

public class GenericGameException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public GenericGameException() { super(); }
	public GenericGameException(String message) { super(message); }
	public GenericGameException(String message, Throwable cause) { super(message, cause); }
	public GenericGameException(Throwable cause) { super(cause); }
}

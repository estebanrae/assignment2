package exceptions;

public class ImageNotFoundException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ImageNotFoundException() { super(); }
	public ImageNotFoundException(String message) { super(message); }
	public ImageNotFoundException(String message, Throwable cause) { super(message, cause); }
	public ImageNotFoundException(Throwable cause) { super(cause); }
}

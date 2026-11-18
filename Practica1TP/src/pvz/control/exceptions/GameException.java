package pvz.control.exceptions;

/**
 * 		Abstract exception class
 * 
 * 		@author Rodrigo Domínguez Carramal
 */


public abstract class GameException extends Exception {

	private static final long serialVersionUID = 1L;

	public GameException(String message) {
		super(message);
	}
	
	public GameException(String message, Throwable cause) {
		super(message, cause);
	}
}

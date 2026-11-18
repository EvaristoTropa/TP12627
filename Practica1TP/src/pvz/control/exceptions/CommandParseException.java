package pvz.control.exceptions;

/**
 * 		Handles exceptions when receiving and identifying a Command
 * 
 * 		@author Rodrigo Domínguez Carramal
 */

public class CommandParseException extends GameException{

	private static final long serialVersionUID = 1L;
	
	public CommandParseException(String message) {
		super(message);
	}
	
	public CommandParseException(String message, Throwable cause) {
		super(message, cause);
	}
}

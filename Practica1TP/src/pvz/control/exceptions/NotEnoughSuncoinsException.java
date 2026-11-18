package pvz.control.exceptions;


/**
 * 		Handles exceptions when trying to buy plants with out enough money
 * 
 * 		@author Rodrigo Domínguez Carramal
 */

public class NotEnoughSuncoinsException extends CommandExecuteException{

	private static final long serialVersionUID = 1L;

	public NotEnoughSuncoinsException(String message) {
		super(message);
	}
	
	public NotEnoughSuncoinsException(String message, Throwable cause) {
		super(message, cause);
	}
}

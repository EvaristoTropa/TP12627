package pvz.control.exceptions;
/**
 * 		Handles exceptions when trying to execute the Catch command
 * 
 * 		@author Rodrigo Domínguez Carramal
 */

public class NotCatchablePositionException extends InvalidPositionException{
	
	private static final long serialVersionUID = 1L;

	public NotCatchablePositionException(String message, int col, int row) {
		super(message, col, row);
	}
	
	public NotCatchablePositionException(String message, Throwable cause, int col, int row) {
		super(message, col, row, cause);
	}
}

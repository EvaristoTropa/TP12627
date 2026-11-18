package pvz.control.exceptions;

/**
 * 		Handles exceptions when problems with positions given arise 
 * 		when executing a Command
 * 
 * 		@author Rodrigo Domínguez Carramal
 */


public class InvalidPositionException extends CommandExecuteException{

	private static final long serialVersionUID = 1L;
	private final int col, row;

	public InvalidPositionException(String message, int col, int row) {
		this.col = col;
		this.row = row;
		super(message);
	}

	public InvalidPositionException(String message,int col, int row, Throwable cause) {
		this.col = col;
		this.row = row;
		super(message, cause);
	}
}

package pvz.control.command;

import pvz.control.exceptions.CommandParseException;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * "Command" that shows an error message (cannot be executed directly by the user).
 * 
 * @author Rodrigo Ferrer López & Rodrigo Domínguez Carramal
 */

public class ErrorCommand extends Command {
	private String message;
	
	public ErrorCommand(String message) {
		this.message = message;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return true;
	}
	
	@Override
	public Command create(String[] commandWords) throws CommandParseException {
		 if (commandWords.length != 0) {
			    throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
			  }
		return this;
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		view.showError(message);
	}
}
package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;

/**
 * "Command" that shows an error message (cannot be executed directly by the user).
 * 
 * @author Rodrigo Ferrer López
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
	public Command create(String[] commandWords) {
		return this;
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		view.showError(message);
	}
}
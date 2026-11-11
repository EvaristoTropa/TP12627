package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * "Command" that represents invalid commands.
 * 
 * @author Rodrigo Ferrer López
 */

public class UnknownCommand extends Command {
	
	public UnknownCommand() {}
	
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
		view.showError(Messages.UNKNOWN_COMMAND);
	}
}
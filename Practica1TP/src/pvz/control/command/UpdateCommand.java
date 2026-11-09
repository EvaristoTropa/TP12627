package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Command that skips the player's turn and updates the game.
 * 
 * @author Rodrigo Ferrer López
 */

public class UpdateCommand extends Command {
	public UpdateCommand() {
		this.name = Messages.COMMAND_UPDATE_NAME;
		this.shortcut = Messages.COMMAND_UPDATE_SHORTCUT;
		this.details = Messages.COMMAND_UPDATE_DETAILS;
		this.help = Messages.COMMAND_UPDATE_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut) || command == "";
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length <= 1)
			return this;
		else
			return new ErrorCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		model.update();
		view.showGame();
	}
}
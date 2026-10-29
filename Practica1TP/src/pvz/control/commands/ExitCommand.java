package pvz.control.commands;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Command that quits the game.
 * 
 * @author Rodrigo Ferrer López
 */

public class ExitCommand extends Command {
	public ExitCommand() {
		this.name = Messages.COMMAND_EXIT_NAME;
		this.shortcut = Messages.COMMAND_EXIT_SHORTCUT;
		this.details = Messages.COMMAND_EXIT_DETAILS;
		this.help = Messages.COMMAND_EXIT_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command == name || command == shortcut;
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 1)
			return this;
		else
			return null;
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		model.quit();
	}
}
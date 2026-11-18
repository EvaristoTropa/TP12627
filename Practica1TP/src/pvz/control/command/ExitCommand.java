package pvz.control.command;

import pvz.control.exceptions.CommandParseException;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Command that quits the game.
 * 
 * @author Rodrigo Ferrer López & Rodrigo Domínguez Carramal
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
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut);
	}
	
	@Override
	public Command create(String[] commandWords) throws CommandParseException {
		if (commandWords.length == 1)
			return this;
		else if (commandWords.length < 1)
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		else
			throw new CommandParseException(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		model.quit();
	}
}
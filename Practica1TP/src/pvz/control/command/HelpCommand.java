package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Command that shows all available commands and how to use them.
 * 
 * @author Rodrigo Ferrer López
 */

public class HelpCommand extends Command {
	public HelpCommand() {
		this.name = Messages.COMMAND_HELP_NAME;
		this.shortcut = Messages.COMMAND_HELP_SHORTCUT;
		this.details = Messages.COMMAND_HELP_DETAILS;
		this.help = Messages.COMMAND_HELP_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut);
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 1)
			return this;
		else if (commandWords.length < 1)
			return new ErrorCommand(Messages.COMMAND_PARAMETERS_MISSING);
		else
			return new ErrorCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		StringBuilder help = new StringBuilder();
		help.append(Messages.HELP_AVAILABLE_COMMANDS + '\n');
		for (Command i : AVAILABLE_COMMANDS)
			help.append(i.getDetails() + ": " + i.getHelp() + '\n');
		view.showMessage(help.toString());
	}
}
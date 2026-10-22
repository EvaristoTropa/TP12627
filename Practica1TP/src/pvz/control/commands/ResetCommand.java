package pvz.control.commands;

import pvz.control.Level;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public class ResetCommand extends Command {
	private Level level;
	private long seed;
	
	public ResetCommand() {
		this.name = Messages.COMMAND_RESET_NAME;
		this.shortcut = Messages.COMMAND_RESET_SHORTCUT;
		this.details = Messages.COMMAND_RESET_DETAILS;
		this.help = Messages.COMMAND_RESET_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command == name || command == shortcut;
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 3) {
			level = Level.valueOfIgnoreCase(commandWords[1]);
			seed = Integer.parseInt(commandWords[2]);
			return this;
		}
		else
			return null;
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		model.reset();
	}
}
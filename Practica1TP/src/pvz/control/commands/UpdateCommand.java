package pvz.control.commands;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public class UpdateCommand extends Command {
	public UpdateCommand() {
		this.name = Messages.COMMAND_UPDATE_NAME;
		this.shortcut = Messages.COMMAND_UPDATE_SHORTCUT;
		this.details = Messages.COMMAND_UPDATE_DETAILS;
		this.help = Messages.COMMAND_UPDATE_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command == name || command == shortcut;
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length <= 1)
			return this;
		else
			return null;
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		model.update();
	}
}
package pvz.control.commands;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public class AddPlantCommand extends Command {
	public AddPlantCommand() {
		this.name = Messages.COMMAND_ADD_PLANT_NAME;
		this.shortcut = Messages.COMMAND_ADD_PLANT_SHORTCUT;
		this.details = Messages.COMMAND_ADD_PLANT_DETAILS;
		this.help = Messages.COMMAND_ADD_PLANT_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command == name || command == shortcut;
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 4)
			return this;
		else
			return null;
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		//...
	}
}
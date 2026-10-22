package pvz.control.commands;

import pvz.logic.gameobjects.Plant;ç
import pvz.utils.Position;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

public class AddPlantCommand extends Command {
	private Plant plant;
	private Position position;
	
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
		if (commandWords.length == 4) {
			plant = new Plant(commandWords[1]);
			position = new Position(Integer.parseInt(commandWords[3]), Integer.parseInt(commandWords[2]));
			return this;
		}
		else
			return null;
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		model.addGameObject(plant, position);
	}
}
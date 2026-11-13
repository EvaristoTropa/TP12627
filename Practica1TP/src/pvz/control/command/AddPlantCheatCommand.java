package pvz.control.command;

import java.util.List;

import pvz.logic.GameModel;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.factories.PlantFactory;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

/**
 * Command that lets you add a plant without spending coins (considered cheating).
 * 
 * @author Rodrigo Ferrer López
 */

public class AddPlantCheatCommand extends Command {
	private String plantName;
	private Position position;
	
	public AddPlantCheatCommand(String plantName, Position position) {
		this.plantName = plantName;
		this.position = position;
		this.name = Messages.COMMAND_CHEAT_PLANT_NAME;
		this.shortcut = Messages.COMMAND_CHEAT_PLANT_SHORTCUT;
		this.details = Messages.COMMAND_CHEAT_PLANT_DETAILS;
		this.help = Messages.COMMAND_CHEAT_PLANT_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut);
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 4) {
			String newPlantName = commandWords[1];
			int i = 0;
			List<Plant> plants = PlantFactory.getAvailablePlants();
			while (i < plants.size() && !plants.get(i).matchPlant(newPlantName))
				i++;
			if (i < plants.size()) {
				Position newPosition = new Position(Integer.parseInt(commandWords[3]), Integer.parseInt(commandWords[2]));
				return new AddPlantCheatCommand(newPlantName, newPosition);
			}
			else
				return new ErrorCommand(Messages.INVALID_GAME_OBJECT);
		}
		else if (commandWords.length < 4)
			return new ErrorCommand(Messages.COMMAND_PARAMETERS_MISSING);
		else
			return new ErrorCommand(Messages.INVALID_COMMAND);
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		if (!model.isValidPosition(position))
			view.showError(Messages.INVALID_POSITION);
		else {
			model.addGameObjectForFree(plantName, position);
			model.update();
			view.showGame();
		}
	}
}
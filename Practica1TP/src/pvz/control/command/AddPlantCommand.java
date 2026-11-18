package pvz.control.command;

import java.util.List;

import pvz.control.exceptions.CommandParseException;
import pvz.control.exceptions.InvalidPositionException;
import pvz.logic.GameModel;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.factories.PlantFactory;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

/**
 * Command that adds a plant in a specified position.
 * 
 * @author Rodrigo Ferrer López & Rodrigo Domínguez Carramal
 */

public class AddPlantCommand extends Command {
	private String plantName;
	private Position position;
	
	public AddPlantCommand(String plantName, Position position) {
		this.plantName = plantName;
		this.position = position;
		this.name = Messages.COMMAND_ADD_PLANT_NAME;
		this.shortcut = Messages.COMMAND_ADD_PLANT_SHORTCUT;
		this.details = Messages.COMMAND_ADD_PLANT_DETAILS;
		this.help = Messages.COMMAND_ADD_PLANT_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut);
	}
	
	@Override
	public Command create(String[] commandWords) throws CommandParseException {
		if (commandWords.length == 4) {
			String newPlantName = commandWords[1];
			int i = 0;
			List<Plant> plants = PlantFactory.getAvailablePlants();
			while (i < plants.size() && !plants.get(i).matchPlant(newPlantName))
				i++;
			if (i < plants.size()) {
				Position newPosition = new Position(Integer.parseInt(commandWords[3]), Integer.parseInt(commandWords[2]));
				return new AddPlantCommand(newPlantName, newPosition);
			}
			else
				throw new CommandParseException(Messages.INVALID_GAME_OBJECT);
		}
		else if (commandWords.length < 4)
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		else
			throw new CommandParseException(Messages.INVALID_COMMAND);
	}
	
	@Override
	public void execute(GameModel model, GameView view) throws InvalidPositionException{
		if (!model.isValidPosition(position))
			throw new InvalidPositionException(Messages.INVALID_POSITION, position.column(), position.row());
		else {
			int i = 0;
			List<Plant> plants = PlantFactory.getAvailablePlants();
			while (i < plants.size() && !plants.get(i).matchPlant(plantName))
				i++;
			if (model.getCoins() < plants.get(i).getCost())
				view.showError(Messages.NOT_ENOUGH_COINS);
			else {
			model.addGameObject(plantName, position);
			model.update();
			view.showGame();
			}
		}
	}
}
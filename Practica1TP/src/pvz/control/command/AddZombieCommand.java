package pvz.control.command;

import java.util.List;

import pvz.logic.GameModel;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.factories.ZombieFactory;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

/**
 * Command that adds a zombie in a specified position (considered cheating).
 * 
 * @author Rodrigo Ferrer López
 */

public class AddZombieCommand extends Command {
	private String zombieName;
	private Position position;
	
	public AddZombieCommand(String zombieName, Position position) {
		this.zombieName = zombieName;
		this.position = position;
		this.name = Messages.COMMAND_ADD_ZOMBIE_NAME;
		this.shortcut = Messages.COMMAND_ADD_ZOMBIE_SHORTCUT;
		this.details = Messages.COMMAND_ADD_ZOMBIE_DETAILS;
		this.help = Messages.COMMAND_ADD_ZOMBIE_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut);
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 4) {
			String newZombieName = commandWords[1];
			int i = 0;
			List<Zombie> zombies = ZombieFactory.getAvailableZombies();
			while (i < zombies.size() && !zombies.get(i).matchZombie(newZombieName))
				i++;
			if (i < zombies.size()) {
				Position newPosition = new Position(Integer.parseInt(commandWords[3]), Integer.parseInt(commandWords[2]));
				return new AddZombieCommand(newZombieName, newPosition);
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
			model.addZombie(zombieName, position);
			model.update();
			view.showGame();
		}
	}
}
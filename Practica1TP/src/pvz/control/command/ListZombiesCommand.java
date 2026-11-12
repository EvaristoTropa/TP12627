package pvz.control.command;

import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.factories.ZombieFactory;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Command that shows what zombies you can add.
 * 
 * @author Rodrigo Ferrer López
 */

public class ListZombiesCommand extends Command {
	public ListZombiesCommand() {
		this.name = Messages.COMMAND_LIST_ZOMBIES_NAME;
		this.shortcut = Messages.COMMAND_LIST_ZOMBIES_SHORTCUT;
		this.details = Messages.COMMAND_LIST_ZOMBIES_DETAILS;
		this.help = Messages.COMMAND_LIST_ZOMBIES_HELP;
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
		StringBuilder list = new StringBuilder();
		for (Zombie i : ZombieFactory.getAvailableZombies())
			list.append(i.getDescription() + '\n');
		view.showMessage(list.toString());
	}
}
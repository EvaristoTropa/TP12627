package pvz.control.command;

import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

/**
 * Command that catches all suns in a given position.
 * 
 * @author Rodrigo Ferrer López
 */

public class CatchCommand extends Command {
	Position position;
	
	public CatchCommand(Position position) {
		this.position = position;
		this.name = Messages.COMMAND_CATCH_NAME;
		this.shortcut = Messages.COMMAND_CATCH_SHORTCUT;
		this.details = Messages.COMMAND_CATCH_DETAILS;
		this.help = Messages.COMMAND_CATCH_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut);
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 3) {
			Position newPosition = new Position(Integer.parseInt(commandWords[2]), Integer.parseInt(commandWords[1]));
			return new CatchCommand(newPosition);
		}
		else if (commandWords.length < 3)
			return new ErrorCommand(Messages.COMMAND_PARAMETERS_MISSING);
		else
			return new ErrorCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		if (!model.catchUsed()) {
		if (!model.catchObject(position))
			view.showError(Messages.NO_CATCHABLE_IN_POSITION);
		else
			view.showGame();
		}
	}
}
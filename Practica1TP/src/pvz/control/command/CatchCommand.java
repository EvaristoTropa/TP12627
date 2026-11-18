package pvz.control.command;

import pvz.control.exceptions.CommandExecuteException;
import pvz.control.exceptions.CommandParseException;
import pvz.control.exceptions.InvalidPositionException;
import pvz.control.exceptions.NotCatchablePositionException;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;
import utils.Position;

/**
 * Command that catches all suns in a given position.
 * 
 * @author Rodrigo Ferrer López & Rodrigo Domínguez Carramal
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
	public Command create(String[] commandWords) throws CommandParseException {
		if (commandWords.length == 3) {
			Position newPosition = new Position(Integer.parseInt(commandWords[2]), Integer.parseInt(commandWords[1]));
			return new CatchCommand(newPosition);
		}
		else if (commandWords.length < 3)
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		else
			throw new CommandParseException(Messages.INVALID_COMMAND);
	}
	
	@Override
	public void execute(GameModel model, GameView view) throws CommandExecuteException{
		if (!model.isInsideBoard(position))
			throw new InvalidPositionException(Messages.INVALID_POSITION, position.column(), position.row());
		else if (!model.catchUsed()) {
		if (!model.catchObject(position))
			throw new NotCatchablePositionException(Messages.NO_CATCHABLE_IN_POSITION.formatted(position.column(), position.row()), position.column(), position.row());
		else
			view.showGame();
		}
		else
			view.showError("Cannot catch suns more than once per cycle");
	}
}
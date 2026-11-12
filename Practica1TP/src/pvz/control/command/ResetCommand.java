package pvz.control.command;

import pvz.control.Level;
import pvz.logic.GameModel;
import pvz.view.GameView;
import pvz.view.Messages;

/**
 * Command that resets the game to its initial parameters.
 * 
 * @author Rodrigo Ferrer López
 */

public class ResetCommand extends Command {
	private Level level;
	private long seed;
	
	public ResetCommand(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		this.name = Messages.COMMAND_RESET_NAME;
		this.shortcut = Messages.COMMAND_RESET_SHORTCUT;
		this.details = Messages.COMMAND_RESET_DETAILS;
		this.help = Messages.COMMAND_RESET_HELP;
	}
	
	@Override
	public boolean matchCommand(String command) {
		return command.equalsIgnoreCase(name) || command.equalsIgnoreCase(shortcut);
	}
	
	@Override
	public Command create(String[] commandWords) {
		if (commandWords.length == 3) {
			Level newLevel = Level.valueOfIgnoreCase(commandWords[1]);
			long newSeed = Integer.toUnsignedLong(Integer.parseInt(commandWords[2]));
			return new ResetCommand(newLevel, newSeed);
		}
		else if (commandWords.length == 1)
			return new ResetCommand(null, 0L);
		else
			return new ErrorCommand(Messages.COMMAND_INCORRECT_PARAMETER_NUMBER);
	}
	
	@Override
	public void execute(GameModel model, GameView view) {
		if (level == null)
			model.reset();
		else
			model.reset(level, seed);
		view.showGame();
	}
}
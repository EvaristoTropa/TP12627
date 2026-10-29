package pvz.control.commands;

import java.util.List;
import java.util.Arrays;

import pvz.logic.GameModel;
import pvz.view.GameView;

/**
 * Implementation of the Command pattern.
 * 
 * Manages all of the game's commands and provides them with abstract methods.
 * 
 * @author Rodrigo Ferrer López
 */

public abstract class Command {
	protected static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
			new AddPlantCommand(),
			new ListPlantsCommand(),
			new ResetCommand(),
			new HelpCommand(),
			new UpdateCommand(),
			new ExitCommand()
	);
	
	protected String name = "";
	protected String shortcut = "";
	protected String details = "";
	protected String help = "";
	
	/**
	 * Checks if the user's prompt matches a command, then creates if it does.
	 * 
	 * @param commandWords User input
	 * 
	 * @return the corresponding command if it matches, or null if there's no match.
	 */
	
	public static Command parse(String[] commandWords) {
		int i = 0;
		while (i < AVAILABLE_COMMANDS.size() && AVAILABLE_COMMANDS.get(i).matchCommand(commandWords[0]))
			i++;
		if (i < AVAILABLE_COMMANDS.size()) {
			Command command = AVAILABLE_COMMANDS.get(i).create(commandWords);
			return command;
		}
		else
			return null;
	}
	
	/**
	 * Fetches a command's name.
	 * 
	 * @return the command's name.
	 */
	
	protected String getName() {
		return name;
	}
	
	/**
	 * Fetches a command's shortcut.
	 * 
	 * @return the command's shortcut.
	 */
	
	protected String getShortcut() {
		return shortcut;
	}
	
	/**
	 * Fetches a command's details.
	 * 
	 * @return the command's details.
	 */
	
	public String getDetails() {
		return details;
	}
	
	/**
	 * Fetches a command's help message.
	 * 
	 * @return the command's help message.
	 */
	
	public String getHelp() {
		return help;
	}
	
	/**
	 * Checks if a string matches a command's name or shortcut.
	 * 
	 * @param command String to check
	 * 
	 * @return <code>true</code> if the string matches.
	 */
	
	abstract public boolean matchCommand(String command);
	
	/**
	 * Creates a command and processes its parameters, if any.
	 * 
	 * @param commandWords User prompt
	 * 
	 * @return the built command.
	 */
	
	abstract public Command create(String[] commandWords);
	
	/**
	 * Carries out the command's actions in the game.
	 * 
	 * @param model Game where actions are executed, view View where messages are shown
	 */
	
	abstract public void execute(GameModel model, GameView view);
}
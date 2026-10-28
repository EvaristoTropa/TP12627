package pvz.control.commands;

import java.util.List;
import java.util.Arrays;

import pvz.logic.GameModel;
import pvz.view.GameView;

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
	
	protected String getName() {
		return name;
	}
	
	protected String getShortcut() {
		return shortcut;
	}
	
	public String getDetails() {
		return details;
	}
	
	public String getHelp() {
		return help;
	}
	
	abstract public boolean matchCommand(String command);
	abstract public Command create(String[] commandWords);
	abstract public void execute(GameModel model, GameView view);
}
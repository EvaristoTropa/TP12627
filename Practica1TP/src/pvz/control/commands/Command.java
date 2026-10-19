package pvz.control.commands;

import java.util.Arrays;

public abstract class Command {
	private static final List<Command> AVAILABLE_COMMANDS = Arrays.asList(
			new AddPlantCommand();
			new ListPlantsCommand();
			new ResetCommand();
			new HelpCommand();
			new UpdateCommand();
			new ExitCommand();
	);
	
	//...
	protected String name = "";
	protected String shortcut = "";
	protected String details = "";
	protected String help = "";
	//...
	
	public static Command parse(String[] commandWords) {
		//...
	}
	
	//...
}
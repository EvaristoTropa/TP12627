package pvz.control;

import pvz.control.commands.Command;
import pvz.logic.Game;
import pvz.view.GamePrinter;
import pvz.view.GameView;
import pvz.view.Messages;
import pvz.utils.Position;
import pvz.logic.gameobjects.*;

/**
 * Input/output coordinator of the game (the C in MVC).
 *
 * <p>Owns the game loop: reads a line from stdin, parses it into a
 * command, validates parameters (plant type, position), delegates
 * state changes to {@link Game}, and triggers a board reprint via
 * {@link tp1.pvz.view.GameView>} when the cycle advances. It holds no game
 * state of its own; the source of truth is always {@link Game}.
 * 
 * @author Rodrigo Dominguez Carramal
 */
public class Controller {

	private final Game game;
	private final GameView view;
	enum Prompts {
		ADD("a", "add"), 
		LIST("l", "list"), 
		RESET("r", "reset"), 
		HELP("h", "help"), 
		EXIT("e", "exit"), 
		NONE("n", "none");
		
		private final String letter, name;
		
		Prompts(String letter, String name){
			this.letter = letter;
			this.name = name;
		}
		public static Prompts fromString(String text) {
			
			String t = text.trim().toLowerCase();
			for(Prompts p : values()) {
				if(t == p.letter ||t == p.name) return p;
			}
			return null;
		}
		
		public static Prompts fromString(String[] text) {
			if(text==null||text.length==0) return NONE;
			return fromString(text[0]);
		}
		
	};
	
	private void listPlants() {
	    System.out.println();

	    // Sunflower
	    System.out.println(String.format("[%s]unflower: cost='%d' suncoins, damage='%d', endurance='%d'",
	            Sunflower.shortName().toUpperCase(),
	            Sunflower.COST,
	            Sunflower.DAMAGE,
	            Sunflower.ENDURANCE));

	    // Peashooter
	    System.out.println(String.format("[%s]eashooter: cost='%d' suncoins, damage='%d', endurance='%d'",
	            Peashooter.shortName().toUpperCase(),
	            Peashooter.COST,
	            Peashooter.DAMAGE,
	            Peashooter.ENDURANCE));

	    System.out.println();
	}
	private void showHelp() {
		
		System.out.println(Messages.HELP_LINES);
	}

	public Controller(Game game) {
		this.game = game;
		this.view = new GamePrinter(game);
	}

	/**
	 * Runs the game logic.
	 */
	public void run() {
		while(!game.hasGameFinished() && !game.playerQuits()) {
			String[] words = view.getPrompt();
			Command command = Command.parse(words);
			if (command != null)
				command.execute(game, view);
			else
				view.showError(Messages.UNKNOWN_COMMAND);
		}	
	}

}
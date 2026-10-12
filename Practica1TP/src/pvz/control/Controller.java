package pvz.control;

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

	    // Sunflower
	    view.showMessage(String.format(Messages.SUNFLOWER_DESCRIPTION,
	            Sunflower.shortName().toUpperCase(),
	            Sunflower.COST,
	            Sunflower.DAMAGE,
	            Sunflower.ENDURANCE));

	    // Peashooter
	    view.showMessage(String.format(Messages.PEASHOOTER_DESCRIPTION,	            
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
		view.showGame();
		while(game.hasGameFinished()||game.playerQuits()) {
			String[] prompt = view.getPrompt();
			Prompts prompttype = Prompts.fromString(prompt);
			if(prompttype ==null) {
				view.showError(Messages.UNKNOWN_COMMAND);
			}else {
				switch (prompttype) {
				case ADD:
					if(prompt.length>4) {
						Position pos = new Position(Integer.parseInt(prompt[3]), Integer.parseInt(prompt[2]));
						String error = game.addGameObject(prompt[1], pos);
						if(error!=null) {
							view.showError(error);
						}
					}else {
						view.showError(Messages.COMMAND_PARAMETERS_MISSING);
					}
					game.update();
					view.showGame();
					break;
				case LIST:
					listPlants();
					break;
				case RESET:
					game.reset();
					break;
				case HELP:
					showHelp();
					break;
				case EXIT:
					game.quit();
					break;
				case NONE:
					game.update();
					view.showGame();
					break;
				};
				game.update();
			}
			
		}	
		view.showEndMessage();
		
	}

}
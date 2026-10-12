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
				if(t.equals(p.letter) ||t.equals(p.name)) return p;
			}
			return null;
		}
		
		public static Prompts fromString(String[] text) {
			if(text==null||text.length==0) return NONE;
		    if (text[0].isBlank()) return NONE;
			return fromString(text[0]);
		}
		
	};
	
	private void listPlants() {
	    // Sunflower
	    view.showMessage(String.format(Messages.SUNFLOWER_DESCRIPTION,
	            Sunflower.COST,
	            Sunflower.DAMAGE,
	            Sunflower.ENDURANCE));

	    // Peashooter
	    view.showMessage(String.format(Messages.PEASHOOTER_DESCRIPTION,	            
	            Peashooter.COST,
	            Peashooter.DAMAGE,
	            Peashooter.ENDURANCE));
	    view.showMessage("");
	}
	private void showHelp() {
		for(String line : Messages.HELP_LINES) {
			view.showMessage(line);
		}
	}
	
	private static boolean isInteger(String s) {
	    return s != null && s.matches("-?\\d+");
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
		while(!game.hasGameFinished()&&!game.playerQuits()) {
			String[] prompt = view.getPrompt();
			Prompts prompttype = Prompts.fromString(prompt);
			if(prompttype ==null) {
				view.showError(Messages.UNKNOWN_COMMAND);
			}else {
				switch (prompttype) {
				case ADD:
					if(prompt.length == 4) {
						if (!isInteger(prompt[2]) || !isInteger(prompt[3])) {
					        view.showError(Messages.INVALID_POSITION);
					        break;
					    }
						Position pos = new Position(Integer.parseInt(prompt[3]), Integer.parseInt(prompt[2]));
						String error = game.addGameObject(prompt[1], pos);
						if(error!=null) {
							view.showError(error);
							break;
						}
						game.update();
						view.showGame();
					}else {
						view.showError(Messages.COMMAND_PARAMETERS_MISSING);
					}

					break;
				case LIST:
					listPlants();
					break;
				case RESET:
					game.reset();
					view.showGame();
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
			}
			
		}	
		view.showEndMessage();
		
	}

}
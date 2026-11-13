package pvz.logic.actions;

import pvz.logic.GameWorld;

/**
 * Interface for all actions, special classes used to run functions in the game in a contained manner.
 * 
 * @author Rodrigo Ferrer López
 */

public interface GameAction {
	
	/**
	 * Executes an action in a given game.
	 * 
	 * @param game Game to execute action in.
	 */
	
	void execute(GameWorld game);
}
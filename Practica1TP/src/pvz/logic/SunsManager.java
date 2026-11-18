package pvz.logic;

import pvz.logic.gameobjects.Sun;
import utils.Position;

/**
 * Delegates on {@link Game} to add suns to the board.
 *
 * @author Rodrigo Ferrer López
 */

public class SunsManager {
	private GameWorld game;

	public SunsManager(GameWorld game) {
		this.game = game;
	}
	
	/**
	 * Adds a sun to a random position on the board.
	 */

	public void addSun() {
        Position pos = game.randomPosition();
        Sun sun = new Sun(pos, game);
        game.addGameObject(sun);
	}
}

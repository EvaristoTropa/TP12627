package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import pvz.view.Messages;
import utils.Position;

/**
 * Non-corporeal object that gives suns when caught and disappears after 10 cycles.
 * 
 * @author Rodrigo Ferrer López
 */

public class Sun extends GameObject {
	private int lifetime = 11;
	private static final int VALUE = 10;
	
	public Sun(Position position, GameWorld game) {
		super(position, game);
	}
	
	public String getIcon() {
		return Messages.GAME_OBJECT_ICON.formatted(Messages.SUN_SYMBOL, lifetime);
	}
	
	public String getDescription() {
		return Messages.SUN_DESCRIPTION;
	}
	
	public boolean isAlive() {
		return lifetime > 0;
	}
	
	public boolean receivePlantAttack(int damage) {
		return false;
	}
	
	public boolean receiveZombieAttack(int damage) {
		return false;
	}
	
	public boolean catchObject(Position position) {
		if (position.equals(this.position)) {
			lifetime = 0;
			game.generateCoins(VALUE);
			return true;
		}
		else
			return false;
	}
	
	/**
	 * Checks if a sun can be spawned at a given position.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if the position is inside the board.
	 */
	
	public boolean canBeSpawnedAt(Position position) {
		return game.isInsideBoard(position);
	}
	
	public void update() {
		if (isAlive())
			lifetime--;
	}
	
	public void onEnter() {};
	public void onExit() {};
}
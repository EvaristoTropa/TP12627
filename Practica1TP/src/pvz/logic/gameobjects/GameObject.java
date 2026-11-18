package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import utils.Position;

/**
 * Abstract class that generalizes all objects in the game.
 * 
 * @author Rodrigo Ferrer López
 */

public abstract class GameObject implements GameItem {
	protected Position position;
	protected GameWorld game;
	
	GameObject(Position position, GameWorld game) {
		this.position = position;
		this.game = game;
	}
	
	/**
	 * Checks if an object is in a given position.
	 * 
	 * @param other Position to check
	 * 
	 * @return <code>true</code> if the object's position matches the given one.
	 */
	
	public boolean isInPosition(Position other) {
		return position.equals(other);
	}
	
	/**
	 * Checks if an object is in a given column.
	 * 
	 * @param column Column to check
	 * 
	 * @return <code>true</code> if the object's position's column matches the given one.
	 */
	
	public boolean isInColumn(int column) {
		return position.column() == column;
	}
	
	/**
	 * Fetches an object's current position.
	 * 
	 * @return a new instance of its position.
	 */
	
	public Position getPosition() {
		return new Position(this.position);
	}
	
	/**
	 * Fetches an object's icon.
	 * 
	 * @return the object's icon.
	 */
	
	abstract public String getIcon();
	
	/**
	 * Fetches an object's description.
	 * 
	 * @return the object's description.
	 */
	
	abstract public String getDescription();
	
	/**
	 * Checks if an object is currently considered alive.
	 * 
	 * @return <code>true</code> if the conditions are met.
	 */
	
	abstract public boolean isAlive();
	
	/**
	 * Tries to catch all catchable objects in a position.
	 * 
	 * @return <code>true</code> if the catch was successful.
	 */
	
	abstract public boolean catchObject(Position position);
	
	/**
	 * Executes the actions the object does every cycle.
	 */
	
	abstract public void update();
	
	/**
	 * Executes the actions the object does upon being added.
	 */
	
	abstract public void onEnter();
	
	/**
	 * Executes the actions the object does upon dying.
	 */
	
	abstract public void onExit();
}
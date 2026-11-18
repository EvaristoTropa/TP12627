package pvz.logic;

import pvz.logic.actions.GameAction;
import pvz.logic.gameobjects.GameObject;
import utils.Position;

/**
 * Interface of {@link Game} that declares the methods that give objects information about the game.
 * Used by game objects to perform their actions.
 * 
 * @author Rodrigo Ferrer López
 */

public interface GameWorld {
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	
	String positionToString(Position position);
	void generateCoins(int amount);
	void generateSun();
	void addScore(int killscore);
	boolean addGameObject(GameObject object);
	boolean plantAttack(Position position, int damage);
	boolean zombieAttack(Position position, int damage);
	boolean isEmpty(Position position);
	boolean isInsideBoard(Position position);
	boolean isInBoardLimit(Position position);
	void pushAction(GameAction action);
	Position randomPosition();
	void zombieKilled();
}
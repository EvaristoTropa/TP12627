package pvz.logic;

import pvz.control.Level;
import pvz.logic.gameobjects.GameObject;
import utils.Position;

/**
 * Interface of {@link Game} that declares the methods that modify the game.
 * Used by commands to perform their actions.
 * 
 * @author Rodrigo Ferrer López
 */

public interface GameModel {
	void reset();
	void reset(Level level, long seed);
	void update();
	void quit();
	boolean addGameObject(GameObject object);
	boolean addGameObject(String plantName, Position position);
	boolean addGameObjectForFree(String plantName, Position position);
	boolean addZombie(String zombieName, Position position);
	boolean catchObject(Position position);
	boolean catchUsed();
	boolean isEmpty(Position position);
	boolean isInsideBoard(Position position);
	boolean isInBoardLimit(Position position);
	boolean isValidPosition(Position position);
	int getCoins();
}
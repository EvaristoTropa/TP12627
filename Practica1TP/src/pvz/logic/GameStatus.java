package pvz.logic;

import utils.Position;

/**
 * Interface of {@link Game} that declares the methods that inform of the game's current status.
 * Used by {@link GamePrinter} to draw the interface.
 * 
 * @author Rodrigo Ferrer López
 */

public interface GameStatus {
	String positionToString(Position position);
	int getCycles();
	int getCoins();
	int getRemainingZombies();
	int getGeneratedCoins();
	int getCaughtCoins();
	boolean hasGameFinished();
	boolean playerWins();
	boolean doZombiesReachedTheHouse();
	boolean playerQuits();
}
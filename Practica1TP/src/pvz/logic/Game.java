package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.utils.Position;
import pvz.logic.ZombiesManager;
import pvz.logic.gameobjects.PeashooterList;
import pvz.logic.gameobjects.SunflowerList;
import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.Sunflower;

/**
 * The singular most important component of the game.
 * 
 * <p>Handles the main logic framework, acting as a hub for other components.
 * 
 * @author Rodrigo Ferrer López
 */

public class Game {
	private long seed;
	private Level level;
	private Random rand;
	private ZombiesManager zombieManager;
	private PeashooterList peashooterList;
	private SunflowerList sunflowerList;
	private int cycles;
	private int coins;
	private boolean quit;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	public static final int INITIAL_COINS = 50;
	
	public Game (long seed, Level level) {
		this.seed = seed;
		this.level = level;
		this.rand = new Random(seed);
		this.zombieManager = new ZombiesManager(this, level, rand);
		this.peashooterList = new PeashooterList();
		this.sunflowerList = new SunflowerList();
		this.cycles = 0;
		this.coins = INITIAL_COINS;
		this.quit = false;
	}
	
	/**
     * Fetches the icon in a given position.
     * 
     * @param position Position to check
     * 
     * @return a string currently representing that position on the board.
     */
	
	public String positionToString(Position position) {
		if (!zombieManager.isEmpty(position))
			return zombieManager.iconInPosition(position);
		else if (!peashooterList.isEmpty(position))
			return peashooterList.iconInPosition(position);
		else if (!sunflowerList.isEmpty(position))
			return sunflowerList.iconInPosition(position);
		else
			return "";
	}
	
	/**
	 * Checks if an object name is valid to be added to the board by the player.
	 * 
	 * @param objectName Name to check
	 * 
	 * @return <code>true</code> if the object's name is in the whitelist.
	 */
	
	public boolean checkGameObject(String objectName) {
		return objectName.equalsIgnoreCase(Peashooter.shortName())
		|| objectName.equalsIgnoreCase(Peashooter.longName())
		|| objectName.equalsIgnoreCase(Sunflower.shortName())
		|| objectName.equalsIgnoreCase(Sunflower.longName());
	}
	
	/**
	 * Fetches the game's cycle count.
	 * 
	 * @return the number of cycles that have currently passed.
	 */
	
	public int getCycles() {
		return cycles;
	}
	
	/**
	 * Fetches the game's coin count.
	 * 
	 * @return the number of sun coins the player currently has.
	 */
	
	public int getCoins() {
		return coins;
	}
	
	/**
	 * Fetches how many zombies remain in the game.
	 * 
	 * @return the number of zombies to be spawned.
	 */
	
	public int getRemainingZombies() {
		return zombieManager.getRemainingZombies();
	}
	
	/**
	 * Checks if the game is considered finished.
	 * 
	 * @return <code>true</code> if the player wins, loses or quits.
	 */
	
	public boolean hasGameFinished() {
		return playerWins() || zombieManager.doZombiesReachedTheHouse() || playerQuits();
	}
	
	/**
	 * Checks if the game is considered won.
	 * 
	 * @return <code>true</code> if the player manages to kill every zombie.
	 */
	
	public boolean playerWins() {
		return zombieManager.allZombiesWereKilled();
	}
	
	/**
	 * Checks if the game is considered forfeit.
	 * 
	 * @return <code>true</code> if the game's quit flag is on.
	 */
	
	public boolean playerQuits() {
		return quit;
	}
	
	/**
	 * Activates a flag which signals that the game has been quit.
	 */
	
	public void quit() {
		quit = true;
	}
	
	/**
	 * Executes all actions that happen every cycle.
	 */
	
	public void update() {
		zombieManager.addZombie();
		sunflowerList.update();
		peashooterList.update();
		zombieManager.update();
		zombieManager.removeDead();
		peashooterList.removeDead();
		sunflowerList.removeDead();
		cycles++;
	}
	
	/**
	 * Rewinds the game to its initial state.
	 */
	
	public void reset() {
		rand = new Random(seed);
		zombieManager = new ZombiesManager(this, level, rand);
		peashooterList = new PeashooterList();
		sunflowerList = new SunflowerList();
		cycles = 0;
		coins = INITIAL_COINS;
	}
	
	/**
	 * If possible, adds a given object to the board at a given position and deducts coins accordingly.
	 * 
	 * @param plantType Plant to spawn, position Position of the new plant
	 */
	
	public void addGameObject(String plantType,  Position position) {
		if (!isEmpty(position) && isInsideBoard(position)) {
			if (plantType.equalsIgnoreCase(Peashooter.shortName())
					|| plantType.equalsIgnoreCase(Peashooter.longName())
					&& coins >= Peashooter.COST) {
						peashooterList.add(new Peashooter(position, this));
						coins -= Peashooter.COST;
				}
			else if (plantType.equalsIgnoreCase(Sunflower.shortName())
					|| plantType.equalsIgnoreCase(Sunflower.longName())
					&& coins >= Sunflower.COST) {
						sunflowerList.add(new Sunflower(position, this));
						coins -= Sunflower.COST;
					}
		}
	}
	
	/**
	 * Adds a given amount of coins to the counter.
	 * 
	 * @param amount Coins earned
	 */
	
	public void generateCoins(int amount) {
		coins += amount;
	}
	
	/**
	 * Looks for a zombie in a given position and damages it by a given amount if found.
	 * 
	 * @param p Position to check, damage Damage to deal
	 */
	
	public void attackZombie(Position p, int damage) {
		zombieManager.damageZombie(p, damage);
	}
	
	/**
	 * Looks for a plant in a given position and damages it by a given amount if found.
	 * 
	 * @param p Position to check, damage Damage to deal
	 */
	
	public void attackPlant(Position p, int damage) {
		peashooterList.receiveDamage(p, damage);
		sunflowerList.receiveDamage(p, damage);
	}
	
	/**
	 * Checks if a given position contains no objects in it.
	 * 
	 * @param p Position to check
	 * 
	 * @return <code>true</code> if no objects were found there.
	 */
	
	public boolean isEmpty(Position p) {
		return zombieManager.isEmpty(p) && peashooterList.isEmpty(p) && sunflowerList.isEmpty(p);
	}
	
	/**
	 * Generates the position where a new zombie will spawn.
	 * 
	 * @param row Spawn row
	 * 
	 * @return the zombie's spawn position.
	 */
	
	public static Position newZombiePosition(int row) {
		return new Position(row, NUM_COLS);
	}
	
	/**
	 * Checks if a position is considered inside the board by the game.
	 * 
	 * @param p Position to check
	 * 
	 * @return <code>true</code> if both the position's row and column are within the bounds set by the game.
	 */
	
	public boolean isInsideBoard(Position p) {
		return p.row() >= 0 && p.row() < NUM_ROWS && p.column() >= 0 && p.column() < NUM_COLS;
	}
}
package pvz.logic;

import java.util.List;
import java.util.Random;

import pvz.control.Level;
import pvz.control.PlantFactory;
import pvz.control.ZombieFactory;
import pvz.logic.gameobjects.GameObject;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.Zombie;
import utils.Position;

/**
 * The singular most important component of the game.
 * 
 * <p>Handles the main logic framework, acting as a hub for other components.
 * 
 * @author Rodrigo Ferrer López
 */

public class Game implements GameStatus, GameWorld, GameModel {
	public static final int INITIAL_COINS = 50;
	
	private long seed;
	private Level level;
	private Random rand;
	private GameObjectContainer gameObjects;
	private ZombieManager zombieManager;
	private int cycles;
	private int coins;
	private boolean playerQuits;
	
	public Game (long seed, Level level) {
		reset(level, seed);
	}
	
	/**
     * Fetches the icon in a given position.
     * 
     * @param position Position to check
     * 
     * @return a string currently representing that position on the board.
     */
	
	public String positionToString(Position position) {
		if (!gameObjects.isEmpty(position))
			return gameObjects.iconInPosition(position);
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
	
	public boolean checkPlant(String plantName) {
		int i = 0;
		List<Plant> plants = PlantFactory.getAvailablePlants();
		while (i < plants.size() && !plants.get(i).matchPlant(plantName))
			i++;
		return i < plants.size();
	}
	
	public boolean checkZombie(String zombieName) {
		int i = 0;
		List<Zombie> zombies = ZombieFactory.getAvailableZombies();
		while (i < zombies.size() && !zombies.get(i).matchZombie(zombieName))
			i++;
		return i < zombies.size();
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
		return playerQuits;
	}
	
	/**
	 * Activates a flag which signals that the game has been quit.
	 */
	
	public void quit() {
		playerQuits = true;
	}
	
	/**
	 * Executes all actions that happen every cycle.
	 */
	
	public void update() {
		cycles++;
		zombieManager.addZombie();
		gameObjects.update();
		gameObjects.removeDead();
	}
	
	/**
	 * Rewinds the game to its initial state.
	 */
	
	public void reset(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		this.rand = new Random(seed);
		this.zombieManager = new ZombieManager(this, level, rand);
		this.gameObjects = new GameObjectContainer();
		this.cycles = 0;
		this.coins = INITIAL_COINS;
		this.playerQuits = false;
	}
	
	public void reset() {
		reset(this.level, this.seed);
	}
	
	public void addGameObject(GameObject object) {
		gameObjects.add(object);
	}
	
	/**
	 * If possible, adds a given object to the board at a given position and deducts coins accordingly.
	 * 
	 * @param plantType Plant to spawn, position Position of the new plant
	 */
	
	public void addPlant(String plantName, Position position) {
		if (isEmpty(position) && isInsideBoard(position)) {
		Plant plant = PlantFactory.spawnPlant(plantName, this, position);
		if (plant != null && coins >= plant.getCost()) {
			addGameObject(plant);
			coins -= plant.getCost();
			}
		}
	}
	
	public void addZombie(String zombieName, Position position) {
		if (isEmpty(position) && isInsideExpandedBoard(position)) {
		Zombie zombie = ZombieFactory.spawnZombie(zombieName, this, position);
		addGameObject(zombie);
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
		gameObjects.attackZombie(p, damage);
	}
	
	/**
	 * Looks for a plant in a given position and damages it by a given amount if found.
	 * 
	 * @param p Position to check, damage Damage to deal
	 */
	
	public void attackPlant(Position p, int damage) {
		gameObjects.attackPlant(p, damage);
	}
	
	/**
	 * Checks if a given position contains no objects in it.
	 * 
	 * @param p Position to check
	 * 
	 * @return <code>true</code> if no objects were found there.
	 */
	
	public boolean isEmpty(Position p) {
		return gameObjects.isEmpty(p);
	}
	
	/**
	 * Checks if a given position contains a zombie in it.
	 * 
	 * @param p Position to check
	 * 
	 * @return <code>true</code> if a zombie was found there.
	 */
	
	public boolean containsZombie(Position p) {
		return gameObjects.containsZombie(p);
	}
	
	/**
	 * Generates the position where a new zombie will spawn.
	 * 
	 * @param row Spawn row
	 * 
	 * @return the zombie's spawn position.
	 */
	
	public static Position newZombiePosition(int row) {
		if (row < 0 || row >= NUM_ROWS)
			row = 0;
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
		return p.row() >= 0 && p.row() < NUM_ROWS
		&& p.column() >= 0 && p.column() < NUM_COLS;
	}
	
	public boolean isInsideExpandedBoard(Position p) {
		return p.row() >= 0 && p.row() < NUM_ROWS
		&& p.column() >= -1 && p.column() <= NUM_COLS;
	}
	
	public void zombieDies() {
		zombieManager.zombieDies();
	}
	
	public boolean anyInColumn(int column) {
		return gameObjects.anyInColumn(column);
	}
}
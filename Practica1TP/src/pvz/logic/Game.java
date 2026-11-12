package pvz.logic;

import java.util.List;
import java.util.Random;
import java.util.ArrayDeque;
import java.util.Deque;

import pvz.control.Level;
import pvz.logic.actions.GameAction;
import pvz.logic.gameobjects.GameObject;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.factories.PlantFactory;
import pvz.logic.gameobjects.factories.ZombieFactory;
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
	private static final int SUN_PERIOD = 5;
	
	private long seed;
	private Level level;
	private Random rand;
	private GameObjectContainer container;
	private Deque<GameAction> actions;
	private ZombieManager zombieManager;
	private SunsManager sunManager;
	private int cycles;
	private int coins;
	private int generatedSuns;
	private int caughtSuns;
	private int sun_counter;
	private boolean playerQuits;
	private boolean catchUsed;
	
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
		return container.positionToString(position);
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
	
	public int getGeneratedCoins() {
		return generatedSuns;
	}
	
	public int getCaughtCoins() {
		return caughtSuns;
	}
	
	/**
	 * Checks if the game is considered finished.
	 * 
	 * @return <code>true</code> if the player wins, loses or quits.
	 */
	
	public boolean hasGameFinished() {
		return playerWins() || doZombiesReachedTheHouse() || playerQuits();
	}
	
	/**
	 * Checks if the game is considered won.
	 * 
	 * @return <code>true</code> if the player manages to kill every zombie.
	 */
	
	public boolean playerWins() {
		return zombieManager.allZombiesWereKilled();
	}
	
	public boolean doZombiesReachedTheHouse() {
		return anyInColumn(-1);
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
		executePendingActions();
		zombieManager.addZombie();
		if (sun_counter >= SUN_PERIOD) {
			sunManager.addSun();
			sun_counter = 0;
			generatedSuns++;
		}
		container.update();
		boolean deadRemoved = true;
		while (deadRemoved || arePendingActionsLeft()) {
		deadRemoved = container.removeDead();
		executePendingActions();
		}
		catchUsed = false;
		cycles++;
		sun_counter++;
	}
	
	public void pushAction(GameAction gameAction) {
		actions.addLast(gameAction);
	}
	
	private void executePendingActions() {
		while (!actions.isEmpty()) {
			GameAction action = actions.removeFirst();
			action.execute(this);
		}
	}
	
	private boolean arePendingActionsLeft() {
		return !actions.isEmpty();
	}
	
	/**
	 * Rewinds the game to its initial state.
	 */
	
	public void reset(Level level, long seed) {
		this.level = level;
		this.seed = seed;
		this.rand = new Random(seed);
		this.zombieManager = new ZombieManager(this, level, rand);
		this.sunManager = new SunsManager(this);
		this.container = new GameObjectContainer();
		this.actions = new ArrayDeque<>();
		this.cycles = 0;
		this.coins = INITIAL_COINS;
		this.generatedSuns = 0;
		this.caughtSuns = 0;
		this.sun_counter = 0;
		this.playerQuits = false;
		this.catchUsed = false;
	}
	
	public void reset() {
		reset(this.level, this.seed);
	}
	
	public boolean addGameObject(GameObject object) {
		return container.add(object);
	}
	
	public void generateSun() {
		sunManager.addSun();
		generatedSuns++;
	}
	
	public boolean catchObject(Position position) {
		if (!catchUsed) {
		int sunsCaught = container.catchObject(position);
		if (sunsCaught > 0) {
		caughtSuns += sunsCaught;
		catchUsed = true;
		return true;
		}
		else
			return false;
		}
		else
			return false;
	}
	
	public boolean catchUsed() {
		return catchUsed;
	}
	
	/**
	 * If possible, adds a given object to the board at a given position and deducts coins accordingly.
	 * 
	 * @param plantType Plant to spawn, position Position of the new plant
	 */
	
	public boolean addGameObject(String plantName, Position position) {
		if (isEmpty(position) && isInsideBoard(position)) {
		Plant plant = PlantFactory.spawnPlant(plantName, this, position);
		if (plant != null && coins >= plant.getCost()) {
			coins -= plant.getCost();
			return addGameObject(plant);
			}
		else 
			return addZombie(plantName, position);
		}
		else if (isInBoardLimit(position))
			return addZombie(plantName, position);
		else
			return false;
	}
	
	public boolean addGameObjectForFree(String plantName, Position position) {
		if (isEmpty(position) && isInsideBoard(position)) {
		Plant plant = PlantFactory.spawnPlant(plantName, this, position);
		if (plant != null)
			return addGameObject(plant);
		else
			return false;
		}
		else
			return false;
	}
	
	public boolean addZombie(String zombieName, Position position) {
		if (isEmpty(position) && isInBoardLimit(position)) {
		Zombie zombie = ZombieFactory.spawnZombie(zombieName, this, position);
		if (zombie != null)
		return addGameObject(zombie);
		else
			return false;
		}
		else
			return false;
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
	
	public boolean plantAttack(Position p, int damage) {
		return container.attackZombie(p, damage);
	}
	
	/**
	 * Looks for a plant in a given position and damages it by a given amount if found.
	 * 
	 * @param p Position to check, damage Damage to deal
	 */
	
	public boolean zombieAttack(Position p, int damage) {
		return container.attackPlant(p, damage);
	}
	
	/**
	 * Checks if a given position contains no objects in it.
	 * 
	 * @param p Position to check
	 * 
	 * @return <code>true</code> if no objects were found there.
	 */
	
	public boolean isEmpty(Position p) {
		return container.isPositionEmpty(p);
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
	
	public Position randomPosition() {
		return new Position(rand.nextInt(NUM_ROWS), rand.nextInt(NUM_COLS));
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
	
	public boolean isInBoardLimit(Position p) {
		return p.row() >= 0 && p.row() < NUM_ROWS
		&& p.column() >= -1 && p.column() <= NUM_COLS;
	}
	
	public boolean isValidPosition(Position position) {
		return isEmpty(position) && isInsideBoard(position);
	}
	
	public void zombieKilled() {
		zombieManager.zombieKilled();
	}
	
	public boolean anyInColumn(int column) {
		return container.anyInColumn(column);
	}
}
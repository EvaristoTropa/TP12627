package pvz.logic;

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
     * Fetches the representation of the objects in a given position.
     * 
     * @param position Position to check
     * 
     * @return a string currently representing that position on the board.
     */
	
	public String positionToString(Position position) {
		return container.positionToString(position);
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
	 * Fetches how many suns have been currently generated.
	 * 
	 * @return the number of spawned suns.
	 */
	
	public int getGeneratedCoins() {
		return generatedSuns;
	}
	
	/**
	 * Fetches how many suns have been caught by the user.
	 * 
	 * @return the number of suns converted to suncoins.
	 */
	
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
	
	/**
	 * Checks if the game is considered lost.
	 * 
	 * @return <code>true</code> if an object has reached the left edge of the board (only zombies can naturally do this).
	 */
	
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
	 * Activates a flag which informs the game that the player has quit.
	 */
	
	public void quit() {
		playerQuits = true;
	}
	
	/**
	 * Manages all actions that happen every cycle: action executions, object generation, updates and removal, and parameter updates.
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
	
	/**
	 * Adds an action to the list of actions to be executed.
	 * 
	 * @param gameAction Action to add
	 */
	
	public void pushAction(GameAction gameAction) {
		actions.addLast(gameAction);
	}
	
	/**
	 * Executes all actions in the list from start to finish.
	 */
	
	private void executePendingActions() {
		while (!actions.isEmpty()) {
			GameAction action = actions.removeFirst();
			action.execute(this);
		}
	}
	
	/**
	 * Checks if there are any actions left to be executed.
	 * 
	 * @return <code>true</code> if the list of actions is not empty.
	 */
	
	private boolean arePendingActionsLeft() {
		return !actions.isEmpty();
	}
	
	/**
	 * Starts a new game with a specified level and seed.
	 * 
	 * @param level Level to play on, seed Seed to play on
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
	
	/**
	 * Starts a new game preserving the current level and seed.
	 */
	
	public void reset() {
		reset(this.level, this.seed);
	}
	
	/**
	 * Tries to add an object to the container.
	 * 
	 * @param object Object to add.
	 * 
	 * @return <code>true</code> if the add is successful.
	 */
	
	public boolean addGameObject(GameObject object) {
		return container.add(object);
	}
	
	/**
	 * Generates a sun in a random position.
	 */
	
	public void generateSun() {
		sunManager.addSun();
		generatedSuns++;
	}
	
	/**
	 * Catches all catchable objects (suns) in a given position.
	 * 
	 * @param position Position to catch in
	 * 
	 * @return <code>true</code> if any object was caught.
	 */
	
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
	
	/**
	 * Checks if the Catch command was used on the current cycle.
	 * 
	 * @return <code>true</code> if that is the case.
	 */
	
	public boolean catchUsed() {
		return catchUsed;
	}
	
	/**
	 * If possible, adds a given plant to the board at a given position using its name and deducts coins accordingly.
	 * If the name or position matches with a zombie instead, it redirects to the {@link #addZombie(String, Position)} function.
	 * 
	 * @param plantName Name of the plant to spawn, position Position of the new plant
	 * 
	 * @return <code>true</code> if the add is successful.
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
	
	/**
	 * Tries to add a plant in a position, but without spending suncoins.
	 * 
	 * @param plantName Name of the plant to spawn, position Position of the new plant
	 * 
	 * @return <code>true</code> if the add is successful.
	 */
	
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
	
	/**
	 * Tries to add a zombie in a position, with no cost whatsoever.
	 * 
	 * @param zombieName Name of the zombie to spawn, position Position of the new zombie
	 * 
	 * @return <code>true</code> if the add is successful.
	 */
	
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
	 * @param position Position to check, damage Damage to deal
	 * 
	 * @return <code>true</code> if the attack was successful.
	 */
	
	public boolean plantAttack(Position position, int damage) {
		return container.attackZombie(position, damage);
	}
	
	/**
	 * Looks for a plant in a given position and damages it by a given amount if found.
	 * 
	 * @param position Position to check, damage Damage to deal
	 * 
	 * @return <code>true</code> if the attack was successful.
	 */
	
	public boolean zombieAttack(Position position, int damage) {
		return container.attackPlant(position, damage);
	}
	
	/**
	 * Checks if a given position contains no objects in it.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if no objects were found there.
	 */
	
	public boolean isEmpty(Position position) {
		return container.isPositionEmpty(position);
	}
	
	/**
	 * Places a generated zombie's spawn position on the right edge of the board.
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
	 * Generates a random position on the board.
	 * 
	 * @return the random position.
	 */
	
	public Position randomPosition() {
		return new Position(rand.nextInt(NUM_ROWS), rand.nextInt(NUM_COLS));
	}
	
	/**
	 * Checks if a position is considered inside the board by the game.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if both the position's row and column are within the bounds set by the game.
	 */
	
	public boolean isInsideBoard(Position position) {
		return position.row() >= 0 && position.row() < NUM_ROWS
		&& position.column() >= 0 && position.column() < NUM_COLS;
	}
	
	/**
	 * Checks if a position is considered inside the board or in the side edges.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if both the position's row and column are within the board or its left or right edges.
	 */
	
	public boolean isInBoardLimit(Position position) {
		return position.row() >= 0 && position.row() < NUM_ROWS
		&& position.column() >= -1 && position.column() <= NUM_COLS;
	}
	
	/**
	 * Checks if a position is valid for an object to be added in.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if the position is both empty and inside the board.
	 */
	
	public boolean isValidPosition(Position position) {
		return isEmpty(position) && isInsideBoard(position);
	}
	
	/**
	 * Informs the zombie manager that a zombie has died.
	 */
	
	public void zombieKilled() {
		zombieManager.zombieKilled();
	}
	
	/**
	 * Checks if there are any objects in a given column.
	 * 
	 * @param column Column to check
	 * 
	 * @return <code>true</code> if that is the case.
	 */
	
	public boolean anyInColumn(int column) {
		return container.anyInColumn(column);
	}
}
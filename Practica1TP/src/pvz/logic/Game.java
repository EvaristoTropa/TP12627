package pvz.logic;

import pvz.control.Level;
import pvz.utils.Position;
import pvz.logic.ZombiesManager;
import pvz.logic.gameobjects.PeashooterList;
import pvz.logic.gameobjects.SunflowerList;

public class Game {
	private long seed;
	private Level level;
	private ZombiesManager zombieManager;
	private PeashooterList peashooterList;
	private SunflowerList sunflowerList;
	private int cycles;
	private int coins;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	public static final int INITIAL_COINS = 50;
	
	public Game (long seed, Level level) {
		this.seed = seed;
		this.level = level;
		this.cycles = 0;
		this.coins = INITIAL_COINS;
	}
	
	public String positionToString(Position position) {
		return position.toString();
	}
	
	public boolean checkGameObject(String objectName) {
		// TODO
		return true;
	}
	
	public int getCycles() {
		return cycles;
	}
	
	public int getCoins() {
		return coins;
	}
	
	public int getRemainingZombies() {
		return zombieManager.getRemainingZombies();
	}
	
	public boolean hasGameFinished() {
		return playerWins() || zombieManager.doZombiesReachedTheHouse() || playerQuits();
	}
	
	public boolean playerWins() {
		return zombieManager.allZombiesWereKilled();
	}
	
	public boolean playerQuits() {
		// TODO
		return true;
	}
	
	public void quit() {
		// TODO
	}
	
	public void update() {
		// TODO
	}
	
	public void reset() {
		// TODO
	}
	
	public void addGameObject(String plantType,  Position position) {
		// TODO
	}
	
	public void generateCoins(int amount) {
		coins += amount;
	}
	
	public void attackZombie(Position p, int damage) {
		zombieManager.damageZombie(p, damage);
	}
	
	public void attackPlant(Position p, int damage) {
		peashooterList.receiveDamage(p, damage);
		sunflowerList.receiveDamage(p, damage);
	}
	
	public boolean isEmpty(Position p) {
		return zombieManager.isEmpty(p) && peashooterList.isEmpty(p) && sunflowerList.isEmpty(p);
	}
	
	public Position newZombiePosition(int row) {
		return new Position(row, NUM_COLS);
	}
	
	public boolean isInsideBoard(Position p) {
		return p.row() >= 0 && p.row() < NUM_ROWS && p.column() >= 0 && p.column() < NUM_COLS;
	}
}
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
 * Handles the main logic framework, acting as a hub for other components.
 * 
 * @author Rodrigo Ferrer López
 */

public class Game {
	private long seed;
	private Level level;
	private ZombiesManager zombieManager;
	private PeashooterList peashooterList;
	private SunflowerList sunflowerList;
	private int cycles;
	private int coins;
	private Random rand;
	public static final int NUM_ROWS = 4;
	public static final int NUM_COLS = 8;
	public static final int INITIAL_COINS = 50;
	
	public Game (long seed, Level level) {
		this.seed = seed;
		this.level = level;
		this.zombieManager = new ZombiesManager(this, level, rand);
		this.peashooterList = new PeashooterList();
		this.sunflowerList = new SunflowerList();
		this.cycles = 0;
		this.coins = INITIAL_COINS;
	}
	
	public String positionToString(Position position) {
		return position.toString();
	}
	
	public boolean checkGameObject(String objectName) {
		return objectName == "peashooter" || objectName == "p"
		|| objectName == "PEASHOOTER" || objectName == "P"
		|| objectName == "sunflower" || objectName == "s"
		|| objectName == "SUNFLOWER" || objectName == "S";
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
		return cycles == -1;
	}
	
	public void quit() {
		cycles = -1;
	}
	
	public void update() {
		sunflowerList.update();
		peashooterList.update();
		zombieManager.removeDead();
		zombieManager.update();
		peashooterList.removeDead();
		sunflowerList.removeDead();
		cycles++;
	}
	
	public void reset() {
		zombieManager = new ZombiesManager(this, level, rand);
		peashooterList = new PeashooterList();
		sunflowerList = new SunflowerList();
		cycles = 0;
		coins = INITIAL_COINS;
	}
	
	public void addGameObject(String plantType,  Position position) {
		if (plantType == "peashooter" || plantType == "p"
		|| plantType == "PEASHOOTER" || plantType == "P"
		&& coins >= Peashooter.COST) {
			peashooterList.add(new Peashooter(position, this));
			coins -= Peashooter.COST;
		}
		else if (plantType == "sunflower" || plantType == "s"
		|| plantType == "SUNFLOWER" || plantType == "S"
		&& coins >= Sunflower.COST) {
			sunflowerList.add(new Sunflower(position, this));
			coins -= Sunflower.COST;
		}
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
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
	
	public String positionToString(javax.swing.text.Position position) {
		return position.toString();
	}
	
	public boolean checkGameObject(String objectName) {
		// TODO figure out what this one even means
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
	
	// TODO complete this class
}
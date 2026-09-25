package pvz.logic;

import java.util.Random;

import pvz.control.Level;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.ZombieList;

/**
 * Manages the full lifecycle of zombies for a game session.
 *
 * <p>Responsibilities: deciding each cycle whether to spawn a new zombie
 * (probabilistically, subject to the remaining quota from {@link Level}),
 * delegating per-cycle updates and dead-removal to the underlying
 * {@link ZombieList}, and answering win/loss queries
 *
 */
public class ZombiesManager {

	private Game game;

	private Level level;

	private Random rand;

	private int remainingZombies;

	private ZombieList zombies;

	public ZombiesManager(Game game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		this.rand = rand;
		this.remainingZombies = level.getNumberOfZombies();
		this.zombies = new ZombieList();
	}

	/**
	 * Checks if the game should add (if possible) a zombie to the game.
	 * 
	 * @return <code>true</code> if a zombie should be added to the game.
	 */
	private boolean shouldAddZombie() {
		return rand.nextDouble() < level.getZombieFrequency();
	}
	
	/**
	 * Return a random row within the board limits.
	 * 
	 * @return a random row.
	 */
	private int randomZombieRow() {
		return rand.nextInt(Game.NUM_ROWS);
	}
	
	public boolean addZombie() {
		int row = randomZombieRow();
		return addZombie(row);
	}

	public boolean addZombie(int row) {
        Position pos = new Position(row, Game.NUM_COLS);
		boolean canAdd = getRemainingZombies() > 0 && shouldAddZombie()
				&& isEmpty(pos);

		if(canAdd) {
            Zombie z = new Zombie(pos, game);
            zombies.add(z);
		}
		return canAdd;
	}

    
    public int getRemainingZombies() {
        return remainingZombies;
    }
    
    public boolean doZombiesReachedTheHouse() {
        return zombies.anyInColumn(-1);
    }
    
    public void damageZombie(Position p, int damage) {
        zombies.damage(p, damage);
    }
    
    public String iconInPosition(Position p) {
        return zombies.iconInPosition(p);
    }
    
    public boolean isEmpty(Position p) {
        return zombies.isEmpty(p);
    }
    
    public void update() {
        zombies.update();
    }
    
    public boolean allZombiesWereKilled() {
        return remainingZombies == 0;
    }
    
    public void removeDead() {
        zombies.removeDead();
    }
}

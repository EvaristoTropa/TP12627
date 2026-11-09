package pvz.logic;

import java.util.List;
import java.util.Random;

import pvz.control.Level;
import pvz.control.ZombieFactory;
import pvz.logic.gameobjects.Zombie;
import utils.Position;

/**
 * Manages the full lifecycle of zombies for a game session.
 *
 * <p>Responsibilities: deciding each cycle whether to spawn a new zombie
 * (probabilistically, subject to the remaining quota from {@link Level}),
 * delegating per-cycle updates and dead-removal to the underlying
 * {@link ZombieList}, and answering win/loss queries
 *
 * @author Rodrigo Ferrer López (filled code);
 */

public class ZombieManager {
	private Game game;
	private Level level;
	private Random rand;
	private int zombiesToSpawn, zombiesToKill;

	public ZombieManager(Game game, Level level, Random rand) {
		this.game = game;
		this.level = level;
		this.rand = rand;
		this.zombiesToSpawn = level.getNumberOfZombies();
		this.zombiesToKill = this.zombiesToSpawn;
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
	 * Generates a random row within the board limits.
	 * 
	 * @return a random row.
	 */
	
	private int randomZombieRow() {
		return rand.nextInt(Game.NUM_ROWS);
	}
	
	/**
	 * Tries to add a zombie with a randomly generated row.
	 * 
	 * @return <code>true</code> if the zombie was added.
	 */
	
	public boolean addZombie() {
		int row = randomZombieRow();
		return addZombie(row);
	}
	
	/**
	 * Tries to add a zombie with a given row.
	 * 
	 * @param row Row to add the zombie to
	 * 
	 * @return <code>true</code> if the zombie was added.
	 */

	public boolean addZombie(int row) {
        Position pos = Game.newZombiePosition(row);
		boolean canAdd = getRemainingZombies() > 0 && shouldAddZombie() && game.isEmpty(pos);

		if(canAdd) {
			List<Zombie> zombies = ZombieFactory.getAvailableZombies();
			int i = rand.nextInt(zombies.size());
            Zombie zombie = zombies.get(i).create(game, pos);
            game.addGameObject(zombie);
            zombiesToSpawn--;
		}
		return canAdd;
	}
	
	/**
	 * Fetches the amount of remaining zombies in the game.
	 * 
	 * @return the number of zombies that are yet to be spawned.
	 */
    
    public int getRemainingZombies() {
        return zombiesToSpawn;
    }
    
    /**
	 * Checks if any zombies got past the lawn (the board).
	 * 
	 * @return <code>true</code> if a zombie has crossed the left boundary of the board.
	 */
    
    public boolean doZombiesReachedTheHouse() {
        return game.anyInColumn(-1);
    }
    
    public void zombieDies() {
    	zombiesToKill--;
    }
    
    /**
	 * Checks if all zombies have been spawned and killed.
	 * 
	 * @return <code>true</code> if no zombies remain in the game.
	 */
    
    public boolean allZombiesWereKilled() {
        return zombiesToSpawn == 0 && zombiesToKill == 0;
    }
}

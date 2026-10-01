package pvz.logic;

import java.util.Random;

import pvz.logic.Game;
import pvz.control.Level;
import pvz.logic.gameobjects.Zombie;
import pvz.logic.gameobjects.ZombieList;
import pvz.utils.Position;

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
        Position pos = game.newZombiePosition(row);
		boolean canAdd = getRemainingZombies() > 0 && shouldAddZombie()
				&& isEmpty(pos);

		if(canAdd) {
            Zombie z = new Zombie(pos, game);
            zombies.add(z);
		}
		return canAdd;
	}
	
	/**
	 * Fetches the amount of remaining zombies in the game.
	 * 
	 * @return the number of zombies that are yet to be spawned and killed.
	 */
    
    public int getRemainingZombies() {
        return remainingZombies;
    }
    
    /**
	 * Checks if the game's loss condition has been fulfilled.
	 * 
	 * @return <code>true</code> if a zombie has crossed the left border of the board.
	 */
    
    public boolean doZombiesReachedTheHouse() {
        return zombies.anyInColumn(-1);
    }
    
    /**
	 * Damages a zombie in a given position by a given amount.
	 * 
	 * @param p Position to check, damage Damage to deal
	 */
    
    public void damageZombie(Position p, int damage) {
        zombies.damage(p, damage);
    }
    
    /**
	 * Fetches the icon of a zombie in a given position.
	 * 
	 * @param p Position to check
	 * 
	 * @return the zombie's icon if found, an empty string otherwise.
	 */
    
    public String iconInPosition(Position p) {
        return zombies.iconInPosition(p);
    }
    
    /**
	 * Checks if there are no zombies in a given position.
	 * 
	 * @param p Position to check
	 * 
	 * @return <code>true</code> if no zombie has been found in that position.
	 */
    
    public boolean isEmpty(Position p) {
        return zombies.isEmpty(p);
    }
    
    /**
	 * Updates all zombies in the list.
	 */
    
    public void update() {
        zombies.update();
    }
    
    /**
	 * Checks if the game's win condition has been fulfilled.
	 * 
	 * @return <code>true</code> if all zombies have been spawned and killed.
	 */
    
    public boolean allZombiesWereKilled() {
        return remainingZombies == 0;
    }
    
    /**
	 * Removes all dead zombies in the list.
	 */
    
    public void removeDead() {
        zombies.removeDead();
    }
}

package pvz.logic.gameobjects.factories;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.CommonZombie;
import pvz.logic.gameobjects.BucketHeadZombie;
import pvz.logic.gameobjects.ExplosiveZombie;
import pvz.logic.gameobjects.SportyZombie;
import pvz.logic.gameobjects.Zombie;
import utils.Position;
import pvz.logic.GameWorld;

/**
 * Implementation of the Factory pattern for zombies.
 * 
 * <p>Allows the game to create zombies in a streamlined way.
 * 
 * @author Rodrigo Ferrer López
 */

public class ZombieFactory {
	private static final List<Zombie> AVAILABLE_ZOMBIES = Arrays.asList(
		new CommonZombie(null, null),
		new BucketHeadZombie(null, null),
		new SportyZombie(null, null),
		new ExplosiveZombie(null, null)
	);
	
	/**
	 * Creates a zombie with a given name and parameters.
	 * 
	 * @param zombieName Name of the zombie to create, game Game where the zombie will reside, position Position of the zombie
	 * 
	 * @return the created zombie if the creation was successful, null otherwise.
	 */
	
	public static Zombie spawnZombie(String zombieName, GameWorld game, Position position) {
		int i = 0;
		while (i < AVAILABLE_ZOMBIES.size() && !AVAILABLE_ZOMBIES.get(i).matchZombie(zombieName))
			i++;
		if (i < AVAILABLE_ZOMBIES.size() && game.isEmpty(position) && game.isInBoardLimit(position))
			return AVAILABLE_ZOMBIES.get(i).copy(position, game);
		else
		return null;
	}
	
	/**
	 * Fetches the types of zombies available in the game.
	 * 
	 * @return an unmodifiable version of the list of zombies.
	 */
	
	public static List<Zombie> getAvailableZombies() {
		return Collections.unmodifiableList(AVAILABLE_ZOMBIES);
	}
}
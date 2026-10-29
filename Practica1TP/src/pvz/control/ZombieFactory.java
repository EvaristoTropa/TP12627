package pvz.control;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.Zombie;
import pvz.logic.GameWorld;
import pvz.utils.Position;

/**
 * Implementation of the Factory pattern for zombies.
 * 
 * Allows the game to create zombies in a streamlined way.
 * 
 * @author Rodrigo Ferrer López
 */

public class ZombieFactory {
	private static final List<Zombie> AVAILABLE_ZOMBIES = Arrays.asList(
		new BasicZombie()
	);
	
	public static Zombie spawnZombie(String zombieName, GameWorld game, Position position) {
		// ...
	}
	
	public static List<Zombie> getAvailableZombies() {
		return Collections.unmodifiableList(AVAILABLE_ZOMBIES);
	}
}
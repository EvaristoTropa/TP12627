package pvz.control;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.BasicZombie;
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
		new BasicZombie(null, null)
	);
	
	public static Zombie spawnZombie(String zombieName, GameWorld game, Position position) {
		int i = 0;
		while (i < AVAILABLE_ZOMBIES.size() && !AVAILABLE_ZOMBIES.get(i).matchZombie(zombieName))
			i++;
		if (i < AVAILABLE_ZOMBIES.size() && game.isEmpty(position) && game.isInsideBoard(position))
			return AVAILABLE_ZOMBIES.get(i).create(game, position);
		else
		return null;
	}
	
	public static List<Zombie> getAvailableZombies() {
		return Collections.unmodifiableList(AVAILABLE_ZOMBIES);
	}
}
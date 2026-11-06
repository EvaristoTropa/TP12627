package pvz.control;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.Sunflower;
import pvz.logic.GameWorld;
import pvz.utils.Position;

/**
 * Implementation of the Factory pattern for plants.
 * 
 * <p>Allows the game to create plants in a streamlined way.
 * 
 * @author Rodrigo Ferrer López
 */

public class PlantFactory {
	private static final List<Plant> AVAILABLE_PLANTS = Arrays.asList(
		new Peashooter(null, null),
		new Sunflower(null, null)
	);
	
	public static Plant spawnPlant(String plantName, GameWorld game, Position position) {
		int i = 0;
		while (i < AVAILABLE_PLANTS.size() && !AVAILABLE_PLANTS.get(i).matchPlant(plantName))
			i++;
		if (i < AVAILABLE_PLANTS.size() && game.isEmpty(position) && game.isInsideBoard(position))
			return AVAILABLE_PLANTS.get(i).create(game, position);
		else
		return null;
	}
	
	public static List<Plant> getAvailablePlants() {
		return Collections.unmodifiableList(AVAILABLE_PLANTS);
	}
}
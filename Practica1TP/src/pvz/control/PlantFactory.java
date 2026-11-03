package pvz.control;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.Plant;
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
		new Peashooter(),
		new Sunflower()
	);
	
	public static Plant spawnPlant(String plantName, GameWorld game, Position position) {
		Plant plant = plant.identify(plantName);
		if (plant != null && !game.isEmpty(position)) {
			plant.setPosition(position);
			return plant;
		}
		else
			return null;
	}
	
	public static List<Plant> getAvailablePlants() {
		return Collections.unmodifiableList(AVAILABLE_PLANTS);
	}
}
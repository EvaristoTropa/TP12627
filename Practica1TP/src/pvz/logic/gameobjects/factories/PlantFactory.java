package pvz.logic.gameobjects.factories;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.Plant;
import pvz.logic.gameobjects.CherryBomb;
import pvz.logic.gameobjects.Peashooter;
import pvz.logic.gameobjects.Sunflower;
import pvz.logic.gameobjects.WallNut;
import utils.Position;
import pvz.logic.GameWorld;

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
		new Sunflower(null, null),
		new WallNut(null, null),
		new CherryBomb(null, null)
	);
	
	public static Plant spawnPlant(String plantName, GameWorld game, Position position) {
		int i = 0;
		while (i < AVAILABLE_PLANTS.size() && !AVAILABLE_PLANTS.get(i).matchPlant(plantName))
			i++;
		if (i < AVAILABLE_PLANTS.size() && game.isEmpty(position) && game.isInsideBoard(position))
			return AVAILABLE_PLANTS.get(i).copy(position, game);
		else
			return null;
	}
	
	public static List<Plant> getAvailablePlants() {
		return Collections.unmodifiableList(AVAILABLE_PLANTS);
	}
}
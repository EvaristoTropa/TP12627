package pvz.control;

import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import pvz.logic.gameobjects.Plant;
import pvz.logic.GameWorld;
import pvz.utils.Position;

public class PlantFactory {
	private static final List<Plant> AVAILABLE_PLANTS = Arrays.asList(
		new Peashooter(),
		new Sunflower()
	);
	
	public static Plant spawnPlant(String plantName, GameWorld game, Position position) {
		// ...
	}
	
	public static List<Plant> getAvailablePlants() {
		return Collections.unmodifiableList(AVAILABLE_PLANTS);
	}
}
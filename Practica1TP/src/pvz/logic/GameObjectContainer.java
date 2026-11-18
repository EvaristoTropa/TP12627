package pvz.logic;

import java.util.ArrayList;
import java.util.List;

import pvz.logic.gameobjects.GameObject;
import utils.Position;

/**
 * Container that holds all objects present in the game, delegating each one to {@link GameObject}.
 * 
 * @author Rodrigo Ferrer López
 */

public class GameObjectContainer {
	private List<GameObject> gameObjects, objectsToAdd;
	private boolean updateIsRunning = false;
	
	public GameObjectContainer() {
		gameObjects = new ArrayList<>();
		objectsToAdd = new ArrayList<>();
		updateIsRunning = false;
	}
	
	/**
	 * Tries to add an object to the list. If an update is currently running, adds it to a special list instead.
	 * 
	 * @param obj Object to add
	 * 
	 * @return <code>true</code> if the add was successful.
	 */
	
	public boolean add(GameObject obj) {
		if (updateIsRunning)
		return objectsToAdd.add(obj);
		else {
			if (gameObjects.add(obj)) {
				obj.onEnter();
				return true;
			}
			else
				return false;
		}
	}
	
	/**
	 * Updates all objects on the list, and if an object adds another, adds it and updates it at the end.
	 */
	
	public void update() {
		updateIsRunning = true;
		for (GameObject i : gameObjects)
			i.update();
		for (GameObject i : objectsToAdd) {
			gameObjects.add(i);
			i.onEnter();
			i.update();
		}
		objectsToAdd.clear();
		updateIsRunning = false;
	}
	
	/**
	 * Removes all objects that are considered dead from the list.
	 * 
	 * @return <code>true</code> if at least one object was removed.
	 */
	
	public boolean removeDead() {
		int deadRemoved = 0;
		for (int i = 0; i < gameObjects.size(); i++)
			if (!gameObjects.get(i).isAlive()) {
				gameObjects.get(i).onExit();
				gameObjects.remove(gameObjects.get(i));
				i--;
				deadRemoved++;
			}
		return deadRemoved > 0;
	}
	
	/**
	 * Catches all catchable objects in a given position.
	 * 
	 * @param position Position to catch in.
	 * 
	 * @return the number of objects caught.
	 */
	
	public int catchObject(Position position) {
		int sunsCaught = 0;
		for (int i = 0; i < gameObjects.size(); i++)
			if (gameObjects.get(i).catchObject(position)) {
				gameObjects.get(i).onExit();
				gameObjects.remove(gameObjects.get(i));
				i--;
				sunsCaught++;
			}
		return sunsCaught;
	}
	
	/**
	 * Checks if no objects on the list (except for suns) are in a given position.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if there are no plants or zombies in that position.
	 */
	
	public boolean isPositionEmpty(Position position) {
		int i = 0;
		while (i < gameObjects.size() && (!gameObjects.get(i).isInPosition(position)
				|| (!gameObjects.get(i).receivePlantAttack(0) && !gameObjects.get(i).receiveZombieAttack(0))))
			i++;
		return i == gameObjects.size();
	}
	
	/**
	 * Fetches the representation of a position on the board.
	 * 
	 * @param position Position to check
	 * 
	 * @return a string representing the position.
	 */
	
	public String positionToString(Position position) {
		StringBuilder icons = new StringBuilder();
		for (GameObject i : gameObjects)
			if (i.isInPosition(position) && i.isAlive())
				icons.append(i.getIcon());
		return icons.toString();
	}
	
	/**
	 * Tries to attack a zombie in a given position.
	 * 
	 * @param position Position to check, damage Damage to deal
	 * 
	 * @return <code>true</code> if the attack was successful.
	 */
	
	public boolean attackZombie(Position position, int damage) {
		int i = 0;
		while (i < gameObjects.size() && !gameObjects.get(i).isInPosition(position))
			i++;
		if (i < gameObjects.size())
			return gameObjects.get(i).receivePlantAttack(damage);
		else
			return false;
	}
	
	/**
	 * Tries to attack a plant in a given position.
	 * 
	 * @param position Position to check, damage Damage to deal
	 * 
	 * @return <code>true</code> if the attack was successful.
	 */
	
	public boolean attackPlant(Position position, int damage) {
		int i = 0;
		while (i < gameObjects.size() && !gameObjects.get(i).isInPosition(position))
			i++;
		if (i < gameObjects.size())
			return gameObjects.get(i).receiveZombieAttack(damage);
		else
			return false;
	}
	
	/**
	 * Checks if any object is in a given column.
	 * 
	 * @param column Column to check.
	 * 
	 * @return <code>true</code> if an object in that column has been found.
	 */
	
	public boolean anyInColumn(int column) {
		int i = 0;
		while (i < gameObjects.size() && !gameObjects.get(i).isInColumn(column))
			i++;
		return i < gameObjects.size();
	}
}
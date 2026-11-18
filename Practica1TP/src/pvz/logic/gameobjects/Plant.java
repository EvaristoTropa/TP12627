package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import utils.Position;

/**
 * Subdivision of the GameObject class for plants, the "ally" objects.
 * 
 * @author Rodrigo Ferrer López
 */

public abstract class Plant extends GameObject {
	protected int hp, cost, damage, endurance;
	protected String symbol = "", name = "", nas = ""; // NAS = Name and Shortcut
	
	Plant(Position position, GameWorld game, int cost, int damage, int endurance) {
		super(position, game);
		this.cost = cost;
		this.damage = damage;
		this.endurance = endurance;
		this.hp = endurance;
	}
	
	/**
	 * Checks if a given string matches a plant's name or shortcut.
	 * 
	 * @param plantName String to check
	 * 
	 * @return <code>true</code> if that is the case.
	 */
	
	public boolean matchPlant(String plantName) {
		return plantName.equalsIgnoreCase(symbol) || plantName.equalsIgnoreCase(name);
	}
	
	public boolean isAlive() {
		return this.hp > 0;
	}
	
	public boolean catchObject(Position position) {
		return false;
	}
	
	public boolean receiveZombieAttack(int damage) {
		hp -= damage;
		return true;
	}
	
	public boolean receivePlantAttack(int damage) {
		return false;
	}
	
	/**
	 * Checks if a plant can be spawned at a given position.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if the position is empty and inside the board.
	 */
	
	public boolean canBeSpawnedAt(Position position) {
		return game.isEmpty(position) && game.isInsideBoard(position);
	}
	
	/**
	 * Creates a new plant of the same type with the given parameters.
	 * 
	 * @param position Copy's position, game Game where the copy will reside
	 * 
	 * @return a copy of the corresponding plant.
	 */
	
	abstract public Plant copy(Position position, GameWorld game);
	
	/**
	 * Fetches a plant's name.
	 * 
	 * @return the plant's name.
	 */
	
	abstract public String getName();
	
	/**
	 * Fetches a plant's symbol.
	 * 
	 * @return the plant's symbol.
	 */
	
	abstract public String getSymbol();
	
	/**
	 * Fetches a plant's cost.
	 * 
	 * @return the plant's cost.
	 */
	
	abstract public int getCost();
	
	/**
	 * Fetches a plant's damage.
	 * 
	 * @return the plant's damage.
	 */
	
	abstract public int getDamage();
	
	/**
	 * Fetches a plant's endurance.
	 * 
	 * @return the plant's endurance.
	 */
	
	abstract public int getEndurance();
	
	abstract public String getIcon();
	abstract public String getDescription();
	abstract public void onEnter();
	abstract public void update();
	abstract public void onExit();
}
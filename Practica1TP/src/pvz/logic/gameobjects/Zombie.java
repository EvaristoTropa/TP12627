package pvz.logic.gameobjects;

import pvz.logic.GameWorld;
import utils.Position;

/**
 * Subdivision of the GameObject class for zombies, the "enemy" objects.
 * 
 * @author Rodrigo Ferrer López & Rodrigo Domínguez Carramal
 */

public abstract class Zombie extends GameObject {
	protected int hp, damage, speed, endurance;
	protected boolean blownUp; //Checks if last attack was an explosion
	protected String symbol = "", name = "";
	
	Zombie(Position position, GameWorld game, int damage, int speed, int endurance) {
		super(position, game);
		this.damage = damage;
		this.speed = speed; // Note that zombie speed works inversely. The higher a zombie's speed stat is, the slower it moves.
		this.endurance = endurance;
		this.hp = endurance;
		this.blownUp = false;
	}
	
	/**
	 * Checks if a given string matches a zombie's name or shortcut.
	 * 
	 * @param zombieName String to check
	 * 
	 * @return <code>true</code> if that is the case.
	 */
	
	public boolean matchZombie(String zombieName) {
		return zombieName.equalsIgnoreCase(name) || zombieName.equalsIgnoreCase(symbol);
	}
	
	public boolean isAlive() {
		return this.hp > 0;
	}
	
	public boolean catchObject(Position position) {
		return false;
	}
	
	public boolean receivePlantAttack(int damage) {
		hp -= damage;
		blownUp = false;
		return true;
	}
	
	public boolean receiveExplosiveAttack(int damage) {
		hp -= damage;
		blownUp = true;
		return true;
	}
	
	public boolean receiveZombieAttack(int damage) {
		return false;
	}
	
	/**
	 * Checks if a zombie can be spawned at a given position.
	 * 
	 * @param position Position to check
	 * 
	 * @return <code>true</code> if the position is empty and inside the board or in the side edges.
	 */
	
	public boolean canBeSpawnedAt(Position position) {
		return game.isEmpty(position) && (game.isInsideBoard(position) || game.isInBoardLimit(position));
	}
	
	/**
	 * Creates a new zombie of the same type with the given parameters.
	 * 
	 * @param position Copy's position, game Game where the copy will reside
	 * 
	 * @return a copy of the corresponding zombie.
	 */
	
	abstract public Zombie copy(Position position, GameWorld game);
	
	/**
	 * Fetches a zombie's name.
	 * 
	 * @return the zombie's name.
	 */
	
	abstract public String getName();
	
	/**
	 * Fetches a zombie's symbol.
	 * 
	 * @return the zombie's symbol.
	 */
	
	abstract public String getSymbol();
	
	abstract public String getIcon();
	abstract public String getDescription();
	abstract public void onEnter();
	abstract public void update();
	abstract public void onExit();
}
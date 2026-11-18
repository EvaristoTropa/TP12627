package pvz.logic.gameobjects;

/**
 * Interface that allows game objects to interact with each other.
 * 
 * @author Rodrigo Ferrer López
 */

public interface GameItem {
	
	/**
	 * Tries to attack an object with plant damage.
	 * 
	 * @param damage Damage dealt
	 * 
	 * @return <code>true</code> if the attack was successful.
	 */
	
	boolean receivePlantAttack(int damage);
	
	/**
	 * Tries to attack an object with zombie damage.
	 * 
	 * @param damage Damage dealt
	 * 
	 * @return <code>true</code> if the attack was successful.
	 */
	
	boolean receiveZombieAttack(int damage);
}
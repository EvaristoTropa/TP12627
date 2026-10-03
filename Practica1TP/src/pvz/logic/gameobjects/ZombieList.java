package pvz.logic.gameobjects;

import pvz.utils.Position;

/**
 * Contains a list of all zombies ({@link Zombie}) in the game and methods to handle it.
 * 
 * @author Rodrigo Ferrer López
*/

public class ZombieList {
    private int numberOfZombies;
    private final Zombie[] zombies;
    private static final int MAX_ZOMBIES = 100;
    
    public ZombieList() {
        this.numberOfZombies = 0;
        this.zombies = new Zombie[MAX_ZOMBIES];
    }
    
    /**
	 * Fetches the number of zombies currently in the game.
	 * 
	 * @return the list's size.
	 */
    
    public int size() {
        return numberOfZombies;
    }
    
    /**
	 * Searches for a zombie in a given position.
	 * 
	 * @param p Position to search
	 * 
	 * @return the list's index with the zombie, or -1 if it's not found.
	 */
    
    private int search(Position p) {
    	int i = 0;
        while (i < numberOfZombies && !zombies[i].isInPosition(p))
        i++;
        if (i == numberOfZombies)
        	i = -1;
        return i;
    }
    
    /**
	 * Fetches the icon of a zombie in a given position.
	 * 
	 * @param p Position to search
	 * 
	 * @return the zombie's icon if it's found in that position, or an empty string otherwise.
	 */
    
    public String iconInPosition(Position p) {
        int i = search(p);
        if (i != -1)
        return zombies[i].getIcon();
        else
        return "";
    }
    
    /**
	 * Adds a given zombie to the end of the list.
	 * 
	 * @param z Zombie to add
	 */
    
    public void add(Zombie z) {
        if (numberOfZombies < MAX_ZOMBIES) {
        zombies[numberOfZombies] = z;
        numberOfZombies++;
        }
    }
    
    /**
	 * Looks for a zombie in a given position, then damages it by a given amount if it's found.
	 * 
	 * @param p Position to search, damage Damage to deal
	 * 
	 * @return <code>true</code> if the zombie was found.
	 */
    
    public boolean damage(Position p, int damage) {
    	int i = search(p);
    	if (i != -1)
        zombies[i].receiveAttack(damage);
        return i < numberOfZombies;
    }
    
    /**
	 * Checks if a position doesn't have any zombies.
	 * 
	 * @return the list's size.
	 */
    
    public boolean isEmpty(Position p) {
        return search(p) == -1;
    }
    
    /**
	 * Updates all zombies in the list.
	 */
    
    public void update() {
        for (int i = 0; i < numberOfZombies; i++)
        zombies[i].update();
    }
    
    /**
	 * Checks for dead zombies in the list and removes them.
	 */
    
    public void removeDead() {
        for (int i = 0; i < numberOfZombies; i++)
        if (!zombies[i].isAlive()) {
            numberOfZombies--;
            for (int j = i; j < numberOfZombies; j++)
            zombies[j] = zombies[j + 1];
            i--;
            }
    }
    
    /**
	 * Checks if there's at least one zombie in a given column.
	 * 
	 * @param column Column to check
	 * 
	 * @return <code>true</code> if a zombie is found in the column.
	 */
    
    public boolean anyInColumn(int column) {
        int i = 0;
        Position pos = new Position(0, column);
        while (i < numberOfZombies && !zombies[i].isVerticallyAligned(pos))
        i++;
        return i < numberOfZombies;
    }
}
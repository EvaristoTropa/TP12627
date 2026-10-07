package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Definition of a basic zombie.
 * 
 * <p>Contains essential methods to handle individual instances and check their properties.
 * 
 * @author Rodrigo Ferrer López
*/

public class Zombie {
    private Position position;
	private Game game;
	private int hp, counter;
    private static final int DAMAGE = 1, PERIOD = 2, ENDURANCE = 5;
	
	public Zombie(Position position, Game game) {
		this.position = position;
		this.game = game;
		this.hp = ENDURANCE;
        this.counter = 0;
	}
	
	/**
	 * Fetches a zombie's icon on the board.
	 * 
	 * @return a string with the zombie's icon showing its current health.
	 */
    
    public String getIcon() {
        return Messages.ZOMBIE_ICON.formatted(hp);
    }
    
    /**
     * Checks if a zombie is in a given position.
     * 
     * @param p Position to compare with
     * 
     * @return <code>true</code> if the zombie's position matches the argument.
     */
    
    public boolean isInPosition(Position p) {
        return position.equals(p);
    }
    
    /**
     * Checks if a zombie is in the same row as a given position.
     * 
     * @param p Position to compare with
     * 
     * @return <code>true</code> if the zombie's position's row and the argument's row match.
     */
    
    public boolean isHorizontallyAligned(Position p) {
        return position.isHorizontallyAligned(p);
    }
    
    /**
     * Checks if a zombie is in the same column as a given position.
     * 
     * @param p Position to compare with
     * 
     * @return <code>true</code> if the zombie's position's column and the argument's column match.
     */
    
    public boolean isVerticallyAligned(Position p) {
        return position.isVerticallyAligned(p);
    }
    
    /**
     * Reduces a zombie's HP by a given amount.
     * 
     * @param damage Amount of damage the zombie takes
     */
    
    public void receiveAttack(int damage) {
        hp -= damage;
    }
    
    /**
     * Executes a zombie's actions at the start of a cycle.
     */
    
    public void update() {
    	if (isAlive()) {
    		Position targetPos = new Position(position.row(), position.column() - 1);
    		if (game.isEmpty(targetPos) && counter >= PERIOD) {
    			position = targetPos;
    			targetPos = new Position(position.row(), position.column() - 1);
    			counter = 0;
    		}
    		if (!game.isEmpty(targetPos))
    		game.attackPlant(targetPos, DAMAGE);
    		counter++;
    	}
    }
    
    /**
     * Checks if a zombie is currently considered alive by the game.
     * 
     * @return <code>true</code> if its HP is at least 1.
     */
    
    public boolean isAlive() {
        return hp > 0;
    }
}
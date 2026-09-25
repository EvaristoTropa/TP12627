package pvz.logic.gameobjects;

import pvz.logic.gameobjects.ZombieList
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * Contains the properties of a basic zombie, as well as methods for individual instances.
*/

public class Zombie {
    private Position position;
	private Game game;
	private int hp, lifetime;
    private final int DAMAGE = 1, PERIOD = 2;
	
	public Zombie(Position position, Game game) {
		this.position = position;
		this.game = game;
		this.hp = 5;
        this.counter = 0; // Used for calculating move cycles
	}
    
    public String getIcon() {
        return ZOMBIE_ICON.formatted(hp);
    }
    
    public boolean isInPosition(Position p) {
        return position.equals(p);
    }
    
    public boolean isHorizontallyAligned(Position p) {
        return position.isHorizontallyAligned(p);
    }
    
    public boolean isVerticallyAligned(Position p) {
        return position.isVerticallyAligned(p);
    }
    
    public void receiveAttack(int damage) {
        hp -= damage;
    }
    
    private void attack(Peashooter target) {
        target.receiveDamage(DAMAGE);
    }
    
    private void attack(Sunflower target) {
        target.receiveDamage(DAMAGE);
    }
    
    public void update() {
        Position targetPos = new Position(row, col - 1);
        
        if (isEmpty(targetPos))
        if (counter >= PERIOD) {
            position = targetPos;
            counter = 0;
        }
        
        // TODO figure out how to check for plants in front of the zombie and damage them
        counter++;
    }
    
    public boolean isAlive() {
        return hp > 0;
    }
    
}
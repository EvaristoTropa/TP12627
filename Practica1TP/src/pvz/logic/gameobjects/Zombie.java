package pvz.logic.gameobjects;

/**
 * Contains the properties of a basic zombie, as well as methods for individual instances.
*/

public class Zombie {
	private Game game;
	private int col, row, hp, damage, period, lifetime;
	
	public Zombie(Game game, int col, int row) {
		this.game = game;
		this.col = col;
		this.row = row;
        // Zombie stats
		this.hp = 5;
        this.damage = 1;
		this.period = 2; // Basic zombies move one cell every 2 cycles
        this.lifetime = 0; // Used for calculating move cycles
	}
    
    public int getCol() {
        return this.col;
    }
    
    public int getRow() {
        return this.row;
    }
    
    public int getHealth() {
        return this.hp;
    }
    
    public int getSpeed() {
        return this.period;
    }
    
    public int getLifetime() {
        return this.lifetime;
    }
    
    public void advanceCycle() {
        this.lifetime++;
    }
    
    public void damage(int damage) {
        this.hp -= damage;
    }
    
    public boolean canMove() {
        return getLifetime() != 0 && getLifetime() % getSpeed() == 0;
    }
    
    public boolean isDead() {
        return getHealth() <= 0;
    }
}
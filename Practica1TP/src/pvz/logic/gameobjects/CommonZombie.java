package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;

/**
 * Standard enemy that moves every 2 cycles and attacks plants in front of it.
 * 
 * @author Rodrigo Ferrer López
 */

public class CommonZombie extends Zombie {
	private int counter = 0;
	
	public CommonZombie(Position position, GameWorld game) {
		super(position, game, 1, 2, 5);
		this.symbol = Messages.ZOMBIE_SYMBOL;
		this.name = Messages.ZOMBIE_NAME;
	}
	
	public Zombie copy(Position position, GameWorld game) {
		return new CommonZombie(position, game);
	}
	
	public String getIcon() {
		return Messages.ZOMBIE_ICON.formatted(hp);
	}
	
	public String getDescription() {
		return Messages.ZOMBIE_DESCRIPTION.formatted(name, speed, damage, endurance);
	}
	
	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public void update() {
		if (isAlive()) {
			Position targetPos = position.left();
			if (counter >= speed) {
			if (game.isEmpty(targetPos)) {
				position = targetPos;
				targetPos = targetPos.left();
				counter = 0;
				}
			}
			game.zombieAttack(targetPos, damage);
			counter++;
		}
	}
	
	public void onExit() {
		game.zombieKilled();
	}
	
	public void onEnter() {}
}
package pvz.control.command;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;

/**
 * Less durable but faster zombie.
 * 
 * @author Rodrigo Ferrer López
 */

public class SportyZombie extends Zombie {
	private int counter = 0;
	
	public SportyZombie(Position position, GameWorld game) {
		super(position, game, 1, 1, 2);
		this.symbol = Messages.SPORTY_ZOMBIE_SYMBOL;
		this.name = Messages.SPORTY_ZOMBIE_NAME;
	}
	
	public Zombie copy(Position position, GameWorld game) {
		return new SportyZombie(position, game);
	}
	
	public String getIcon() {
		return Messages.GAME_OBJECT_ICON.formatted(symbol, hp);
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
package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;

/**
 * Zombie that walks every 2 cycles and attacks plants in front of it.
 * 
 * @author Rodrigo Ferrer López
 */

public class CommonZombie extends Zombie {
	public CommonZombie(GameWorld game, Position position) {
		super(game, position, 1, 2, 5);
		this.symbol = Messages.ZOMBIE_SYMBOL;
		this.name = Messages.ZOMBIE_NAME;
		this.description = Messages.ZOMBIE_DESCRIPTION;
		this.icon = Messages.ZOMBIE_ICON;
		this.nas = Messages.ZOMBIE_NAME_AND_SHORTCUT;
	}
	
	public CommonZombie create(GameWorld game, Position position) {
		return new CommonZombie(game, position);
	}
	
	public String getIcon() {
		return icon.formatted(hp);
	}
	
	public String getDescription() {
		return description.formatted(name, period, damage, endurance);
	}
	
	public void update() {
		if (isAlive()) {
			Position targetPos = Position.left(position);
			if (counter >= period) {
			if (game.isEmpty(targetPos)) {
				position = targetPos;
				targetPos = Position.left(position);
				counter = 0;
				}
			}
			game.attackPlant(targetPos, damage);
			counter++;
		}
	}
	
	public void onExit() {
		game.zombieDies();
	}
	
	public void onEnter() {}
}
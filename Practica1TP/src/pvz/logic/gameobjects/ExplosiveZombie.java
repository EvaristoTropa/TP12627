package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;
import pvz.logic.actions.ExplosionAction;

/**
 * Basic zombie that explodes on death.
 * 
 * @author Rodrigo Ferrer López
 */

public class ExplosiveZombie extends Zombie {
	private int counter = 0;
	
	public ExplosiveZombie(Position position, GameWorld game) {
		super(position, game, 1, 2, 5);
		this.symbol = Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
		this.name = Messages.EXPLOSIVE_ZOMBIE_NAME;
	}
	
	public Zombie copy(Position position, GameWorld game) {
		return new ExplosiveZombie(position, game);
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
		else {
			Position[] positions = {
					position.up(),
					position.upRight(),
					position.right(),
					position.downRight(),
					position.down(),
					position.downLeft(),
					position.left(),
					position.upLeft()
			};
			ExplosionAction explosion = new ExplosionAction(3, false, positions);
			game.pushAction(explosion);
		}
	}
	
	public void onExit() {
		game.zombieKilled();
	}
	
	public void onEnter() {}
}
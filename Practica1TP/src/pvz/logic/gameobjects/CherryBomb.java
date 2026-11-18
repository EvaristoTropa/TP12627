package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;
import pvz.logic.actions.ExplosionAction;

/**
 * Plant that explodes after a few cycles, damaging zombies around it while dying in the process.
 * 
 * @author Rodrigo Ferrer López
 */

public class CherryBomb extends Plant {
	private boolean primed = false;
	private int counter = 0;
	private static final int PERIOD = 2;
	public static final int COST = 50;
	
	public CherryBomb(Position position, GameWorld game) {
		super(position, game, COST, 10, 2);
		this.symbol = Messages.CHERRY_BOMB_SYMBOL;
		this.name = Messages.CHERRY_BOMB_NAME;
		this.nas = Messages.CHERRY_BOMB_NAME_SHORTCUT;
		this.primed = false;
		this.counter = 0;
	}
	
	public Plant copy(Position position, GameWorld game) {
		return new CherryBomb(position, game);
	}
	
	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getIcon() {
		return Messages.GAME_OBJECT_ICON.formatted(symbol, hp);
	}
	
	public String getDescription() {
		return Messages.PLANT_DESCRIPTION.formatted(nas, cost, damage, endurance);
	}
	
	public int getCost() {
		return cost;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getEndurance() {
		return endurance;
	}
	
	public void update() {
		if (isAlive()) {
			if (primed) {
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
				ExplosionAction explosion = new ExplosionAction(damage, true, positions);
				game.pushAction(explosion);
				game.zombieAttack(position, hp);
			}
			else {
				if (counter == PERIOD - 1) {
					primed = true;
					symbol = "C";
				}
				counter++;
			}
		}
	}
	
	public void onEnter() {}
	public void onExit() {}
}
package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;

/**
 * Plant that shoots zombies in a straight line.
 * 
 * @author Rodrigo Domínguez Carramal (V1), Rodrigo Ferrer López (V2)
 */

public class Peashooter extends Plant {
	public static final int COST = 50;
	
	public Peashooter(Position position, GameWorld game) {
		super(position, game, COST, 1, 3);
		this.symbol = Messages.PEASHOOTER_SYMBOL;
		this.name = Messages.PEASHOOTER_NAME;
		this.nas = Messages.PEASHOOTER_NAME_AND_SHORTCUT;
	}
	
	public Plant copy(Position position, GameWorld game) {
		return new Peashooter(position, game);
	}
	
	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getIcon() {
		return Messages.PEASHOOTER_ICON.formatted(hp);
	}
	
	public String getDescription() {
		return Messages.PEASHOOTER_DESCRIPTION.formatted(cost, damage, endurance);
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
			Position targetPos = position;
			while (targetPos.column() < GameWorld.NUM_COLS && !game.plantAttack(targetPos, damage))
			targetPos = targetPos.right();
		}
	}
	
	public void onEnter() {}
	public void onExit() {}
}
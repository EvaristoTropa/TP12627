package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;

/**
 * Plant that passively generates suns.
 * 
 * @author Rodrigo Domínguez Carramal (V1), Rodrigo Ferrer López (V2)
 */

public class Sunflower extends Plant {
	private int counter = 0;
	private static final int PERIOD = 3;
	public static final int COST = 20;
	
	public Sunflower(Position position, GameWorld game) {
		super(position, game, COST, 0, 1);
		this.symbol = Messages.SUNFLOWER_SYMBOL;
		this.name = Messages.SUNFLOWER_NAME;
		this.nas = Messages.SUNFLOWER_NAME_AND_SHORTCUT;
		this.counter = 0;
	}
	
	public Plant copy(Position position, GameWorld game) {
		return new Sunflower(position, game);
	}
	
	public String getName() {
		return name;
	}
	
	public String getSymbol() {
		return symbol;
	}
	
	public String getIcon() {
		return Messages.SUNFLOWER_ICON.formatted(hp);
	}
	
	public String getDescription() {
		return Messages.SUNFLOWER_DESCRIPTION.formatted(cost, damage, endurance);
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
			if (counter >= PERIOD) {
				game.generateSun();
				counter = 0;
			}
			counter++;
		}
	}
	
	public void onEnter() {}
	public void onExit() {}
}
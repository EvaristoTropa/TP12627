package pvz.logic.gameobjects;

import pvz.view.Messages;
import utils.Position;
import pvz.logic.GameWorld;

/**
 * Highly resistant plant that performs no actions, serving as a barrier.
 * 
 * @author Rodrigo Ferrer López
 */

public class WallNut extends Plant {
	public static final int COST = 50;
	
	public WallNut(Position position, GameWorld game) {
		super(position, game, COST, 0, 10);
		this.symbol = Messages.WALL_NUT_SYMBOL;
		this.name = Messages.WALL_NUT_NAME;
		this.nas = Messages.WALL_NUT_NAME_SHORTCUT;
	}
	
	public Plant copy(Position position, GameWorld game) {
		return new WallNut(position, game);
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
	
	public void update() {}
	public void onEnter() {}
	public void onExit() {}
}
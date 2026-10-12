package pvz.logic.gameobjects;

import pvz.logic.Game;
import pvz.utils.Position;
import pvz.view.Messages;

/**
 * 
 * 
 * @author Rodrigo Domínguez Carramal
 */


public class Sunflower {
	
	private Position pos;
	private Game game;
	private int hp;
	private int cooldown;
	public static final int DAMAGE = 0, FRECUENCY = 3, ENDURANCE = 1;
	private static final String shortn = "s", longn = "sunflower", desc = "Genera soles.\n";
	
	
	public static final int COST = 20;
	
	public Sunflower(Position pos, Game g) {
		this.pos = pos;
		this.game = g;
		this.hp = ENDURANCE;
		this.cooldown = 0;
	}
	
	public static String shortName() {
		return shortn;
	}
	
	public static String longName() {
		return longn;
	}
	
	public static String getDescription() {
		return desc;
	}
	
	public void update() {
		if(!dead()) {
			cooldown++;
			if(cooldown >= FRECUENCY) {
				game.generateCoins(10);
				cooldown = 0;
			}
		}
	}

	public boolean isInPosition(Position p) {

		return pos == p;
	}

	public void receiveAttack(int damage) {
		
		hp -= damage;
	}

	public boolean dead() {

		return hp <= 0;
	}
	
	public String getIcon() {
		
		return Messages.SUNFLOWER_ICON.formatted(hp);
	}

}

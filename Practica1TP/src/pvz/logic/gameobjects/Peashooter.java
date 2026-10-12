package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;
import pvz.logic.Game;

/**
 * 
 * 
 * @author Rodrigo Domínguez Carramal
 */


public class Peashooter {

	private Position pos;
	private Game game;
	private int hp;
	private int cooldown;
	public static final int DAMAGE = 1, FRECUENCY = 1, ENDURANCE = 3;
	private static final String shortn = "p", longn = "peashooter", desc = "Dispara guisantes a los zombis causándoles daño.\n";
	
	private void attack() {
		
		int z = pos.row();
		int i = pos.column() + 1;
		Position target = new Position(z, i);
		while(i < Game.NUM_COLS && !game.containsZombie(target)) {
			i++;
			target = new Position(z, i);
		}
		if(i< game.NUM_COLS) {
			game.attackZombie(target, DAMAGE);
		}
		
	}
	
	
	public static final int COST = 50;
	
	public Peashooter(Position pos, Game game) {
		
		this.pos = pos;
		this.game = game;
		this.cooldown = FRECUENCY;
		this.hp = ENDURANCE;
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
		
		if(isAlive()) {
			
			if(cooldown>=FRECUENCY) {
				attack();
				cooldown = 0;
			}
			cooldown++;
		}
	}

	public boolean isInPosition(Position p) {

		return pos.equals(p);
	}

	public void receiveDamage(int damage) {
		
		hp -= damage;
	}

	public boolean isAlive() {

		return hp > 0;
	}
	
public String getIcon() {
		
		return Messages.PEASHOOTER_ICON.formatted(hp);
	}
}

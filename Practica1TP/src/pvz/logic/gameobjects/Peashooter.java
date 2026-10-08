package pvz.logic.gameobjects;

import pvz.utils.Position;
import pvz.view.Messages;
import pvz.logic.Game;

public class Peashooter {

	private Position pos;
	private Game game;
	private int hp;
	private int cooldown;
	private static final int DAMAGE = 1, FRECUENCY = 1, ENDURANCE = 3;
	private static final String shortn = "p", longn = "peashooter";
	
	private void attack() {
		
		int z = pos.row();
		int i = pos.column() + 1;
		Position target = new Position(z, i);
		while(i < game.NUM_COLS && game.isEmpty(target)) {
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
		this.cooldown = 0;
		this.hp = ENDURANCE;
	}
	
	
	
	public String shortName() {
		return shortn;
	}
	
	public String longName() {
		return longn;
	}
	
	public void update() {
		
		if(!dead()) {
			cooldown++;
			if(cooldown>=FRECUENCY) {
				attack();
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
		
		return Messages.PEASHOOTER_ICON.formatted(hp);
	}
}

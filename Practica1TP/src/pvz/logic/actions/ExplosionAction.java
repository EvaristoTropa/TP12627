package pvz.logic.actions;

import pvz.logic.GameWorld;
import utils.Position;

public class ExplosionAction implements GameAction {
	private int damage;
	private boolean isPlant;
	private Position[] positions;
	
	public ExplosionAction(int damage, boolean isPlant, Position[] positions) {
		this.damage = damage;
		this.isPlant = isPlant;
		this.positions = positions;
	}
	
	public void execute(GameWorld game) {
		if (isPlant)
			for (Position i : positions)
				game.plantAttack(i, damage);
		else
			for (Position i : positions)
				game.zombieAttack(i, damage);
	}
}
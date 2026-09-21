package pvz.logic;

public class Zombie {
	private Game game;
	private int col, row, hp, period;
	
	public Zombie(Game game, int col, int row)
	{
		this.game = game;
		this.col = col;
		this.row = row;
		this.hp = 5;
		this.period = 2; // The zombie moves every 2 cycles
	}
}
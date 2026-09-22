package pvz.logic.gameobjects;

import pvz.logic.gameobjects.Zombie;

public class ZombieList {
    private int numberOfZombies;
    private final Zombie[] zombies;
    private final int MAX_ZOMBIES = 100;
    
    public ZombieList () {
        this.numberOfZombies = 0;
        this.zombies = new Zombie[MAX_ZOMBIES];
    }
    
    public void addZombie (Game game, int col, int row) {
        if (this.numberOfZombies < MAX_ZOMBIES) {
        this.zombies[this.numberOfZombies] = new Zombie(game, col, row);
        this.numberOfZombies++;
        }
    }
    
    public void removeZombie (int pos) {
        if (this.numberOfZombies > 0) {
            this.numberOfZombies--;
            for (int i = pos; i < this.numberOfZombies; i++)
            this.zombies[i] = this.zombies[i + 1];
        }
    }
}
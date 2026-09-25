package pvz.logic.gameobjects;

import pvz.logic.gameobjects.Zombie;
import pvz.utils.Position;

public class ZombieList {
    private int numberOfZombies;
    private final Zombie[] zombies;
    private final int MAX_ZOMBIES = 100;
    
    public ZombieList() {
        this.numberOfZombies = 0;
        this.zombies = new Zombie[MAX_ZOMBIES];
    }
    
    public int size() {
        return numberOfZombies;
    }
    
    public String iconInPosition(Position p) {
        int i = 0;
        while (i < numberOfZombies && zombies[i].!isInPosition(p))
        i++;
        if (i < numberOfZombies)
        return zombies[i].getIcon();
        else
        return "";
    }
    
    public void add(Zombie z) {
        if (numberOfZombies < MAX_ZOMBIES) {
        zombies[numberOfZombies] = z;
        numberOfZombies++;
        }
    }
    
    public boolean damage(Position p, int damage) {
        int i = 0;
        while (i < numberOfZombies && zombies[i].!isInPosition(p))
        i++;
        if (i < numberOfZombies)
        zombies[i].receiveAttack(damage);
        return i < numberOfZombies;
    }
    
    public boolean isEmpty(Position p) {
        int i = 0;
        while (i < numberOfZombies && zombies[i].!isInPosition(p))
        i++;
        return i == numberOfZombies;
    }
    
    public void update() {
        for (int i = 0; i < numberOfZombies; i++)
        zombies[i].update();
    }
    
    public void removeDead() {
        for (int i = 0; i < numberOfZombies; i++)
        if (zombies[i].!isAlive()) {
            numberOfZombies--;
            for (int j = i; j < numberOfZombies; j++)
            zombies[j] = zombies[j + 1];
            i--;
            }
    }
    
    public boolean anyInColumn(int column) {
        int i = 0;
        Position pos = new Position(0, column);
        while (i < numberOfZombies && zombies[i].!isVerticallyAligned(pos))
        i++;
        return i < numberOfZombies;
    }
}
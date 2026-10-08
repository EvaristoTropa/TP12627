package pvz.logic.gameobjects;

import pvz.utils.Position;

public class SunflowerList {
	private int numberOfSuns;
	private final Sunflower[] sunflowers;
	private static final int MAX_SUNS = 100;
	
	public SunflowerList() {
		this.numberOfSuns = 0;
		this.sunflowers = new Sunflower[MAX_SUNS];
	}
	
	public void update() {
		for(int i = 0; i<numberOfSuns; i++) sunflowers[i].update();
	}
	
	public int size() {
		
		return numberOfSuns;
	}
	
	public boolean isEmpty(Position p) {
		
		return search(p) == -1;
	}	
	
	
	
	// Returns the index of a Sunflower in the position given, if theres any
	private int search(Position p) {
    	int i = 0;
        while (i < numberOfSuns && !sunflowers[i].isInPosition(p)) i++;
        if (i == numberOfSuns) i = -1;
        return i;
    }
	
	public void add(Sunflower sun) {
		if(numberOfSuns < MAX_SUNS) {
			sunflowers[numberOfSuns] = sun;
			numberOfSuns++;
		}
	}
	
	
	//Removes a Sunflower keeping the consistency of the list
	public void remove(int index) {
		int a = index;
		while(a<numberOfSuns-1) {
			sunflowers[a]=sunflowers[a+1];
			a++;
		}
		numberOfSuns--;
	}
	
	public boolean damage(Position p, int damage) {
    	int i = search(p);
    	if (i != -1)
        sunflowers[i].receiveAttack(damage);
        return i < numberOfSuns;
    }
	
	
	//Removes every dead Sunflower from the list
	public void removeDead() {
		for(int i = 0; i<numberOfSuns; i++) {
			if(sunflowers[i].dead()) {
				remove(i);
			}
		}
	}
	
	public void receiveDamage(Position p, int dmg) {
		
		int a = search(p);
		sunflowers[a].receiveAttack(dmg);
	}

	public String iconInPosition(Position p) {
		int i = search(p);
		if (i != -1)
			return sunflowers[i].getIcon();
		else
			return "";
}
	
}

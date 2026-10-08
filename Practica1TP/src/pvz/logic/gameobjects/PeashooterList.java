package pvz.logic.gameobjects;

import pvz.utils.Position;


/**
 * Contains a list of all plants in the game and methods to handle it
 * 
 * @author Rodrigo Domínguez Carramal
 * 
 */

public class PeashooterList {
	private int numberOfPeas;
	private final Peashooter[] peashooters;
	private static final int MAX_PEAS = 100;
	
	
	public PeashooterList() {
		this.numberOfPeas = 0;
		this.peashooters = new Peashooter[MAX_PEAS];
	}
	
}
	
	public void update() {
		for(int i = 0; i<numberOfPeas; i++) peashooters[i].update();
	}
	
	public int size() {
		
		return numberOfPeas;
	}
	
	public boolean isEmpty(Position p) {
		
		return search(p) == -1;
	}	
	
	// Returns de index of a Peashooter in the position given, if theres any
	private int search(Position p) {
    	int i = 0;
        while (i < numberOfPeas && !peashooters[i].isInPosition(p)) i++;
        if (i == numberOfPeas) i = -1;
        return i;
    }
	
	public void add(Peashooter pea) {
		if(numberOfPeas < MAX_PEAS) {
			peashooters[numberOfPeas] = pea;
			numberOfPeas++;
		}
	}
	
	
	//Removes a Peashooter keeping the consistency of the list
	public void remove(int index) {
		int a = index;
		while(a<numberOfPeas-1) {
			peashooters[a]=peashooters[a+1];
			a++;
		}
		numberOfPeas--;
	}
	
	public boolean damage(Position p, int damage) {
    	int i = search(p);
    	if (i != -1) peashooters[i].receiveAttack(damage);
        return i == -1;
    }
	
	
	//Removes every dead Peashooter from the list
	public void removeDead() {
		for(int i = 0; i<numberOfPeas; i++) {
			if(peashooters[i].dead()) {
				remove(i);
				i--;
			}
		}
	}
	
	public void receiveDamage(Position p, int dmg) {
		
		int a = search(p);
		peashooters[a].receiveAttack(dmg);
	}
	
	public String iconInPosition(Position p) {
        int i = search(p);
        if (i != -1)
        return peashooters[i].getIcon();
        else
        return "";
    }
	
	
}

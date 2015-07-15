package djinn;

import java.util.ArrayList;

public class World {
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> entitiesToBeRemoved = new ArrayList<Entity>();
	public int randEnemy;
	public int initialNumEnemies;
	
	public World(Djinn djinn){
		this.entities.add(djinn.thePlayer);
		this.entities.add(djinn.theBall);
		this.entities.add(djinn.theDivider);
		this.entities.addAll(djinn.EnemyList);
		this.initialNumEnemies = djinn.EnemyList.size()*2;
	}
	
	public void run(Djinn djinn) {
		//TODO fix game crash once EnemyList.size() == 0

		this.addEnemyShot(djinn);
		
		for (Entity entity : this.entities) {
			entity.onUpdate(djinn);			// Update all entities
		}
		
		for (Entity entity : this.entitiesToBeRemoved) {
			this.entities.remove(entity);
			djinn.EnemyList.remove(entity);
		}
	}
	
	public void addEnemyShot(Djinn djinn) {
		if (!djinn.theBall.gameStart) return;
		
		if (djinn.ShotList.size() > 0) djinn.ShotList.remove(0); // Uninitialize the last shot	

		randEnemy = getRandRange(0, djinn.EnemyList.size()); // Choose a random enemy from the EnemyList
		int randNum = getRandRange(0, initialNumEnemies);
		
		if (entities.contains(djinn.EnemyList.get(randEnemy)) && randEnemy == randNum ) { // This logic limits the amount of shots being produced
			djinn.ShotList.add(new EntityShot(djinn, djinn.EnemyList.get(randEnemy).posX, djinn.EnemyList.get(randEnemy).posY)); 			// Initialize a shot from the random enemy
			this.entities.add(djinn.ShotList.get(0)); 			// Add the initialized shot to the entities ArrayList
		}
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}
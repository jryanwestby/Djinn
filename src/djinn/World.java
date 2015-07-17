package djinn;

import java.util.ArrayList;

public class World {
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	//public ArrayList<EntityShot> PlayerShotList = new ArrayList<EntityShot>();
	public ArrayList<EntityEnemyShot> EnemyShotList = new ArrayList<EntityEnemyShot>();
	public ArrayList<Entity> entitiesToBeRemoved = new ArrayList<Entity>();
	public int randEnemy;
	public int initialNumEnemies;
	
	public World(Djinn djinn){
		this.entities.add(djinn.thePlayer);
		this.entities.add(djinn.theBall);
		this.entities.add(djinn.theDivider);
		this.entities.addAll(djinn.EnemyList);
		this.initialNumEnemies = djinn.EnemyList.size();
	}
	
	public void run(Djinn djinn) {

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
		if (!djinn.gameStart) return;
		
		if (this.EnemyShotList.size() > 0) this.EnemyShotList.remove(0); // Uninitialize the last shot	

		randEnemy = getRandRange(0, djinn.EnemyList.size()); // Choose a random enemy from the EnemyList
		int randNum = getRandRange(0, initialNumEnemies);
		
		//TODO fix index out of bounds
		if (randEnemy == randNum && entities.contains(djinn.EnemyList.get(randEnemy))) { // This logic limits the amount of shots being produced
			this.EnemyShotList.add(new EntityEnemyShot(djinn, djinn.EnemyList.get(randEnemy).posX, djinn.EnemyList.get(randEnemy).posY)); 	// Initialize a shot from the random enemy
			this.entities.add(this.EnemyShotList.get(0)); 			// Add the initialized shot to the entities ArrayList
		}
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}
package djinn;

import java.util.ArrayList;

public class World {
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> entitiesToBeRemoved = new ArrayList<Entity>();
	public EntityShot currentShot;
	public int randEnemy;
	
	public World(Djinn djinn){
		this.entities.add(djinn.thePlayer);
		this.entities.add(djinn.theBall);
		this.entities.add(djinn.theDivider);
		this.entities.addAll(Djinn.EnemyList);
	}
	
	public void run(Djinn djinn) {
		//TODO fix game crash once EnemyList.size() == 0
		System.out.println(djinn.ShotList.size());
		if (djinn.ShotList.size() > 0) djinn.ShotList.remove(0); // Uninitialize the last shot	

		randEnemy = getRandRange(0, Djinn.EnemyList.size()); // Choose a random enemy from the EnemyList
		int randNum = getRandRange(0, this.entities.size());
		if (entities.contains(Djinn.EnemyList.get(randEnemy)) && randEnemy == randNum ) { // This logic limits the amount of shots being produced
			// Initialize a shot from the random enemy
			djinn.ShotList.add(new EntityShot(djinn, Djinn.EnemyList.get(randEnemy).posX, Djinn.EnemyList.get(randEnemy).posY));

			// Add the initialized shot to the entities ArrayList
			this.entities.add(djinn.ShotList.get(0));
		}
		
		// Update all entities
		for (Entity entity : this.entities) {
			entity.onUpdate(djinn); 
		}
		
		// If an alien has been shot, remove it from the update list
		for (EntityEnemy entity : Djinn.EnemyList) {
			if (entity.alienIsShot) {
				this.entities.remove(entity);
				this.entitiesToBeRemoved.add(entity);
			}
		}
		
		for (Entity entity : this.entitiesToBeRemoved) {
			Djinn.EnemyList.remove(entity);
		}
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}
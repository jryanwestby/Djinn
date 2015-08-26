package djinn;

import java.util.ArrayList;
import org.lwjgl.input.Keyboard;

public class World {
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Entity> enemyShotsToBeAdded = new ArrayList<Entity>();
	public ArrayList<Entity> entitiesToBeRemoved = new ArrayList<Entity>();
	public int initialNumEnemies;
	
	
	public World(Djinn djinn){
		
		this.entities.add(djinn.thePlayer);
		//this.entities.add(djinn.theBall);
		this.entities.add(djinn.theDivider);
		this.entities.addAll(djinn.EnemyList);
		
		this.initialNumEnemies = djinn.EnemyList.size();
	}
	
	public void run(Djinn djinn) {
		if (djinn.thePlayer.playerShotReady) {
			this.entities.add(djinn.playerShot);
			djinn.thePlayer.playerShotReady = false;
		}
	
		for (Entity entity : this.enemyShotsToBeAdded) {
			this.entities.add(entity);
		}
		
		for (Entity entity : this.entities) {
			entity.onUpdate(djinn);			// Update all entities
		}
		
		djinn.blockHandler.onUpdate(djinn);
		
		for (Entity entity : this.entitiesToBeRemoved) {
			this.entities.remove(entity);
			djinn.EnemyList.remove(entity);
		}
	}	
}
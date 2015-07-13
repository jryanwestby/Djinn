package djinn;

import java.util.ArrayList;

public class World {
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public World(Djinn djinn){
		this.entities.add(djinn.thePlayer);
		this.entities.add(djinn.theBall);
		this.entities.addAll(djinn.EnemyList);
		this.entities.add(djinn.theDivider);
	}
	
	public void run(Djinn djinn) {
		for (Entity entity : this.entities) {
			entity.onUpdate(djinn);
		}
		
		for (EntityEnemy entity : djinn.EnemyList) {
			if (entity.alienIsShot) {
				this.entities.remove(entity);
			}
		}
	}
}
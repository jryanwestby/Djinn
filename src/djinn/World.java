package djinn;

import java.util.ArrayList;

public class World {
	public ArrayList<Entity> enemyShotList = new ArrayList<Entity>();
	public ArrayList<Entity> entitiesToBeRemoved = new ArrayList<Entity>();
	public int initialNumEnemies;
	
	public boolean newGame = true;
	public boolean gameStart = true;
	
	// initialize title screen stuff
	
	public World(Djinn djinn){
		this.initialNumEnemies = djinn.EnemyList.size();
	}
	
	public void run(Djinn djinn) {
		
		// Run title screen until option is selected
		// depending on option, update entities
	
		for (Entity enemyShot : this.enemyShotList) {
			enemyShot.onUpdate(djinn);
		}
		
		for (Entity enemy : djinn.EnemyList) {
			enemy.onUpdate(djinn);			// Update all enemies
		}
		
		// if flag for playerShot, update 
		djinn.playerShot.onUpdate(djinn);
		djinn.blockHandler.onUpdate(djinn);
		
		for (Entity entity : this.entitiesToBeRemoved) {
			djinn.EnemyList.remove(entity);
		}
	}
	
	public void gameReset(Djinn djinn) {
		djinn.thePlayer.width -= 10;

		while (!djinn.blockHandler.newBlockReady) {
			djinn.blockHandler.onUpdate(djinn);
		}
	}
}

// Title screen code:
//
//import java.awt.Font;
//import org.newdawn.slick.TrueTypeFont;
//
//public class StateHandler {
//	
////	private TrueTypeFont font;
//	public Keybind keyReturn;
//
//	public StateHandler(Djinn djinn) {
////		Font awtFont = new Font("Futura", Font.BOLD, 24);
////		this.font = new TrueTypeFont(awtFont, true);
//	}
//}

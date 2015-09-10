package djinn;

import java.util.ArrayList;

public class World {
	public ArrayList<Entity> enemyShotList = new ArrayList<Entity>();
	public ArrayList<Entity> enemiesToBeRemoved = new ArrayList<Entity>();
	public ArrayList<Entity> enemyShotsToBeRemoved = new ArrayList<Entity>();
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
		
		if (djinn.thePlayer.shotActive){
			djinn.playerShot.onUpdate(djinn);
		}
		djinn.thePlayer.onUpdate(djinn);
		djinn.theDivider.onUpdate(djinn);
		djinn.blockHandler.onUpdate(djinn);
		
		for (Entity enemy : this.enemiesToBeRemoved) {
			djinn.EnemyList.remove(enemy);
		}
		
		for (Entity shot : this.enemyShotsToBeRemoved) {
			djinn.theWorld.enemyShotList.remove(shot);
		}
		
		if (!djinn.thePlayer.shotActive) {
			djinn.playerShot = null;
		}
	}
	
	public void gameReset(Djinn djinn) {
		djinn.thePlayer.width -= 10;
//
//		while (!djinn.blockHandler.newBlockReady) {
//			djinn.blockHandler.onUpdate(djinn);
//		}
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

package djinn;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class World {
	public ArrayList<Entity> enemyShotList = new ArrayList<Entity>();
	public ArrayList<Entity> enemiesToBeRemoved = new ArrayList<Entity>();
	public ArrayList<Entity> enemyShotsToBeRemoved = new ArrayList<Entity>();
	public int initialNumEnemies;
	
	public boolean titleState;
	public boolean readyState;
	public boolean playState;
	public boolean endState;
	
	public Keybind keyR;
	public Keybind keyReturn;
	public Keybind keyUp;
	public Keybind keyDown;
	public long lastkeyUp;
	public long lastkeyDown;
	
	public World(Djinn djinn){
		this.initialNumEnemies = djinn.EnemyList.size();
		
		this.titleState = true;
		
		this.keyR = new Keybind(Keyboard.KEY_R, "R");
		this.keyReturn = new Keybind(Keyboard.KEY_RETURN, "Return");
		this.keyUp = new Keybind(Keyboard.KEY_UP, "Up");
		this.keyDown = new Keybind(Keyboard.KEY_DOWN, "Down");
	}
	
	public void run(Djinn djinn) {	

		if (this.titleState) {
			djinn.textHandler.titleText(djinn);			
			
			if (this.keyReturn.isKeyDown()) {
				this.titleState = false;
				this.readyState = true;
			}
		}
		else if (this.readyState) {
			
			// R key begins game 
			if (this.keyR.isKeyDown()) {
				this.playState = true;
			}
			
			// Update entities
			djinn.thePlayer.onUpdate(djinn);
			djinn.theDivider.onUpdate(djinn);
			djinn.blockHandler.onUpdate(djinn);
			for (Entity enemyShot : this.enemyShotList) {
				enemyShot.onUpdate(djinn);
			}
			for (Entity enemy : djinn.EnemyList) {
				enemy.onUpdate(djinn);
			}
			if (djinn.thePlayer.shotActive){
				djinn.playerShot.onUpdate(djinn);
			}
			
			// Remove entities
			for (Entity enemy : this.enemiesToBeRemoved) {
				djinn.EnemyList.remove(enemy);
			}
			
			for (Entity shot : this.enemyShotsToBeRemoved) {
				this.enemyShotList.remove(shot);
			}
			if (!djinn.thePlayer.shotActive) {
				djinn.playerShot = null;
			}
			
		}
		else if (this.endState) {
			this.enemyShotList.clear();
			djinn.EnemyList.clear();
			djinn.theDivider = null;
			djinn.blockHandler = null;
			djinn.thePlayer = null;
			
			djinn.textHandler.endText(djinn);
			
			if (this.keyReturn.isKeyDown()) {
				this.endState = false;
				Djinn.initEntities();
				this.titleState = true;
			}
		}
	}
}

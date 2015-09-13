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
	
	public int gameChoice;
	
	public Keybind keyR;
	public Keybind keyReturn;
	public Keybind keyUp;
	public Keybind keyDown;
	public long lastkeyUp;
	public long lastkeyDown;
	
	public World(Djinn djinn){
		this.initialNumEnemies = djinn.EnemyList.size();
		
		this.titleState = true;
		this.gameChoice = 0;
		
		this.keyR = new Keybind(Keyboard.KEY_R, "R");
		this.keyReturn = new Keybind(Keyboard.KEY_RETURN, "Return");
		this.keyUp = new Keybind(Keyboard.KEY_UP, "Up");
		this.keyDown = new Keybind(Keyboard.KEY_DOWN, "Down");
	}
	
	public void run(Djinn djinn) {	

		if (this.titleState) {
			if (djinn.theWorld.enemyShotList.size() == 0) {
				djinn.theWorld.enemyShotList.add(new EntityEnemyShot(djinn, 120, 462));
				djinn.theWorld.enemyShotList.get(0).motionY = 0;
			}
			
			djinn.textHandler.titleText(djinn);
			djinn.theWorld.enemyShotList.get(0).onUpdate(djinn);
			
			if (this.keyDown.isKeyDown() && djinn.getSystemTime()-this.lastkeyDown>120) {
				djinn.theWorld.enemyShotList.get(0).posY = (djinn.theWorld.enemyShotList.get(0).posY==562) ? 462 : djinn.theWorld.enemyShotList.get(0).posY+50;
				this.gameChoice = (this.gameChoice == 2) ? 0 : this.gameChoice+1;
			}
			
			if (this.keyUp.isKeyDown() && djinn.getSystemTime()-this.lastkeyUp>120) {
				djinn.theWorld.enemyShotList.get(0).posY = (djinn.theWorld.enemyShotList.get(0).posY==462) ? 562 : djinn.theWorld.enemyShotList.get(0).posY-50;
				this.gameChoice = (this.gameChoice == 0) ? 2 : this.gameChoice+1;
			}
			
			if (this.keyReturn.isKeyDown()) {
				djinn.theWorld.enemyShotList.clear();
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

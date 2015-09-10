package djinn;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;

import java.util.ArrayList;

public class World {
	TrueTypeFont font;
	TrueTypeFont font2;
	
	public ArrayList<Entity> enemyShotList = new ArrayList<Entity>();
	public ArrayList<Entity> enemiesToBeRemoved = new ArrayList<Entity>();
	public ArrayList<Entity> enemyShotsToBeRemoved = new ArrayList<Entity>();
	public int initialNumEnemies;
	
	public boolean titleState;
	public boolean readyState;
	public boolean playState;
	public boolean pauseState;
	
	public Keybind keyReturn;
	public Keybind keyUp;
	public Keybind keyDown;
	public long lastkeyUp;
	public long lastkeyDown;
	
	public World(Djinn djinn){
		this.initialNumEnemies = djinn.EnemyList.size();
		
		this.titleState = true;
		
		this.keyReturn = new Keybind(Keyboard.KEY_RETURN, "Return");
		this.keyUp = new Keybind(Keyboard.KEY_UP, "Up");
		this.keyDown = new Keybind(Keyboard.KEY_DOWN, "Down");
	}
	
	public void run(Djinn djinn) {	
		Color.white.bind();

		if (this.titleState) {
			this.font = new TrueTypeFont(new Font("Futura", Font.BOLD, 30), true);
		
			font.drawString(djinn.displayWidth/2-200, djinn.displayHeight/2 - 400, "Welcome to...", Color.black);
			
			this.font2 = new TrueTypeFont(new Font("Futura", Font.BOLD, 48), true);
			font2.drawString(djinn.displayWidth/2-100, djinn.displayHeight/2 - 360, "Djinn", Color.black);
			
			font.drawString(djinn.displayWidth/2-200, djinn.displayHeight/2 - 200	, "Please Press Enter", Color.black);
			
			if (this.keyReturn.isKeyDown()) {
				this.titleState = false;
			}
		}
		else {
			this.playState = true;
			
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
				djinn.theWorld.enemyShotList.remove(shot);
			}
			if (!djinn.thePlayer.shotActive) {
				djinn.playerShot = null;
			}
			
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

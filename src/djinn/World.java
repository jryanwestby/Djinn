package djinn;

import org.lwjgl.input.Keyboard;
import java.util.ArrayList;

public class World {
	public ArrayList<Entity> enemyShotList = new ArrayList<Entity>();
	public ArrayList<Entity> enemiesToBeRemoved = new ArrayList<Entity>();
	public ArrayList<Entity> enemyShotsToBeRemoved = new ArrayList<Entity>();
	public int initialNumEnemies;
	
	public boolean pauseState;
	public boolean winState;
	public boolean titleState;
	public boolean readyState;
	public boolean playState;
	public boolean endState;
	
	public int pauseChoice;
	public int gameChoice;
	public int endChoice;
	
	public Keybind keyEsc;
	public Keybind keyR;
	public Keybind keyReturn;
	public Keybind keyUp;
	public Keybind keyDown;
	public long lastkeyUp;
	public long lastkeyDown;
	public long lastEnter;
	
	public World(Djinn djinn){
		this.initialNumEnemies = djinn.EnemyList.size();
		
		this.titleState = true;
		this.pauseChoice = 0;
		this.gameChoice = 0;
		this.endChoice = 0;
		
		this.keyEsc = new Keybind(Keyboard.KEY_ESCAPE, "Escape");
		this.keyR = new Keybind(Keyboard.KEY_R, "R");
		this.keyReturn = new Keybind(Keyboard.KEY_RETURN, "Return");
		this.keyUp = new Keybind(Keyboard.KEY_UP, "Up");
		this.keyDown = new Keybind(Keyboard.KEY_DOWN, "Down");
	}
	
	public void run(Djinn djinn) {	
		
		if (this.keyEsc.isKeyDown()) {
			djinn.theWorld.enemyShotList.clear();
			this.winState = false;
			this.titleState = false;
			this.playState = false;
			this.readyState = false;
			
			this.pauseState = true;
			this.pauseChoice = 0;
			djinn.menuSelector.posY = djinn.menuSelector.defaultPosY;
		}
		
		// Pause Screen
		if (this.pauseState) {

			djinn.menuSelector.onUpdate(djinn);
			djinn.textHandler.pauseText(djinn);
			
			if (this.keyDown.isKeyDown() && djinn.getSystemTime()-this.lastkeyDown>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==562) ? djinn.menuSelector.defaultPosY : djinn.menuSelector.posY+50;
				this.pauseChoice = (this.pauseChoice == 2) ? 0 : this.pauseChoice+1;
				this.lastkeyDown = djinn.getSystemTime();
			}
			
			if (this.keyUp.isKeyDown() && djinn.getSystemTime()-this.lastkeyUp>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==djinn.menuSelector.defaultPosY) ? 562 : djinn.menuSelector.posY-50;
				this.pauseChoice = (this.pauseChoice == 0) ? 2 : this.pauseChoice-1;
				this.lastkeyUp = djinn.getSystemTime();
			}
				
			if (this.keyReturn.isKeyDown() && this.pauseChoice == 0) {
				this.pauseState = false;
				this.readyState = true;
				this.lastEnter = djinn.getSystemTime();
				
			} else if (this.keyReturn.isKeyDown() && this.pauseChoice == 1) {
				this.pauseState = false;
				Djinn.initEntities();
				
				this.titleState = true;
				this.gameChoice = 0;
				djinn.menuSelector.posY = djinn.menuSelector.defaultPosY;
				
				this.lastEnter = djinn.getSystemTime();
				
			} else if (this.keyReturn.isKeyDown() && this.pauseChoice == 2) {
				Djinn.isRunning = false; // End Game
			}
		}
		
		// Win Screen
		this.winState = djinn.EnemyList.isEmpty();
		if (this.winState)	{
			
			this.readyState = false;
			this.playState = false;
			
			djinn.menuSelector.onUpdate(djinn);
			djinn.textHandler.winText(djinn);
			
			if (this.keyDown.isKeyDown() && djinn.getSystemTime()-this.lastkeyDown>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==512) ? 462 : djinn.menuSelector.posY+50;
				this.endChoice = (this.endChoice == 1) ? 0 : this.endChoice+1;
				this.lastkeyDown = djinn.getSystemTime();
			}
			
			if (this.keyUp.isKeyDown() && djinn.getSystemTime()-this.lastkeyUp>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==462) ? 512 : djinn.menuSelector.posY-50;
				this.endChoice = (this.endChoice == 0) ? 1 : this.endChoice-1;
				this.lastkeyUp = djinn.getSystemTime();
			}
				
			if (this.keyReturn.isKeyDown() && endChoice == 0) {
				this.winState = false;
				Djinn.initEntities();
				this.titleState = true; // Restart Game
				
				this.gameChoice = 0;
				djinn.menuSelector.posY = 462;
				
				this.lastEnter = djinn.getSystemTime();
				
			} else if (this.keyReturn.isKeyDown() && endChoice == 1) {
				Djinn.isRunning = false; // End Game
			}
		}
		
		// Title Screen
		if (this.titleState) {
			
			djinn.menuSelector.onUpdate(djinn);
			djinn.textHandler.titleText(djinn);
			
			if (this.keyDown.isKeyDown() && djinn.getSystemTime()-this.lastkeyDown>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==562) ? djinn.menuSelector.defaultPosY : djinn.menuSelector.posY+50;
				this.gameChoice = (this.gameChoice == 2) ? 0 : this.gameChoice+1;
				this.lastkeyDown = djinn.getSystemTime();
			}
			
			if (this.keyUp.isKeyDown() && djinn.getSystemTime()-this.lastkeyUp>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==djinn.menuSelector.defaultPosY) ? 562 : djinn.menuSelector.posY-50;
				this.gameChoice = (this.gameChoice == 0) ? 2 : this.gameChoice-1;
				this.lastkeyUp = djinn.getSystemTime();
			}
			
			if (this.keyReturn.isKeyDown() && djinn.getSystemTime()-this.lastEnter>200) {
				this.titleState = false;
				this.readyState = true;
				djinn.menuSelector.posY = djinn.menuSelector.defaultPosY;
				
				this.lastEnter = djinn.getSystemTime();
			}
		}
		
		
		if (this.readyState) {
			
			// R key begins game 
			if (this.keyR.isKeyDown()) {
				this.playState = true;
			}
			
			// Play Defender
			if (this.gameChoice == 0) {
								
				// Display start text				
				if (!this.playState) djinn.textHandler.initDefenderText(djinn);
				djinn.textHandler.drawDefenderText(djinn);
				
				// Update Entities
				djinn.thePlayer.onUpdate(djinn);
				djinn.theDivider.onUpdate(djinn);
				
				for (Entity enemyShot : this.enemyShotList) {
					enemyShot.onUpdate(djinn);
				}
				
				if (djinn.thePlayer.shotActive){
					djinn.playerShot.onUpdate(djinn);
				}
				
				for (Entity enemy : djinn.EnemyList) {
					enemy.onUpdate(djinn);
				}
				
				// Remove Any Entities
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
			
			// Play Tetronimoes
			else if (this.gameChoice == 1) {
				if (!this.playState) djinn.textHandler.initTetronText(djinn);
				djinn.textHandler.drawTetronText(djinn);
				
				djinn.blockHandler.onUpdate(djinn);
				djinn.theDivider.onUpdate(djinn);
			} 
			
			// Play Djinn
			else if (this.gameChoice == 2) {
				
				if (!this.playState) djinn.textHandler.initDjinnText(djinn);
				djinn.textHandler.drawDjinnText(djinn);
				
				// Update Entities
				djinn.blockHandler.onUpdate(djinn);
				
				djinn.thePlayer.onUpdate(djinn);
				djinn.theDivider.onUpdate(djinn);
				
				for (Entity enemyShot : this.enemyShotList) {
					enemyShot.onUpdate(djinn);
				}
				
				for (Entity enemy : djinn.EnemyList) {
					enemy.onUpdate(djinn);
				}
				
				if (djinn.thePlayer.shotActive){
					djinn.playerShot.onUpdate(djinn);
				} 
				
				// Remove Any Entities
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
		}
		
		
		// Game over screen
		if (this.endState) {
		
			djinn.menuSelector.onUpdate(djinn);			
			djinn.textHandler.endText(djinn);
			
			if (this.keyDown.isKeyDown() && djinn.getSystemTime()-this.lastkeyDown>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==512) ? djinn.menuSelector.defaultPosY : djinn.menuSelector.posY+50;
				this.endChoice = (this.endChoice == 1) ? 0 : this.endChoice+1;
				this.lastkeyDown = djinn.getSystemTime();
			}
			
			if (this.keyUp.isKeyDown() && djinn.getSystemTime()-this.lastkeyUp>300) {
				djinn.menuSelector.posY = (djinn.menuSelector.posY==djinn.menuSelector.defaultPosY) ? 512 : djinn.menuSelector.posY-50;
				this.endChoice = (this.endChoice == 0) ? 1 : this.endChoice-1;
				this.lastkeyUp = djinn.getSystemTime();
			}
				
			if (this.keyReturn.isKeyDown() && endChoice == 0) {
				this.endState = false;
				Djinn.initEntities();
				
				this.titleState = true; // Restart Game
				this.gameChoice = 0;
				djinn.menuSelector.posY = djinn.menuSelector.defaultPosY;
				
				this.lastEnter = djinn.getSystemTime();
				
			} else if (this.keyReturn.isKeyDown() && endChoice == 1) {
				Djinn.isRunning = false; // End Game
			}				
		}
	}
}
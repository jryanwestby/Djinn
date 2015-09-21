package djinn;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.lwjgl.input.Keyboard;

public class EntityBlockHandler {
	public float width;
	public float height;
	public float refPosX;
	public float tempRefPosX;
	public float refPosY;
	public float speed;
	public float motionX;
	public float motionY;
	
	public Gui gui;
	public Random random;
	
	public int numBlockTypes;
	public EntityBlockType currType;
	public EntityBlockType nextType;
	public int currentBlockRotation;
	public int tempBlockRotation;
	
	public boolean newBlockReady;
	public long lastBlock;
	
	public ArrayList<Rectangle> blockList = new ArrayList<Rectangle>();
	public HashMap<Integer, ArrayList<Rectangle>> blockHeightMap = new HashMap<Integer, ArrayList<Rectangle>>();
	public ArrayList<Rectangle> blocksToBeRemoved = new ArrayList<Rectangle>();
	public boolean removeRowReady;
	public int numBlocks;
	
	public Keybind keyW;
	public Keybind keyA;
	public Keybind keyS;
	public Keybind keyD;
	public Keybind keySpace;
	public long lastkeyW;
	public long lastkeyA;
	public long lastkeyS;
	public long lastkeyD;
	public long lastkeySpace;
	
	
	public EntityBlockHandler(Djinn djinn) {
		this.keyW = new Keybind(Keyboard.KEY_W, "Up");
		this.keyA = new Keybind(Keyboard.KEY_A, "Left");
		this.keyS = new Keybind(Keyboard.KEY_S, "Down");
		this.keyD = new Keybind(Keyboard.KEY_D, "Right");
		this.keySpace = new Keybind(Keyboard.KEY_SPACE, "Spacebar");
				
		this.width = 28F;
		this.height = 28F;
		this.refPosX = 0;
		this.refPosY = 0;
		this.speed = 1.0F;
		this.motionX = 0;
		this.motionY = 0;
		
		this.gui = new Gui();
		this.random = new Random();
		
		this.numBlockTypes = EntityBlockType.values().length;
		this.currType = EntityBlockType.values()[random.nextInt(numBlockTypes)];
		this.nextType = EntityBlockType.values()[random.nextInt(numBlockTypes)];
		this.currentBlockRotation = random.nextInt(4); 
		
		this.newBlockReady = true;
		
		this.removeRowReady = false;
		this.numBlocks = 0;
	}
	
	public void onUpdate(Djinn djinn) {
		this.addBlock(djinn, this.currentBlockRotation);
		this.moveEntity(djinn, this.motionX, this.motionY);
		this.setBounds();
		this.handleInput(djinn);
		this.handleCollisions(djinn);
		this.doRender(djinn);
	}

	public void addBlock(Djinn djinn, int rotation) {
		if(this.newBlockReady){
			
			// Game Over if blocks are added too fast, when blocks have filled screen
			if (djinn.getSystemTime()-this.lastBlock<20) { 
				djinn.theWorld.playState = false;
				djinn.theWorld.readyState = false;
				
				djinn.theWorld.endState = true;
				djinn.theWorld.endChoice = 0;
				djinn.menuSelector.posY = djinn.menuSelector.defaultPosY;
			}

			this.currType = this.nextType;
			this.nextType = EntityBlockType.values()[random.nextInt(numBlockTypes)];
			
			this.refPosX = djinn.displayWidth/2-this.width*2;
			this.refPosY = djinn.displayHeight-this.height;
			
			for (int col = 0; col < 4; col++) {
				for (int row = 0; row < 4; row++) {
					if(currType.isTile(col, row, rotation)) {
						blockList.add(new Rectangle((int)this.refPosX+(int)this.width*col,(int)this.refPosY-(int)this.height*row,(int)this.width,(int)this.height));
					}
				}
			}
						
			this.numBlocks = blockList.size();
			this.newBlockReady = false;
			this.lastBlock = djinn.getSystemTime();
		}
	}

	public void moveEntity(Djinn djinn, float mx, float my) {
		this.refPosX += mx;
		this.refPosY += my;
	}

	private void setBounds() {		
		int tileNumber = this.numBlocks-4;
		for (int col = 0; col < 4; col++) {
			for (int row = 0; row < 4; row++) {
				if(currType.isTile(col, row, this.currentBlockRotation)) {
					blockList.get(tileNumber).x = (int)this.refPosX+(int)this.width*col;
					blockList.get(tileNumber).y = (int)this.refPosY-(int)this.height*row;
					blockList.get(tileNumber).width = (int) this.width; 
					blockList.get(tileNumber).height = (int) this.height;
					tileNumber++;
				}
			}
		}
	}

	private void handleInput(Djinn djinn) {
		if (!djinn.theWorld.playState) {
			this.motionY = 0;
			return;
		}
		
		if (djinn.theWorld.keyR.isKeyDown()) {
			if (djinn.theWorld.gameChoice==1) {
				this.motionY = -this.speed*2;
			} else this.motionY = -this.speed;
		}
		this.motionX = 0;
		
		// W and S rotate block
		if (this.keyW.isKeyDown() && djinn.getSystemTime()-this.lastkeyW>150) {
			this.tempBlockRotation = this.currentBlockRotation;
			this.currentBlockRotation = (this.currentBlockRotation == 0) ? 3 : this.currentBlockRotation-1;
			this.lastkeyW = djinn.getSystemTime();
		}
		
		// A and D move block left and right
		if (this.keyA.isKeyDown() && djinn.getSystemTime()-this.lastkeyA>100) {
			this.tempRefPosX = this.refPosX;
			this.refPosX -= this.width;
			this.lastkeyA = djinn.getSystemTime();
		}
		
		if (this.keyS.isKeyDown() && djinn.getSystemTime()-this.lastkeyS>150) {
			this.tempBlockRotation = this.currentBlockRotation;
			this.currentBlockRotation = (this.currentBlockRotation == 3) ? 0 : this.currentBlockRotation+1;
			this.lastkeyS = djinn.getSystemTime();
		}
		
		if (this.keyD.isKeyDown() && djinn.getSystemTime()-this.lastkeyD>100) {
			this.tempRefPosX = this.refPosX;
			this.refPosX += this.width;
			this.lastkeyD = djinn.getSystemTime();
		}
		
		if (this.keySpace.isKeyDown() && djinn.getSystemTime()-this.lastkeySpace>600) {
			this.motionY = -this.speed*14;		
			this.lastkeySpace = djinn.getSystemTime();
		}
	}

	private void handleCollisions(Djinn djinn) {
		
		boolean collisionWithDivider = checkCollisionWithDivider(djinn);
				
		if (collisionWithDivider) {
			this.setAndRespawn(djinn);
		}
		
		else {
			boolean collisionWithBlock = checkCollisionWithBlock(djinn);
			boolean collisionWithWall = checkCollisionWithWall(djinn);
			
			if (collisionWithWall || collisionWithBlock) {
				if (djinn.getSystemTime()-this.lastkeyA<30 || djinn.getSystemTime()-this.lastkeyD<30) { 
					this.refPosX = this.tempRefPosX;
					this.lastkeyA = djinn.getSystemTime();
					this.lastkeyD = djinn.getSystemTime();
					this.setBounds();
					
					// Check one more time to ensure block sets itself correctly if it encounters a block in the Y direction
					if (this.checkCollisionWithBlock(djinn)) {
						this.setAndRespawn(djinn);
					}
				}
				else if (djinn.getSystemTime()-this.lastkeyW<30 || djinn.getSystemTime()-this.lastkeyS<30) { 
					this.currentBlockRotation = this.tempBlockRotation;
					this.lastkeyW = djinn.getSystemTime();
					this.lastkeyS = djinn.getSystemTime();
					this.setBounds();
					
					//	Check one more time to ensure block sets itself correctly if it encounters a block in the Y direction
					if (this.checkCollisionWithBlock(djinn)) {
						this.setAndRespawn(djinn);
					}
				} else{
					this.setAndRespawn(djinn);
				}

			}
		}
	}
	
	private boolean checkCollisionWithDivider(Djinn djinn) {
		for (int currentBlock = this.numBlocks-4; currentBlock < blockList.size(); currentBlock++) {
			if (blockList.get(currentBlock).intersects(djinn.theDivider.rect)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean checkCollisionWithBlock(Djinn djinn) {
		for (int currentBlock = this.numBlocks-4; currentBlock < blockList.size(); currentBlock++) {
			for (int blockIndex = 0; blockIndex < blockList.size()-4; blockIndex++) {
				if (blockList.get(currentBlock).intersects(blockList.get(blockIndex))) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean checkCollisionWithWall(Djinn djinn) {
		for (int currentBlock = this.numBlocks-4; currentBlock < blockList.size(); currentBlock++) {
			if (blockList.get(currentBlock).x < 0 || blockList.get(currentBlock).x+this.width > djinn.displayWidth) {
				return true;
			}
		}
		return false;
	}
	
	private void setAndRespawn(Djinn djinn) {
		while (this.checkCollisionWithDivider(djinn) || this.checkCollisionWithBlock(djinn)) {
			this.refPosY += 1.0F;
			this.setBounds();
		}
		
		this.checkRows(djinn);
		if (this.removeRowReady) this.removeRow(djinn); 
		
		this.newBlockReady = true;
		this.currentBlockRotation = random.nextInt(4); 
		this.motionY = -this.speed;
	}
	
	private void checkRows(Djinn djinn) {
		for (int currentBlock = numBlocks-4; currentBlock < blockList.size(); currentBlock++) {
			int currentRowHeight = blockList.get(currentBlock).y;
			
			if (blockHeightMap.containsKey(currentRowHeight)) {
				blockHeightMap.get(currentRowHeight).add(blockList.get(currentBlock));
				
				if (blockHeightMap.get(currentRowHeight).size() == 16) {
					blocksToBeRemoved.addAll(blockHeightMap.get(currentRowHeight));
					this.removeRowReady = true;
				}
			} else {
				blockHeightMap.put(currentRowHeight, new ArrayList<Rectangle>());
				blockHeightMap.get(currentRowHeight).add(blockList.get(currentBlock));
			}
		}
	}
	
	private void removeRow(Djinn djinn) {
		blockList.removeAll(blocksToBeRemoved);
		
		for (int remainingBlock = 0; remainingBlock < blockList.size(); remainingBlock++) {
			if (blockList.get(remainingBlock).y > blocksToBeRemoved.get(0).y) {
				blockList.get(remainingBlock).y -= this.height;
			}
		}
		blocksToBeRemoved.clear();
		
		djinn.textHandler.tetronLevel += 1;
		if (djinn.theWorld.gameChoice==1) this.speed += 0.2;
		else if (djinn.theWorld.gameChoice==2) this.speed += 0.1; // Speed up Tetron
		djinn.thePlayer.width -= 6.0F; // Add a life
		
		for (int i = 0; i < 5; i++) {
			int randEnemyToRemove = getRandRange(0, djinn.EnemyList.size());
			djinn.theWorld.enemiesToBeRemoved.add(djinn.EnemyList.get(randEnemyToRemove));
		}
		
		this.removeRowReady = false;
		this.resetHeightMap(djinn);
	}
	
	private void resetHeightMap(Djinn djinn) {
		blockHeightMap.clear();
		
		for (int currentBlock = 0; currentBlock < blockList.size(); currentBlock++) {
			int currentRowHeight = blockList.get(currentBlock).y;
			
			if (blockHeightMap.containsKey(currentRowHeight)) {
				blockHeightMap.get(currentRowHeight).add(blockList.get(currentBlock));

			} else {
				blockHeightMap.put(currentRowHeight, new ArrayList<Rectangle>());
				blockHeightMap.get(currentRowHeight).add(blockList.get(currentBlock));
			}
		}
		
		this.numBlocks = blockList.size();
	}
	
	private void doRender(Djinn djinn) {
		for (int tileNumber = 0; tileNumber < blockList.size(); tileNumber++) {
			gui.drawRect(blockList.get(tileNumber).x, blockList.get(tileNumber).y, this.width, this.height, 0xFF000000);
		}		
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}
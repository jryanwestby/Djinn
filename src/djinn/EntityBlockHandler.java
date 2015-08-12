package djinn;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

public class EntityBlockHandler {
	public float width;
	public float height;
	public float refPosX;
	public float refPosY;
	public float speed;
	public float motionX;
	public float motionY;
	public boolean dropBlock;
	
	public Gui gui;
	public Random random;
	
	public int numBlockTypes;
	public EntityBlockType currType;
	public EntityBlockType nextType;
	public int blockRotation;
	
	public boolean newBlockReady;
	public ArrayList<Rectangle> blockList = new ArrayList<Rectangle>();
	public int numBlocks;
	
	public Keybind keyA;
	public Keybind keyD;
	public Keybind keySpace;
	public long lastkeyA;
	public long lastkeyD;
		
	public EntityBlockHandler(Djinn djinn) {
		this.keyA = new Keybind(Keyboard.KEY_A, "Left");
		this.keyD = new Keybind(Keyboard.KEY_D, "Right");
		this.keySpace = new Keybind(Keyboard.KEY_SPACE, "Spacebar");
				
		this.width = 28F;
		this.height = 28F;
		this.refPosX = 0;
		this.refPosY = 0;
		this.speed = 12.0F;
		this.motionX = 0;
		this.motionY = 0;
		this.dropBlock = false;
		
		this.gui = new Gui();
		this.random = new Random();
		
		this.numBlockTypes = EntityBlockType.values().length;
		this.currType = EntityBlockType.values()[random.nextInt(numBlockTypes)];
		this.nextType = EntityBlockType.values()[random.nextInt(numBlockTypes)];
		this.blockRotation = random.nextInt(4); 
		
		this.newBlockReady = true;
		this.numBlocks = 0;
	}
	
	public void onUpdate(Djinn djinn) {
		this.addBlock(djinn, blockRotation);
		this.moveEntity(djinn, this.motionX, this.motionY);
		this.setBounds();
		this.doRender(djinn);
		this.handleInput(djinn);
		this.handleCollisions(djinn);
	}
	
	public void addBlock(Djinn djinn, int rotation) {
		if(this.newBlockReady){
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
			this.numBlocks += 4;
			this.newBlockReady = false;
			this.dropBlock = false;
		}
	}

	private void moveEntity(Djinn djinn, float mx, float my) {
		this.refPosX += mx;
		this.refPosY += my;
	}

	private void setBounds() {		
		int tileNumber = this.numBlocks-4;
		for (int col = 0; col < 4; col++) {
			for (int row = 0; row < 4; row++) {
				if(currType.isTile(col, row, blockRotation)) {
					blockList.get(tileNumber).x = (int)this.refPosX+(int)this.width*col;
					blockList.get(tileNumber).y = (int)this.refPosY-(int)this.height*row;
					blockList.get(tileNumber).width = (int) this.width; 
					blockList.get(tileNumber).height = (int) this.height;
					tileNumber++;
				}
			}
		}
	}

	private void doRender(Djinn djinn) {
		for (int tileNumber = 0; tileNumber < blockList.size(); tileNumber++) {
			gui.drawRect(blockList.get(tileNumber).x, blockList.get(tileNumber).y, this.width, this.height, 0xFF000000);
		}		
	}

	private void handleInput(Djinn djinn) {
		if (this.dropBlock) {
			this.motionX = 0;
		} else {
			if (djinn.gameStart) this.motionY = -this.speed/5;
			this.motionX = 0;
			
			if (this.keyA.isKeyDown() && djinn.getSystemTime()-this.lastkeyA>200) {
				this.refPosX -= this.width;
				this.lastkeyA = djinn.getSystemTime();
			}
			
			if (this.keyD.isKeyDown() && djinn.getSystemTime()-this.lastkeyD>200) {
				this.refPosX += this.width;
				this.lastkeyD = djinn.getSystemTime();
			}
		}
	}
	
	private void handleCollisions(Djinn djinn) {
		
		boolean collisionWithDivider = false;
		collisionWithDividerCheck:
			for (int currentBlock = this.numBlocks-4; currentBlock < blockList.size(); currentBlock++) {
				if (blockList.get(currentBlock).intersects(djinn.theDivider.rect)) {
					collisionWithDivider = true;
					break collisionWithDividerCheck;
				}
			}
		
		boolean collisionWithBlock = false;
		collisionWithBlockCheck:
			for (int currentBlock = this.numBlocks-4; currentBlock < blockList.size(); currentBlock++) {
				for (int blockIndex = 0; blockIndex < blockList.size()-4; blockIndex++) {
					if (blockList.get(currentBlock).intersects(blockList.get(blockIndex))) {
						collisionWithBlock = true;
						break collisionWithBlockCheck;
					}
				}
			}
		// TODO Need to stop block before it collides, needs to run one more update with a new refPosY
		
		if (collisionWithBlock || collisionWithDivider) {
			this.moveEntity(djinn, this.width, this.height);
			this.newBlockReady = true;
			this.blockRotation = random.nextInt(4); 
			collisionWithDivider = false;
		}
	}
}
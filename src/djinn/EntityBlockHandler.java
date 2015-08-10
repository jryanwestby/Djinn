package djinn;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

public class EntityBlockHandler {
	public float refPosX;
	public float refPosY;
	public float motionX;
	public float motionY;
	public float width;
	public float height;
	public float speed;
	public Gui gui;
	public Random random;
	
	public int numBlockTypes;
	public EntityBlockType currType;
	public EntityBlockType nextType;
	public int blockRotation;
	
	public boolean newBlockReady;
	public ArrayList<Rectangle> tileList = new ArrayList<Rectangle>();
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
		this.motionY = -this.speed/8F;
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
						tileList.add(new Rectangle((int)this.refPosX+(int)this.width*col,(int)this.refPosY-(int)this.height*row,(int)this.width,(int)this.height));
					}
				}
			}
			this.numBlocks += 4;
			this.newBlockReady = false;
			this.motionY = -this.speed/8F;
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
					tileList.get(tileNumber).x = (int)this.refPosX+(int)this.width*col;
					tileList.get(tileNumber).y = (int)this.refPosY-(int)this.height*row;
					tileList.get(tileNumber).width = (int) this.width; 
					tileList.get(tileNumber).height = (int) this.height;
					tileNumber++;
				}
			}
		}
	}

	private void doRender(Djinn djinn) {
		for (int tileNumber = 0; tileNumber < tileList.size(); tileNumber++) {
			gui.drawRect(tileList.get(tileNumber).x, tileList.get(tileNumber).y, this.width, this.height, 0xFF000000);
		}		
	}

	private void handleInput(Djinn djinn) {
		this.motionX = 0;
		
		if (this.keyA.isKeyDown() && djinn.getSystemTime()-this.lastkeyA>200) {
			this.refPosX -= this.width;
			this.lastkeyA = djinn.getSystemTime();
		}
		
		if (this.keyD.isKeyDown() && djinn.getSystemTime()-this.lastkeyD>200) {
			this.refPosX += this.width;
			this.lastkeyD = djinn.getSystemTime();
		}
		
//		if (this.keySpace.isKeyDown()) {
//			this.motionY = -this.speed/2;
//		} else {
//			this.motionY = -this.speed/10;
//		}
	}
	
	private void handleCollisions(Djinn djinn) {
		boolean collisionWithDivider = false;
		for (int tileNumber = this.numBlocks-4; tileNumber < tileList.size(); tileNumber++) {
			if (tileList.get(tileNumber).intersects(djinn.theDivider.rect)) {
				collisionWithDivider = true;
			}
		}
		
		boolean collisionWithBlock = false;
		for (int tileNumber = this.numBlocks-4; tileNumber < tileList.size(); tileNumber++) {
			for (int tileListNum = 0; tileListNum < tileList.size()-4; tileListNum++) {
				if (tileList.get(tileNumber).intersects(tileList.get(tileListNum))) {
					collisionWithBlock = true;
				}
			}
		}
		
		if (collisionWithDivider || collisionWithBlock) {
			this.motionX = 0;
			this.motionY = 0;
			this.newBlockReady = true;
			this.blockRotation = random.nextInt(4); 
			collisionWithDivider = false;
		}
	}
}

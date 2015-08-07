package djinn;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Random;

public class EntityBlockHandler {
	private static final int NUM_BLOCK_TYPES = EntityBlockType.values().length;
	public float refPosX;
	public float refPosY;
	public float motionX;
	public float motionY;
	public float width;
	public float height;
	public float speed;
	public Gui gui;
	public Random random;
	
	public EntityBlockType currType;
	public EntityBlockType nextType;
	public int rotation;
	
	public boolean newBlockReady;
	public ArrayList<Rectangle> block = new ArrayList<Rectangle>();
		
	public EntityBlockHandler(Djinn djinn) {
		this.refPosX = 0;
		this.refPosY = 0;
		this.motionX = 0;
		this.motionY = 0;
		this.width = 14F;
		this.height = 14F;
		this.speed = 12.0F;
		this.gui = new Gui();
		this.random = new Random();
		
		this.currType = EntityBlockType.values()[random.nextInt(NUM_BLOCK_TYPES)];
		this.nextType = EntityBlockType.values()[random.nextInt(NUM_BLOCK_TYPES)];
		
		this.newBlockReady = false;
	}
	
	public void onUpdate(Djinn djinn) {
		this.moveEntity(djinn, this.motionX, this.motionY);
		this.setBounds();
		this.doRender(djinn);
		this.handleInput(djinn);
		this.handleCollisions(djinn);
		this.addBlock(djinn, rotation);
	}

	private void moveEntity(Djinn djinn, float mx, float my) {
		this.refPosX += mx;
		this.refPosY += my;
	}

	private void setBounds() {
		int tileNumber = 0;
		for (int col = 0; col < 4; col++) {
			for (int row = 0; row < 4; row++) {
				if(currType.isTile(col, row, rotation)) {
					block.get(tileNumber).x;
					block.get(tileNumber).y;
					block.get(tileNumber).width; 
					block.get(tileNumber).height; 
				}
			}
		}
	}

	private void doRender(Djinn djinn) {
		
	}

	private void handleInput(Djinn djinn) {
		
	}
	
	private void handleCollisions(Djinn djinn) {
		
	}
	
	public void addBlock(Djinn djinn, int rotation) {
		if(newBlockReady){
			this.rotation = random.nextInt(4); // Explain 4
			
			for (int col = 0; col < 4; col++) {
				for (int row = 0; row < 4; row++) {
					if(currType.isTile(col, row, rotation)) {
						block.add(new Rectangle());
					}
				}
			}
		}		
	}
}

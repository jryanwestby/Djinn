package djinn;

import java.awt.Rectangle;

public class Entity {
	public float posX;
	public float posY;
	public float motionX;
	public float motionY;
	public float width;
	public float height;
	public float speed;
	public Rectangle rect;
	public Gui gui;
	
	public Entity(Djinn djinn) {
		this.posX = 0;
		this.posY = 0;
		this.motionX = 0;
		this.motionY = 0;
		this.width = 14F;
		this.height = 14F;
		this.speed = 12.0F;
		this.rect = new Rectangle();
		this.setBounds();
		this.gui = new Gui();
	}

	public void onUpdate(Djinn djinn) {
		this.setBounds();
		this.moveEntity(djinn, this.motionX, this.motionY);
		this.doRender(djinn);
	}
	
	public void setBounds() {
		// Tells Java the dimensions and coords of the Rect
		// Can then use it to compare to other rects and check for collision
		this.rect.x = (int) this.posX;
		this.rect.y = (int) this.posY;
		this.rect.width = (int) this.width;
		this.rect.height = (int) this.height;
	}
	
	public void moveEntity(Djinn djinn, float mx, float my) {
		this.posX += mx;
		this.posY += my;
	}
	
	public void doRender(Djinn djinn) {
		gui.drawRect(this.posX, this.posY, this.width, this.height, 0xFF000000);
	}
}

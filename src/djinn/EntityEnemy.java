package djinn;

import org.lwjgl.input.Keyboard;

public class EntityEnemy extends Entity {
	
	public Keybind keyLeft;
	public Keybind keyRight;
	public int maxDistLeft;
	public int maxDistRight;
	public long lastCollision;
	public boolean alienIsShot;
	
	public EntityEnemy(Djinn djinn, float x, float y) {
		super(djinn);
		
		this.posX = x;
		this.posY = y;
		this.width *= (float) 1.8F;
		this.maxDistLeft = (int) (width*2);
		this.maxDistRight = (int) (width*2);
		this.lastCollision = 0;
		
		this.keyLeft = new Keybind(Keyboard.KEY_A, "Left");
		this.keyRight = new Keybind(Keyboard.KEY_D, "Right");
	}

	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);		
		handleInput(djinn);
		handleCollisions(djinn);
	}

	private void handleInput(Djinn djinn) {
		this.motionX = 0;
		this.motionY = 0;
		
		if (this.keyLeft.isKeyDown() && this.maxDistLeft != 0) {
			this.motionX = -this.speed/16;
			this.maxDistLeft -= 1;
			this.maxDistRight += 1;
		}
		if (this.keyRight.isKeyDown() && this.maxDistRight != 0) {
			this.motionX = this.speed/16;
			this.maxDistRight -= 1;
			this.maxDistLeft += 1;
		}
	}
	
	private void handleCollisions(Djinn djinn) {
	
		boolean collision = this.rect.intersects(djinn.theBall.rect);
		
		if (collision && djinn.getSystemTime()-this.lastCollision>1000) {
			djinn.theBall.bounceY();
			djinn.theWorld.entitiesToBeRemoved.add(this);
			this.lastCollision = djinn.getSystemTime();
		}
	}
	
	@Override
	public void moveEntity(Djinn djinn, float mx, float my) {
		if (this.maxDistLeft <= 0) {
			return; 
		}
		if (this.maxDistRight <= 0) {
			return;
		}
		super.moveEntity(djinn, mx, my);
	}
}

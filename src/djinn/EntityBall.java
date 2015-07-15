package djinn;

import org.lwjgl.input.Keyboard;

public class EntityBall extends Entity{
	public Keybind keySpace;
	public Keybind keyLeft;
	public Keybind keyRight;
	public long lastCollision;
	public boolean gameStart;
	
	public EntityBall(Djinn djinn) {
		super(djinn);
		this.speed /= 2F;
		this.posX = djinn.thePlayer.posX + (djinn.thePlayer.width/2);
		this.posY = djinn.thePlayer.posY - this.height; // sets ball at top half of screen
		this.lastCollision = 0;
		this.gameStart = false;
		
		this.keySpace = new Keybind(Keyboard.KEY_SPACE, "Spacebar");
		this.keyLeft = new Keybind(Keyboard.KEY_LEFT, "Left");
		this.keyRight = new Keybind(Keyboard.KEY_RIGHT, "Right");
	}
	
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);
		handleCollisions(djinn);
		handleInput(djinn);
	}

	private void handleCollisions(Djinn djinn) {
		if (this.posX<0) this.bounceX();
		if (this.posX+this.width>djinn.displayWidth) this.bounceX();
		if (this.posY<0) this.bounceY();
		if (this.posY+this.height>djinn.displayHeight) this.bounceY();
		
		boolean collisionWithPlayer = this.rect.intersects(djinn.thePlayer.rect);		
		if (collisionWithPlayer && djinn.getSystemTime()-this.lastCollision>1000) {
			this.bounceY();
			
//			if (this.posX+this.width/2 > djinn.thePlayer.posX+djinn.thePlayer.width/3 &&
//				this.posX+this.width/2 < djinn.thePlayer.posX+djinn.thePlayer.width*2/3) {
//				this.speed /= 1.5F;
//				System.out.println(1 + " Speed:" + this.speed);
//			}
//			else {
//				this.speed *= 1.5F;
//				System.out.println(0 + " Speed:" + this.speed);
//			}
			this.lastCollision = djinn.getSystemTime();
		}
		
		boolean collisionWithDivider = this.rect.intersects(djinn.theDivider.rect);
		if (collisionWithDivider) {
			this.gameStart = false;
			djinn.thePlayer.width -= 2;	
			this.posX = djinn.thePlayer.posX + (djinn.thePlayer.width/2);
			this.posY = djinn.thePlayer.posY - this.height;
		}
	}
	
	void bounceX() {
		this.motionX = -this.motionX;
	}
	
	void bounceY() {
		this.motionY = -this.motionY;
	}
	
	private void handleInput(Djinn djinn) {
		if (gameStart) return;
		
		if (!gameStart){
			this.motionX = 0;
			this.motionY = 0;
			this.speed = 6.0F;
			
			if (this.keyLeft.isKeyDown() || this.keyRight.isKeyDown()) this.posX = djinn.thePlayer.posX + (djinn.thePlayer.width/2);
		}

		if (this.keySpace.isKeyDown()) {
			gameStart = true;
			this.motionX = this.speed;
			this.motionY = -this.speed;
		} 
	}

	@Override
	public void moveEntity(Djinn djinn, float mx, float my) {
		if (this.posX<0 && this.motionX<0) return; // If ball  is offscreen, then prevent moveEntity from running
		if (this.posX+this.width>djinn.displayWidth && this.motionX>0) return;
		
		super.moveEntity(djinn, mx, my);
	}
}

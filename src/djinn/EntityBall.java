package djinn;

public class EntityBall extends Entity{
	public long lastCollision;
	
	public EntityBall(Djinn djinn) {
		super(djinn);
		this.speed /= 2;
		this.posX = (djinn.displayWidth/2) - (this.width/2);
		this.posY = (djinn.displayHeight/4) - (this.height/2); // sets ball at top half of screen
		this.motionX = this.speed;
		this.motionY = this.speed; // set ball in motion
		this.lastCollision = 0;
	}
	
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);
		handleCollisions(djinn);
	}

	private void handleCollisions(Djinn djinn) {
		if (this.posX<0) this.bounceX();
		if (this.posX+this.width>djinn.displayWidth) this.bounceX();
		if (this.posY<0) this.bounceY();
		if (this.posY+this.height>djinn.displayHeight) this.bounceY();
		
		boolean collision = this.rect.intersects(djinn.thePlayer.rect) || this.rect.intersects(djinn.theDivider.rect);
		
		if (collision && djinn.getSystemTime()-this.lastCollision>100) {
			this.bounceY();
			this.lastCollision = djinn.getSystemTime();
		}
	}

	void bounceX() {
		this.motionX = -this.motionX;
	}
	
	void bounceY() {
		this.motionY = -this.motionY;
	}
}

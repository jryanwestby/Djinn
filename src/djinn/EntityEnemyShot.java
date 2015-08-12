package djinn;

public class EntityEnemyShot extends Entity {
	public EntityEnemyShot(Djinn djinn, float x, float y) {
		super(djinn);
		
		this.motionY = (float) this.speed/4F;
		this.posX = x;
		this.posY = y;
	}
	
	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);		
		handleCollisions(djinn);	
	}
	
	private void handleCollisions(Djinn djinn) {
		
		boolean collisionWithDivider = this.rect.intersects(djinn.theDivider.rect);
		if (collisionWithDivider) {
			djinn.theWorld.entitiesToBeRemoved.add(this);
		}
		
		boolean collisionWithPlayer = this.rect.intersects(djinn.thePlayer.rect);
		if (collisionWithPlayer) {
			djinn.gameReset();
			djinn.theWorld.entitiesToBeRemoved.add(this);
			djinn.blockHandler.dropBlock = true;
		}
		
//		boolean collisionWithBall = this.rect.intersects(djinn.theBall.rect);
//		if (collisionWithBall) {
//			djinn.theWorld.entitiesToBeRemoved.add(this);
//		}
	}
	

}

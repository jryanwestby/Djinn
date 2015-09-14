package djinn;

public class EntityEnemyShot extends Entity {
	public EntityEnemyShot(Djinn djinn, float x, float y) {
		super(djinn);
		
		if (djinn.theWorld.gameChoice == 0) {
			this.motionY = (float) this.speed/2F;
		} else {
			this.motionY = (float) this.speed/4F;
		}
		
		this.posX = x;
		this.posY = y;
	}
	
	@Override
	public void onUpdate(Djinn djinn) {
		if (!djinn.theWorld.playState) djinn.theWorld.enemyShotsToBeRemoved.add(this);
		super.onUpdate(djinn);		
		handleCollisions(djinn);
	}
	
	private void handleCollisions(Djinn djinn) {
		
		boolean collisionWithDivider = this.rect.intersects(djinn.theDivider.rect);
		if (collisionWithDivider) {
			djinn.theWorld.enemyShotsToBeRemoved.add(this);
		}
		
		boolean collisionWithPlayer = this.rect.intersects(djinn.thePlayer.rect);
		if (collisionWithPlayer) {
			while (!djinn.blockHandler.newBlockReady) {
				djinn.blockHandler.onUpdate(djinn);
			}
			djinn.thePlayer.width += 6.0F;
			djinn.theWorld.playState = false;
			djinn.theWorld.enemyShotsToBeRemoved.add(this);
		}
	}
}

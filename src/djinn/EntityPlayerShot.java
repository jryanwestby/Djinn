package djinn;

public class EntityPlayerShot extends Entity{
	
	public EntityPlayerShot(Djinn djinn, float x, float y) {
		super(djinn);

		this.motionY = -(float) this.speed;
		this.posX = x;
		this.posY = y;
	}
	
	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);
		handleCollisions(djinn);
		}
	
	private void handleCollisions(Djinn djinn) {
		// if playershot runs offscreen
		// turn off playershot flag in entityplayer
	}
}
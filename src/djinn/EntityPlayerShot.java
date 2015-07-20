package djinn;

public class EntityPlayerShot extends Entity{
	
	public EntityPlayerShot(Djinn djinn, float x, float y) {
		super(djinn);

		this.motionY = -(float) this.speed/8;
		this.posX = x;
		this.posY = y;
	}
}
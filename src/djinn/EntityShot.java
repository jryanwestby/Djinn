package djinn;

public class EntityShot extends Entity {
	
	public EntityShot(Djinn djinn, float x, float y) {
		super(djinn);
		this.motionY = this.speed;
		this.posX = x;
		this.posY = y;
	}
}

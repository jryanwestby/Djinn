package djinn;

public class EntityShot extends Entity {
	
	boolean shotReady = false;
	
	public EntityShot(Djinn djinn, float x, float y) {
		super(djinn);
		
		this.motionY = this.speed;
		this.posX = x;
		this.posY = y;
	}
	
	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);		
		
		
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}

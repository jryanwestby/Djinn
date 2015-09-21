package djinn;

public class EntitySelector extends Entity {
	public int defaultPosY;
	
	public EntitySelector(Djinn djinn, float x, float y) {
		super(djinn);
		
		this.posX = x;
		this.posY = y;
		this.defaultPosY = 462;
	}
}

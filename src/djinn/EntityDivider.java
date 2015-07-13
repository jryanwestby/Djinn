package djinn;

public class EntityDivider extends Entity {

	public EntityDivider(Djinn defendris) {
		super(defendris);
		this.posY = (defendris.displayHeight/2) - (this.height/2);
		this.width = defendris.displayWidth;
	}
}

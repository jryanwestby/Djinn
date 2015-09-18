package djinn;

public class EntityDivider extends Entity {

	public EntityDivider(Djinn djinn) {
		super(djinn);
		this.posY = (djinn.displayHeight/2) - (this.height/2);
		this.width = djinn.displayWidth;
	}
}

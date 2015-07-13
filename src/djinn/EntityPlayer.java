package djinn;

import org.lwjgl.input.Keyboard;

public class EntityPlayer extends Entity {
	public Keybind keyLeft;
	public Keybind keyRight;
	
	public EntityPlayer(Djinn djinn) {
		super(djinn);
		this.posX = 16F;
		this.posY = (djinn.displayHeight/2) - (this.height/2) - 20F;
		this.width *= 5F;
		
		this.keyLeft = new Keybind(Keyboard.KEY_LEFT, "Left");
		this.keyRight = new Keybind(Keyboard.KEY_RIGHT, "Right");
	}
	
	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);
		handleInput(djinn);
	}

	private void handleInput(Djinn djinn) {
		this.motionX = 0;
		this.motionY = 0;
		
		if (this.keyLeft.isKeyDown()) this.motionX = -this.speed;
		if (this.keyRight.isKeyDown()) this.motionX = this.speed;
	}
	
	@Override
	public void moveEntity(Djinn djinn, float mx, float my) {
		if (this.posX<0 && this.motionX<0) return; // If player is offscreen, then prevent moveEntity from running
		if (this.posX+this.width>djinn.displayWidth && this.motionX>0) return;
		super.moveEntity(djinn, mx, my);
	}
}

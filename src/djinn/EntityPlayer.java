package djinn;

import org.lwjgl.input.Keyboard;

public class EntityPlayer extends Entity {
	public Keybind keyLeft;
	public Keybind keyRight;
	public Keybind keySpace;
	public long lastShot;
	public boolean playerShotReady = false;
	
	public EntityPlayer(Djinn djinn) {
		super(djinn);
		this.width *= 4F;
		this.posX = (djinn.displayWidth/2);
		this.posY = (djinn.displayHeight/2) - (this.height/2) - 20F;
		this.speed *= 1.2F;
		
		this.keyLeft = new Keybind(Keyboard.KEY_LEFT, "Left");
		this.keyRight = new Keybind(Keyboard.KEY_RIGHT, "Right");
		this.keySpace = new Keybind(Keyboard.KEY_SPACE, "Spacebar");
		
		this.lastShot = 0;
	}
	
	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);
		handleInput(djinn);
		
		if (this.width < 8.0F) {
			Djinn.isRunning = false; // Game over
			//TODO Program actual game over notification
		}
	}

	private void handleInput(Djinn djinn) {
		if (!djinn.theWorld.gameStart) {
			this.posX = (djinn.displayWidth/2);
			this.posY = (djinn.displayHeight/2) - (this.height/2) - 20F;
			return;
		}

		this.motionX = 0;
		this.motionY = 0;
		
		if (this.keyLeft.isKeyDown()) this.motionX = -this.speed;
		if (this.keyRight.isKeyDown()) this.motionX = this.speed;

		if (this.keySpace.isKeyDown() && djinn.getSystemTime()-this.lastShot>600) {
			addPlayerShot(djinn);
		}
	}

	
	private void addPlayerShot(Djinn djinn) {
		djinn.playerShot = new EntityPlayerShot(djinn, djinn.thePlayer.posX+djinn.thePlayer.width/2, djinn.thePlayer.posY);
	}
	
	@Override
	public void moveEntity(Djinn djinn, float mx, float my) {
		if (this.posX<0 && this.motionX<0) return; // If player is offscreen, then prevent moveEntity from running
		if (this.posX+this.width>djinn.displayWidth && this.motionX>0) return;
		super.moveEntity(djinn, mx, my);
	}
}

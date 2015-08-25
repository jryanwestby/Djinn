package djinn;

import org.lwjgl.input.Keyboard;

public class EntityEnemy extends Entity {

	public Keybind keyA;
	public Keybind keyD;
	public Keybind keyLeft;
	public Keybind keyRight;
	public int maxDistLeft;
	public int maxDistRight;
	public int randEnemy;
	
	public EntityEnemy(Djinn djinn, float x, float y) {
		super(djinn);
		
		this.posX = x;
		this.posY = y;
		this.width *= (float) 2.5F;
		this.maxDistLeft = (int) (width*2);
		this.maxDistRight = (int) (width*2);
		
		this.keyA = new Keybind(Keyboard.KEY_A, "A");
		this.keyD = new Keybind(Keyboard.KEY_D, "D");
		this.keyLeft = new Keybind(Keyboard.KEY_LEFT, "Left");
		this.keyRight = new Keybind(Keyboard.KEY_RIGHT, "Right");
	}

	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);		
		handleInput(djinn);
		handleCollisions(djinn);
		addEnemyShot(djinn);
	}

	private void handleInput(Djinn djinn) {
		if (!djinn.gameStart) {
			this.motionX = 0;
			this.motionY = 0;
			return;
		}

		this.motionX = 0;
		this.motionY = 0;
		
		if (this.keyA.isKeyDown() || this.keyRight.isKeyDown()) {
			this.motionX = -this.speed/16;
			this.maxDistLeft -= 1;
			this.maxDistRight += 1;
		}
		if (this.keyD.isKeyDown() || this.keyLeft.isKeyDown()) {
			this.motionX = this.speed/16;
			this.maxDistRight -= 1;
			this.maxDistLeft += 1;
		}
	}
	
	public void addEnemyShot(Djinn djinn) {
		if (djinn.gameStart){
			if (djinn.theWorld.enemyShotsToBeAdded.size() > 0) djinn.theWorld.enemyShotsToBeAdded.remove(0); // This is a hack that limits the number of shots being added
			
			int randNum = getRandRange(0, djinn.theWorld.initialNumEnemies);
		 	randEnemy = getRandRange(0, djinn.EnemyList.size()); // Choose a random enemy from the EnemyList

			if (randNum == randEnemy && djinn.theWorld.entities.contains(djinn.EnemyList.get(randEnemy))) { // This logic limits the amount of shots being produced
				djinn.theWorld.enemyShotsToBeAdded.add(new EntityEnemyShot(djinn, djinn.EnemyList.get(randEnemy).posX, djinn.EnemyList.get(randEnemy).posY)); 			// Add the initialized shot to the entities ArrayList
			}
		}
	}
	
	private void handleCollisions(Djinn djinn) {	
		if (djinn.theWorld.entities.contains(djinn.playerShot)) {
			boolean collisionWithPlayerShot = this.rect.intersects(djinn.playerShot.rect);
			if (collisionWithPlayerShot) {
				djinn.theWorld.entitiesToBeRemoved.add(djinn.playerShot);
				djinn.theWorld.entitiesToBeRemoved.add(this);
			}
		}
	}

	@Override
	public void moveEntity(Djinn djinn, float mx, float my) {
		if (this.maxDistLeft <= 0) {
			return; 
		}
		if (this.maxDistRight <= 0) {
			return;
		}
		super.moveEntity(djinn, mx, my);
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}

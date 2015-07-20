package djinn;

import org.lwjgl.input.Keyboard;

public class EntityEnemy extends Entity {

	public Keybind keyLeft;
	public Keybind keyRight;
	public int maxDistLeft;
	public int maxDistRight;
	public int randEnemy;
	
	public EntityEnemy(Djinn djinn, float x, float y) {
		super(djinn);
		
		this.posX = x;
		this.posY = y;
		this.width *= (float) 1.8F;
		this.maxDistLeft = (int) (width*2);
		this.maxDistRight = (int) (width*2);
		
		this.keyLeft = new Keybind(Keyboard.KEY_A, "Left");
		this.keyRight = new Keybind(Keyboard.KEY_D, "Right");
	}

	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);		
		handleInput(djinn);
		handleCollisions(djinn);
		addEnemyShot(djinn);
	}

	private void handleInput(Djinn djinn) {
		this.motionX = 0;
		this.motionY = 0;
		
		if (this.keyLeft.isKeyDown() && this.maxDistLeft != 0) {
			this.motionX = -this.speed/16;
			this.maxDistLeft -= 1;
			this.maxDistRight += 1;
		}
		if (this.keyRight.isKeyDown() && this.maxDistRight != 0) {
			this.motionX = this.speed/16;
			this.maxDistRight -= 1;
			this.maxDistLeft += 1;
		}
	}
	
	public void addEnemyShot(Djinn djinn) {
		if (!djinn.gameStart) return;
		
		if (djinn.theWorld.enemyShotsToBeAdded.size() > 0) djinn.theWorld.enemyShotsToBeAdded.remove(0);
		
		int randNum = getRandRange(0, djinn.theWorld.initialNumEnemies);
	 	randEnemy = getRandRange(0, djinn.EnemyList.size()); // Choose a random enemy from the EnemyList

		if (randNum == randEnemy && djinn.theWorld.entities.contains(djinn.EnemyList.get(randEnemy))) { // This logic limits the amount of shots being produced
			djinn.theWorld.enemyShotsToBeAdded.add(new EntityEnemyShot(djinn, djinn.EnemyList.get(randEnemy).posX, djinn.EnemyList.get(randEnemy).posY)); 			// Add the initialized shot to the entities ArrayList
		} 
	}
	
	private void handleCollisions(Djinn djinn) {
	
//		boolean collisionWithBall = this.rect.intersects(djinn.theBall.rect);
//		if (collisionWithBall && djinn.getSystemTime()-this.lastCollision>1000) {
//			djinn.theBall.bounceY();
//			djinn.theWorld.entitiesToBeRemoved.add(this);
//			this.lastCollision = djinn.getSystemTime();
//		}
		
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

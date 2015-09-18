package djinn;

public class EntityEnemy extends Entity {

	public int maxDistLeft;
	public int maxDistRight;
	public int randEnemy;
	
	public long lastMove;
	public int moveCounter;
	public boolean moveDir;
	
	public EntityEnemy(Djinn djinn, float x, float y) {
		super(djinn);
		
		this.posX = x;
		this.posY = y;
		this.width *= (float) 2.5F;
		
		this.lastMove = djinn.getSystemTime();
		
		this.moveCounter = 5;
	}

	@Override
	public void onUpdate(Djinn djinn) {
		super.onUpdate(djinn);		
		handleInput(djinn);
		handleCollisions(djinn);
		addEnemyShot(djinn);
	}

	private void handleInput(Djinn djinn) {
		if (!djinn.theWorld.playState) {
			this.motionX = 0;
			this.motionY = 0;
			return;
		}
		
		if (djinn.getSystemTime()-this.lastMove>400) {
			this.moveCounter += 1;
			if (this.moveCounter % 10 == 0) this.moveDir = !this.moveDir; 
			
			if (this.moveDir) {
				for (Entity enemy : djinn.EnemyList) enemy.posX -= 0.2;
			}
			else {
				for (Entity enemy : djinn.EnemyList) enemy.posX += 0.2;
			}
			
			if (this.moveCounter % 10 == 0) {
				for (Entity enemy : djinn.EnemyList) enemy.posY += 0.1;
			}
			
			this.lastMove = djinn.getSystemTime();
		}
		
		this.motionX = 0;
		this.motionY = 0;
	}
	
	private void handleCollisions(Djinn djinn) {	
		if (djinn.thePlayer.shotActive) {
			boolean collisionWithPlayerShot = this.rect.intersects(djinn.playerShot.rect);
			if (collisionWithPlayerShot) {
				djinn.thePlayer.shotActive = false;
				djinn.theWorld.enemiesToBeRemoved.add(this);
			}
		}
	}
	
	public void addEnemyShot(Djinn djinn) {
		if (djinn.theWorld.playState){
		
			int randNum = getRandRange(0, djinn.theWorld.initialNumEnemies*20);
		 	randEnemy = getRandRange(0, djinn.EnemyList.size()); // Choose a random enemy from the EnemyList
		 	
			if (randNum==randEnemy) { // This logic limits the amount of shots being produced
				djinn.theWorld.enemyShotList.add(new EntityEnemyShot(djinn, djinn.EnemyList.get(randEnemy).posX, djinn.EnemyList.get(randEnemy).posY)); 
			}
		}
	}

	@Override
	public void moveEntity(Djinn djinn, float mx, float my) {
		super.moveEntity(djinn, mx, my);
		
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}

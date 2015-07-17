package djinn;

import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Djinn {

	private static Djinn instance;
	public String TITLE = "Djinn";
	public String VERSION = "0.0.1";
	public int displayWidth = 600;
	public int displayHeight = 1000;
	public static boolean isRunning = true;
	
	// Entity variables
	public EntityPlayer thePlayer;
	public EntityBall theBall;
	public EntityDivider theDivider;
	public ArrayList<EntityEnemy> EnemyList = new ArrayList<EntityEnemy>();
	public World theWorld;
	
	// State variables
	public boolean gameStart;
	
	public static void main(String[] args) {
		initEntities();
		initDisplay();
		gameLoop();
		cleanUp();
	}

	private static void initEntities() {
		instance = new Djinn();
		instance.thePlayer = new EntityPlayer(instance);
		instance.theBall = new EntityBall(instance);
		instance.theDivider = new EntityDivider(instance);
		
		// Add enemies
		for (int row=0;row<20;row++) {
			for (int col=0;col<15;col++){
				instance.EnemyList.add(new EntityEnemy(instance,2F+30*row,7F+15F*col));
			}
		}
		
		instance.theWorld = new World(instance);
	}
	
	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(instance.displayWidth, instance.displayHeight));
			Display.setInitialBackground(1.0F, 1.0F, 1.0F);
			Display.setTitle(instance.TITLE);
			Display.create();
		} catch (LWJGLException e) {
			isRunning = false;
			e.printStackTrace();
		}
	}
	
	private static void gameLoop() {
		while (!Display.isCloseRequested() && isRunning) {
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
			GL11.glLoadIdentity();
			instance.init2D(); // Sets up camera 
			instance.theWorld.run(instance);
			
			Display.sync(60);
			Display.update();
		}
	}
	
	private static void cleanUp() {
		isRunning = false;
		Display.destroy();		
	}
	
	public void init2D() {
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0.0f, Display.getWidth(), Display.getHeight(), 0.0f, -1.0f, 1.0f);
		
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLoadIdentity();
		
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0.4f);
	}
	
	public void gameReset() {
		instance.gameStart = false;
		instance.thePlayer.width -= 2;
		instance.theBall.posX = instance.thePlayer.posX + (instance.thePlayer.width/2);
		instance.theBall.posY = instance.thePlayer.posY - instance.theBall.height;
	}
	
	public long getSystemTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}
	
	public static int getRandRange(int min, int max) {
		return min + (int)(Math.random() * max);
	}
}

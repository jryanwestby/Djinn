package djinn;

import java.io.File;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Djinn {

	private static Djinn instance;
	public String TITLE = "Djinn";
	public String VERSION = "1.0";
	public int displayWidth = 450;
	public int displayHeight = 800;
	public static boolean isRunning = true;
	
	// Entity variables
	public EntityPlayer thePlayer;
	public EntityPlayerShot playerShot;
	public EntityDivider theDivider;
	public ArrayList<EntityEnemy> EnemyList = new ArrayList<EntityEnemy>();
	public EntityBlockHandler blockHandler;
	public World theWorld;
	public TextHandler textHandler;
	public EntitySelector menuSelector;
	
	public static void main(String[] args) {
		System.setProperty("org.lwjgl.librarypath", new File("lib/natives").getAbsolutePath());

		initEntities();
		initDisplay();
		gameLoop();
		cleanUp();
	}

	public static void initEntities() {
		instance = new Djinn();
		instance.thePlayer = new EntityPlayer(instance);
		instance.theDivider = new EntityDivider(instance);
		
		// Add enemies
		for (int row=0;row<8;row++) {
			for (int col=0;col<4;col++){
				instance.EnemyList.add(new EntityEnemy(instance,instance.displayWidth/8+instance.displayWidth/10*row,70F+40F*col));
			}
		}
		
		instance.blockHandler = new EntityBlockHandler(instance);
		instance.theWorld = new World(instance);
		instance.textHandler = new TextHandler(instance);
		instance.menuSelector = new EntitySelector(instance, 120, 462);
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
	
	public static void cleanUp() {
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
	
	public long getSystemTime() {
		return (Sys.getTime() * 1000 / Sys.getTimerResolution());
	}

}

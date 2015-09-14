package djinn;

import java.awt.Font;
import java.io.InputStream;

import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.util.ResourceLoader;

public class TextHandler {
	
	TrueTypeFont titleFontSm;
	TrueTypeFont titleFontLg;
	
	TrueTypeFont endFontSm;
	TrueTypeFont endFontLg;
	
	TrueTypeFont defenderFont;
	TrueTypeFont tetronFont;
	
	private boolean antiAlias = false;

	
	public TextHandler(Djinn djinn) {
	}
	
	public void titleText(Djinn djinn) {
		Color.white.bind();

		// load font from file
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("joystik.ttf");
	
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			titleFontSm = new TrueTypeFont(awtFont2, antiAlias);
			
			awtFont2 = awtFont2.deriveFont(30f);
			titleFontLg = new TrueTypeFont(awtFont2, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		titleFontSm.drawString(100, 200, "Welcome to...", Color.black);
		titleFontLg.drawString(160, 300, "DJINN", Color.black);
		
		titleFontSm.drawString(80, 400	, "Select Your Style:", Color.black);
		titleFontSm.drawString(160, 450	, "Defender", Color.black);
		titleFontSm.drawString(160, 500	, "Tetron", Color.black);
		titleFontSm.drawString(160, 550	, "Djinn", Color.black);
	}
	
	public void endText(Djinn djinn) {
		Color.white.bind();
		
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("joystik.ttf");
	
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			endFontSm = new TrueTypeFont(awtFont2, antiAlias);
			
			awtFont2 = awtFont2.deriveFont(30f);
			endFontLg = new TrueTypeFont(awtFont2, antiAlias);
		} catch (Exception e) {
			e.printStackTrace();
		}

		endFontLg.drawString(130, 300, "GAME OVER", Color.black);
		
		endFontSm.drawString(100, 400, "Select Your Fate:", Color.black);
		endFontSm.drawString(160, 450, "Another Try", Color.black);
		endFontSm.drawString(160, 500, "End It", Color.black);
	}
	
	public void initDefenderText(Djinn djinn) {
		Color.white.bind();

		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("joystik.ttf");
	
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(24f); // set font size
			defenderFont = new TrueTypeFont(awtFont2, antiAlias);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		drawDefenderText(djinn);
	}
	
	public void drawDefenderText(Djinn djinn) {
		int playerLives = (int)(80-djinn.thePlayer.width)/6;
		defenderFont.drawString(60, 550, "Shoot All Invaders!", Color.black);
		defenderFont.drawString(60, 600, "Lives Left: " + playerLives, Color.black);		
		
		defenderFont.drawString(60, 700	, "Arrow Keys: Move", Color.black);
		defenderFont.drawString(60, 750	, "Space: Shoot", Color.black);
		defenderFont.drawString(60, 800	, "R: Begin", Color.black);
	}
	
	public void initTetronText(Djinn djinn) {
		Color.white.bind();
		
		try {
			InputStream inputStream	= ResourceLoader.getResourceAsStream("joystik.ttf");
	
			Font awtFont2 = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont2 = awtFont2.deriveFont(20f); // set font size
			tetronFont = new TrueTypeFont(awtFont2, antiAlias);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		drawTetronText(djinn);
		tetronFont.drawString(60, 650, "R: Begin", Color.black);
	}

	public void drawTetronText(Djinn djinn) {
		tetronFont.drawString(60, 100, "Clear Lines for a Hi Score!", Color.black);
		tetronFont.drawString(60, 150, "Current Level: ", Color.black);
		
		tetronFont.drawString(60, 250, "A and D: Move Block", Color.black);
		tetronFont.drawString(60, 300, "W and S: Rotate Block", Color.black);
		tetronFont.drawString(60, 350, "Space: Speed Up Block", Color.black);
	}
}

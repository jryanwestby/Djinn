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
}

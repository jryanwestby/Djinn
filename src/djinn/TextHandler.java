package djinn;

import java.awt.Font;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.Color;

public class TextHandler {
	
	TrueTypeFont font;
	TrueTypeFont font2;
	
	public TextHandler(Djinn djinn) {
	}
	
	public void titleText(Djinn djinn) {
		Color.white.bind();
		
		this.font = new TrueTypeFont(new Font("Futura", Font.BOLD, 30), true);
		this.font2 = new TrueTypeFont(new Font("Futura", Font.BOLD, 48), true);
				
		font.drawString(djinn.displayWidth/2-200, djinn.displayHeight/2 - 400, "Welcome to...", Color.black);
		font2.drawString(djinn.displayWidth/2-100, djinn.displayHeight/2 - 360, "Djinn", Color.black);
		
		font.drawString(djinn.displayWidth/2-200, djinn.displayHeight/2 - 200	, "Please Press Enter", Color.black);
	}
	
	public void endText(Djinn djinn) {
		Color.white.bind();
		
		this.font = new TrueTypeFont(new Font("Futura", Font.BOLD, 30), true);
		this.font2 = new TrueTypeFont(new Font("Futura", Font.BOLD, 48), true);
				
		font2.drawString(djinn.displayWidth/2-100, djinn.displayHeight/2 - 360, "GAME OVER", Color.black);
		font.drawString(djinn.displayWidth/2-200, djinn.displayHeight/2 - 400, "Try Again? Press Enter", Color.black);
	}
}

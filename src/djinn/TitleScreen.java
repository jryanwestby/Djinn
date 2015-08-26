package djinn;

import java.awt.Font;

import org.lwjgl.input.Keyboard;
import org.newdawn.slick.TrueTypeFont;

public class TitleScreen {
	
	private TrueTypeFont font;
	public Keybind keyReturn;

	
	public TitleScreen(Djinn djinn) {
		Font awtFont = new Font("Futura", Font.BOLD, 24);
		this.font = new TrueTypeFont(awtFont, true);
		
		this.keyReturn = new Keybind(Keyboard.KEY_RETURN, "Return");		
	}
	
	public void run(Djinn djinn) {
		handleInput(djinn);
	}
	
	private void handleInput(Djinn djinn) {
		if (djinn.gameStart) return;
 
		if (this.keyReturn.isKeyDown()) {
			djinn.gameStart = true;
		} 
	}
}

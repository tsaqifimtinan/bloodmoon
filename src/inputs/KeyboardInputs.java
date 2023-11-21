package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import main.GamePanel;

public class KeyboardInputs implements KeyListener{
	
	private GamePanel gamepanel;
	
	public KeyboardInputs (GamePanel gamepanel) {
		this.gamepanel = gamepanel;
	}
	
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub	
		switch(e.getKeyCode()) {
		
		case KeyEvent.VK_W:
			gamepanel.changeyDelta(-10);
			System.out.println("User pressed W");
			break;
		case KeyEvent.VK_A:
			gamepanel.changexDelta(-10);
			System.out.println("User pressed A");
			break;
		case KeyEvent.VK_S:
			gamepanel.changeyDelta(+10);
			System.out.println("User pressed S");
			break;
		case KeyEvent.VK_D:
			gamepanel.changexDelta(+10);
			System.out.println("User pressed D");
			break;
		}
	}
}

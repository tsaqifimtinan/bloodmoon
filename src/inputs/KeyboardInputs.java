package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import gamestates.Gamestate;
import main.Game;
import main.GamePanel;

import static util.Constants.Direction.*;

public class KeyboardInputs implements KeyListener {

	private GamePanel gamePanel;

	public KeyboardInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		switch(Gamestate.state) {
		case menu:
			gamePanel.getGame().getMenu().keyReleased(e);
			break;
		case playing:
			gamePanel.getGame().getPlaying().keyReleased(e);
			break;
		default:
			break;
		
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(Gamestate.state) {
		case menu:
			gamePanel.getGame().getMenu().keyPressed(e);
			break;
		case playing:
			gamePanel.getGame().getPlaying().keyPressed(e);
			break;
		default:
			break;
		
		}
	}
}
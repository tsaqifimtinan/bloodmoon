package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import gamestates.Gamestate;
import main.GamePanel;

public class MouseInputs implements MouseListener, MouseMotionListener {

	private GamePanel gamePanel;

	public MouseInputs(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		switch (Gamestate.state) {
		case playing:
			gamePanel.getGame().getPlaying().mouseDragged(e);
			break;
		case options:
			gamePanel.getGame().getGameOptions().mouseDragged(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		switch (Gamestate.state) {
		case menu:
			gamePanel.getGame().getMenu().mouseMoved(e);
			break;
		case playing:
			gamePanel.getGame().getPlaying().mouseMoved(e);
			break;
		case options:
			gamePanel.getGame().getGameOptions().mouseMoved(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		switch (Gamestate.state) {
		case playing:
			gamePanel.getGame().getPlaying().mouseClicked(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (Gamestate.state) {
		case menu:
			gamePanel.getGame().getMenu().mousePressed(e);
			break;
		case playing:
			gamePanel.getGame().getPlaying().mousePressed(e);
			break;
		case options:
			gamePanel.getGame().getGameOptions().mousePressed(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		switch (Gamestate.state) {
		case menu:
			gamePanel.getGame().getMenu().mouseReleased(e);
			break;
		case playing:
			gamePanel.getGame().getPlaying().mouseReleased(e);
			break;
		case options:
			gamePanel.getGame().getGameOptions().mouseReleased(e);
			break;
		default:
			break;

		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}

}
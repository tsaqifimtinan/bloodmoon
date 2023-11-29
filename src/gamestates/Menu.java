package gamestates;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import main.Game;
import ui.MenuButton;
import util.LoadSave;

public class Menu extends State implements Statemethods {

	private MenuButton[] buttons = new MenuButton[3];
	private BufferedImage backgroundImg, backgroundImgPink;
	private int menuX, menuY, menuWidth, menuHeight;

	public Menu(Game game) {
		super(game);
		loadButtons();
		loadBackground();
		backgroundImgPink = LoadSave.getSpriteAtlas(LoadSave.menu_background_img);

	}

	private void loadBackground() {
		backgroundImg = LoadSave.getSpriteAtlas(LoadSave.menu_background);
		menuWidth = (int) (backgroundImg.getWidth() * Game.scale);
		menuHeight = (int) (backgroundImg.getHeight() * Game.scale);
		menuX = Game.game_width / 2 - menuWidth / 2;
		menuY = (int) (45 * Game.scale);

	}

	private void loadButtons() {
		buttons[0] = new MenuButton(Game.game_width / 2, (int) (150 * Game.scale), 0, Gamestate.playing);
		buttons[1] = new MenuButton(Game.game_width / 2, (int) (220 * Game.scale), 1, Gamestate.options);
		buttons[2] = new MenuButton(Game.game_width / 2, (int) (290 * Game.scale), 2, Gamestate.quit);
	}

	@Override
	public void update() {
		for (MenuButton mb : buttons)
			mb.update();
	}

	@Override
	public void draw(Graphics g) {

		g.drawImage(backgroundImgPink, 0, 0, Game.game_width, Game.game_height, null);
		g.drawImage(backgroundImg, menuX, menuY, menuWidth, menuHeight, null);

		for (MenuButton mb : buttons)
			mb.draw(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				mb.setMousePressed(true);
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MenuButton mb : buttons) {
			if (isIn(e, mb)) {
				if (mb.isMousePressed())
					mb.applyGamestate();
				break;
			}
		}

		resetButtons();

	}

	private void resetButtons() {
		for (MenuButton mb : buttons)
			mb.resetBools();

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		for (MenuButton mb : buttons)
			mb.setMouseOver(false);

		for (MenuButton mb : buttons)
			if (isIn(e, mb)) {
				mb.setMouseOver(true);
				break;
			}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER)
			Gamestate.state = Gamestate.playing;

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
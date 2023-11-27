package main;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import static util.Constants.PlayerConstants.*;
import static util.Constants.Direction.*;
import static main.Game.game_height;
import static main.Game.game_width;

public class GamePanel extends JPanel {

	private MouseInputs mouseInputs;
	private Game game;
	public GamePanel(Game game) {
		mouseInputs = new MouseInputs(this);
		this.game = game;
		setPanelSize();
		addKeyListener (new KeyboardInputs(this));
		addMouseListener(mouseInputs);
		addMouseMotionListener(mouseInputs);
	}

	private void setPanelSize() {
		Dimension size = new Dimension(game_width, game_height);
		setPreferredSize(size);
		System.out.println("Size: " + game_width + " : " + game_height);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		game.render(g);
	}
	
	public Game getGame () {
		return game;
	}
}
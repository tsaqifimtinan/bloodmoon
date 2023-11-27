package main;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import entities.Entity;
import entities.Player;
import levels.LevelManager;

public class Game implements Runnable {

	private GameWindow gameWindow;
	private GamePanel gamePanel;
	private Thread gameThread;
	private final int FPS_SET = 60;
	private final int UPS_SET = 100;
	private Player player;
	private LevelManager levelManager;
	private List<Entity> entities;
	public final static int tiles_default_size = 64;
	public final static int scale = 1;
	public final static int tiles_in_width = 26;
	public final static int tiles_in_height = 14;
	public final static int tiles_size = (int) (tiles_default_size * scale);
	public final static int game_width = tiles_size * tiles_in_width;
	public final static int game_height = tiles_size * tiles_in_height;
	

	public Game() {
		entities = new ArrayList<>();
		initialize();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
	}

	private void initialize() {
		levelManager = new LevelManager(this);
		player = new Player(200, 500, (int) 128 * scale, (int) 128 * scale, 2);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		player.update();
		levelManager.update();
		
		// Update logic for entities
        for (Entity entity : entities) {
			player.update();
		}
	
		// Check and restrict y-coordinate
		float maxYLimit = game_height - 128; // Adjust this value as needed
	
		if (player.getY() > maxYLimit) {
			player.setY(maxYLimit);
		}
	}
	
	public void render (Graphics g) {
		levelManager.draw(g);
		player.render(g);
		
		for (Entity entity : entities) {
            player.render(g);
        }
	}

	@Override
	public void run() {

		double timePerFrame = 1000000000.0 / FPS_SET;
		double timePerUpdate = 1000000000.0 / UPS_SET;
		long lastFrame = System.nanoTime();
		long now = System.nanoTime();
		long previousTime = System.nanoTime();
		
		int frames = 0;
		int updates = 0;
		long lastCheck = System.currentTimeMillis();
		
		double deltaU = 0;
		double deltaF = 0;

		while (true) {
			now = System.nanoTime();
			long currentTime = System.nanoTime();

			deltaU += (currentTime - previousTime) / timePerUpdate;
			deltaF += (currentTime - previousTime) / timePerFrame;
			previousTime = currentTime;
			
			if (deltaU >= 1) {
				update();
				updates++;
				deltaU--;
			}
			
			if (deltaF >= 1) {
				gamePanel.repaint();
				frames++;
				deltaF--;
			}

			if (System.currentTimeMillis() - lastCheck >= 1000) {
				lastCheck = System.currentTimeMillis();
				System.out.println("FPS: " + frames + " | UPS: " + updates);
				frames = 0;
				updates = 0;
			}
		}

	}
	
	public void windowFocusLost() {
		player.resetDirBooleans();
	}
	
	public Player getPlayer() {
		return player;
	}

}
package main;

import java.awt.Graphics;

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
	public final static int tiles_default_size = 64;
	public final static float scale = 1.0f;
	public final static int tiles_in_width = 26;
	public final static int tiles_in_height = 14;
	public final static int tiles_size = (int) (tiles_default_size * scale);
	public final static int game_width = tiles_size * tiles_in_width;
	public final static int game_height = tiles_size * tiles_in_height;
	

	public Game() {
		
		initialize();
		gamePanel = new GamePanel(this);
		gameWindow = new GameWindow(gamePanel);
		gamePanel.requestFocus();
		startGameLoop();
	}

	private void initialize() {
		player = new Player(200, 200);
		levelManager = new LevelManager(this);
	}

	private void startGameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void update() {
		player.update();
		levelManager.update();
	}
	
	public void render (Graphics g) {
		levelManager.draw(g);
		player.render(g);
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
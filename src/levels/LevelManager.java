package levels;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import util.LoadSave;

public class LevelManager {
	private Game game;
	private BufferedImage levelSpriteGround, levelSpriteSky;
	
	public LevelManager(Game game) {
		this.game = game;
		levelSpriteGround = LoadSave.getSpriteAtlas(LoadSave.level_atlas);
		levelSpriteSky = LoadSave.getSpriteAtlas(LoadSave.sky_atlas);
		
	}

	public void draw(Graphics g) {
		g.drawImage(levelSpriteSky, 0, 0, null);
		g.drawImage(levelSpriteGround, 0, 0, null);
	}
	
	public void update() {
		
	}
}
